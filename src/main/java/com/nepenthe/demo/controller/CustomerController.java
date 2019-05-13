package com.nepenthe.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.config.annotation.UserLoginToken;
import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.dto.response.Response;
import com.nepenthe.demo.entity.Customer;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.CustomerService;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.redis.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 客户相关接口
 *
 * @author lwk
 * @date 2019-04-15 15:19
 */
@Api(tags = "客户相关")
@RestController
@RequestMapping(value = "customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisManager redisManager;

    @UserLoginToken(required = false)
    @ApiOperation(value = "测试redis", httpMethod = "POST")
    @RequestMapping(value = "putRedis", method = RequestMethod.POST)
    public Response putRedis(@RequestBody JSONObject jsonObject) {
        /*long start = System.currentTimeMillis();
        for (int i = 0; i < 10001; i++) {
            String key = UUID.randomUUID().toString();
            redisManager.incr(key);
            redisManager.expire(key, 60);
        }
        System.out.println("时间=" + (System.currentTimeMillis() - start));*/
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("aaa");

        User user = new User();
        user.setGender(0);
        user.setCreateTime(new Date());
        user.setMobile("12211112222");
        user.setName("001");
        userService.saveUser(user);
        return null;
    }


    @UserLoginToken(required = false)
    @ApiOperation(value = "顾客信息", httpMethod = "POST")
    @RequestMapping(value = "customerInfo", method = RequestMethod.POST)
    public Response customerInfo(@RequestBody JSONObject jsonObject) {
        try {
            JSONObject body = jsonObject.getJSONObject("body");
            Integer customerId = body.getInteger("customerId");
            if (customerId == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerByCustomerId(customerId));
        } catch (Exception e) {
            logger.error("customerInfo exception={}", e);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    @ApiOperation(value = "顾客列表", httpMethod = "POST")
    @RequestMapping(value = "customerList", method = RequestMethod.POST)
    public Response customerList(@RequestBody JSONObject jsonObject) {
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        page = page == null ? 1 : page;
        size = size == null ? 1 : size;

        return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerList(page, size));
    }

    @UserLoginToken(required = false)
    @ApiOperation(value = "添加客户", httpMethod = "POST")
    @RequestMapping(value = "saveCustomer", method = RequestMethod.POST)
    public Response saveCustomer(@RequestBody JSONObject jsonObject) {
        Customer customer = null;
        try {
            JSONObject body = jsonObject.getJSONObject("body");
            customer = JSONObject.parseObject(body.toJSONString(), Customer.class);
        } catch (Exception e) {
            logger.error("转换对象异常={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        String key = "11";
        Long count = redisManager.incr(key);
        redisManager.expire(key, 60);
        if (count > 1) {
            return new ErrorResponse(Code.DUPLICATE_SUBMISSION, Constant.getMsg(Code.DUPLICATE_SUBMISSION));
        }
        try {
            customerService.saveCustomer(customer);
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), null);
        } catch (Exception e) {
            logger.error("saveCustomer exception={}", e);
        } finally {
            redisManager.delete(key);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    public static void main(String[] args) {
        String u = "index.html#/approvalDetail?type=25&exampleId=%s";
        System.out.println(String.format(u, 1111));
    }

}
