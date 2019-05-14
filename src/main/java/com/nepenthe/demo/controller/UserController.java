package com.nepenthe.demo.controller;

import com.nepenthe.demo.dto.LoginDto;
import com.nepenthe.demo.dto.request.Request;
import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.dto.response.Response;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.TokenUtil;
import com.nepenthe.demo.util.Utils;
import com.nepenthe.demo.util.redis.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("用户相关")
@RestController
@RequestMapping(value = "user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", httpMethod = "POST")
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
            if (Constant.hasEmpty(mobile, password)) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
        } catch (Exception e) {
            logger.error("转换对象异常={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        String key = "loginkey";
        Long count = redisManager.incr(key);
        if (count > 1) {
            return new ErrorResponse(Code.DUPLICATE_SUBMISSION, Constant.getMsg(Code.DUPLICATE_SUBMISSION));
        } else {
            redisManager.expire(key, 60);
        }
        try {
            User user = userService.findUserByMobile(mobile);
            if (user == null) {
                return new ErrorResponse(Code.USER_NOT_EXIST, Constant.getMsg(Code.USER_NOT_EXIST));
            }
            // TODO 验证密码是否正确，先简单验证
            if (!user.getPassword().equals(password)) {
                return new ErrorResponse(Code.PASSWORD_IS_INCORRECT, Constant.getMsg(Code.PASSWORD_IS_INCORRECT));
            }

            String token = TokenUtil.getToken(user);
            LoginDto loginDto = new LoginDto();
            loginDto.setMobile(user.getMobile());
            loginDto.setToken(token);
            loginDto.setUserId(user.getId());
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), loginDto);
        } catch (Exception e) {
            logger.error("saveCustomer exception={}", e);
        } finally {
            redisManager.delete(key);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }
}
