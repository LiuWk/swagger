package com.nepenthe.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.config.annotation.UserLoginToken;
import com.nepenthe.demo.dto.request.Request;
import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.dto.response.Response;
import com.nepenthe.demo.entity.Customer;
import com.nepenthe.demo.service.CustomerService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
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
    private RedisManager redisManager;

    @UserLoginToken(required = false)
    @ApiOperation(value = "测试", httpMethod = "POST")
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public Response test(@RequestParam(value = "json") String json) {

        return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), json);
    }


    @UserLoginToken(required = false)
    @ApiOperation(value = "顾客信息", httpMethod = "POST")
    @RequestMapping(value = "getCustomerInfo", method = RequestMethod.POST)
    public Response getCustomerInfo(@RequestParam(value = "json") String json) {
        try {
            Request req = Utils.getRequest(json);
            if (req == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            Integer customerId = req.getBody().getInteger("customerId");
            if (customerId == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerByCustomerId(customerId));
        } catch (Exception e) {
            logger.error("customerInfo exception={}", e);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    @UserLoginToken(required = false)
    @ApiOperation(value = "顾客列表", httpMethod = "POST")
    @RequestMapping(value = "getCustomerList", method = RequestMethod.POST)
    public Response getCustomerList(@RequestParam(value = "json") String json) {
        Integer page;
        Integer size;
        try {
            Request req = Utils.getRequest(json);
            if (req == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            page = req.getBody().getInteger("page");
            size = req.getBody().getInteger("size");
        } catch (Exception e) {
            logger.error("customerList exception={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        page = page == null ? 1 : page;
        size = size == null ? 1 : size;
        return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerList(page, size));
    }

    @UserLoginToken(required = false)
    @ApiOperation(value = "添加客户", httpMethod = "POST")
    @RequestMapping(value = "saveCustomer", method = RequestMethod.POST)
    public Response saveCustomer(@RequestParam(value = "json") String json) {
        Customer customer = null;
        try {
            Request req = Utils.getRequest(json);
            if (req == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            customer = JSONObject.parseObject(req.getBody().toJSONString(), Customer.class);
        } catch (Exception e) {
            logger.error("转换对象异常={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        String key = "11";
        Long count = redisManager.incr(key);
        if (count > 1) {
            return new ErrorResponse(Code.DUPLICATE_SUBMISSION, Constant.getMsg(Code.DUPLICATE_SUBMISSION));
        } else {
            redisManager.expire(key, 60);
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
