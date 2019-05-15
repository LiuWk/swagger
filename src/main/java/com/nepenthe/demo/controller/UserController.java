package com.nepenthe.demo.controller;

import com.nepenthe.demo.dto.LoginDto;
import com.nepenthe.demo.dto.RegisterDto;
import com.nepenthe.demo.dto.request.Request;
import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.dto.response.Response;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.LockService;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.Utils;
import com.nepenthe.demo.util.redis.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 *
 * @author lwk
 * @date 2019-05-13 09:05
 */
@Api(tags = "用户相关")
@RestController
@RequestMapping(value = "user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private LockService lockService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "String", required = true)
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response login(@RequestParam(value = "json") String json) {
        logger.info("login json={}", json);
        String mobile;
        String password;
        try {
            Request req = Utils.getRequest(json);
            if (req == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            mobile = req.getBody().getString("mobile");
            password = req.getBody().getString("password");
            if (Utils.hasEmpty(mobile, password)) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
        } catch (Exception e) {
            logger.error("login convert request exception={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }
        // 加锁
        String lockKey = String.format(Constant.USER_LOGIN_LOCK, mobile);
        Response lockResp = lockService.addLock(lockKey);
        if (!lockResp.getSuccess()){
            return lockResp;
        }
        try {
            User user = userService.findUserByMobile(mobile);
            if (user == null) {
                return new ErrorResponse(Code.USER_NOT_EXIST, Constant.getMsg(Code.USER_NOT_EXIST));
            }
            if (!password.equals(user.getPassword())) {
                return new ErrorResponse(Code.PASSWORD_IS_INCORRECT, Constant.getMsg(Code.PASSWORD_IS_INCORRECT));
            }

            LoginDto loginDto = userService.login(user);
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), loginDto);
        } catch (Exception e) {
            logger.error("saveCustomer exception={}", e);
        } finally {
            lockService.deleteLock(lockKey);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    /**
     * 用户注册
     *
     * @return response
     */
    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Response register(@RequestParam(value = "json") String json) {
        logger.info("register json={}", json);
        RegisterDto registerDto;
        try {
            Request req = Utils.getRequest(json);
            if (req == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            registerDto = req.getBody().toJavaObject(RegisterDto.class);

            if (Utils.hasEmpty(registerDto, registerDto.getMobile(), registerDto.getPassword())) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            // 验证手机号格式
            if (!Utils.checkMobile(registerDto.getMobile())) {
                return new ErrorResponse(Code.MOBILE_FORMAT_INCORRECT, Constant.getMsg(Code.MOBILE_FORMAT_INCORRECT));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        String lockKey = String.format(Constant.USER_REGISTER_LOCK, registerDto.getMobile());
        Response lockResp = lockService.addLock(lockKey);
        if (!lockResp.getSuccess()){
            return lockResp;
        }

        try {

            // 验证手机号是否存在
            User user = userService.findUserByMobile(registerDto.getMobile());
            if (!StringUtils.isEmpty(user)) {
                return new ErrorResponse(Code.USER_EXIST, Constant.getMsg(Code.USER_EXIST));
            }

            /*// 验证验证码。。。。。
            if (registerDto.getSecurityCode()){

            }*/

            // 注册成功执行登录
            User newUser = userService.userRegister(registerDto);
            if (newUser != null && newUser.getId() != null) {
                LoginDto loginDto = userService.login(user);
                return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), loginDto);
            }
        } catch (Exception e) {
            logger.error("register exception={}", e);
        } finally {
            lockService.deleteLock(lockKey);
        }

        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));

    }
}
