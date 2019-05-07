package com.nepenthe.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.config.request.Request;
import com.nepenthe.demo.service.CustomerService;
import com.nepenthe.demo.config.response.ErrorResponse;
import com.nepenthe.demo.config.response.Response;
import com.nepenthe.demo.entity.Customer;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.redis.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lwk
 * @date 2019-04-15 15:19
 */
@Api(tags = "测试")
@RestController
@RequestMapping(value = "customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RedisManager redisManager;

    @ApiOperation(value = "顾客信息", httpMethod = "POST")
    @RequestMapping(value = "customerInfo", method = RequestMethod.POST)
    public Response customerInfo(@RequestBody JSONObject jsonObject) {
        // TODO 传入的报文可以统一切面打印
        logger.info("customerInfo request json={}", jsonObject);
        try {
            JSONObject body = jsonObject.getJSONObject("body");
            Integer customerId = body.getInteger("customerId");
            if (customerId == null) {
                return new ErrorResponse(Code.PARAMETER_IS_NULL, Constant.getMsg(Code.PARAMETER_IS_NULL));
            }
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerByCustomerId(customerId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("customerInfo exception={}",e);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    @ApiOperation(value = "顾客列表", httpMethod = "POST")
    @RequestMapping(value = "customerList", method = RequestMethod.POST)
    public Response customerList(@RequestBody JSONObject jsonObject) {
        logger.info("customerInfo request={}", jsonObject);
        Integer page = jsonObject.getInteger("page");
        Integer size = jsonObject.getInteger("size");
        page = page == null ? 1 : page;
        size = size == null ? 1 : size;

        return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), customerService.findCustomerList(page, size));
    }

    @ApiOperation(value = "添加客户", httpMethod = "POST")
    @RequestMapping(value = "saveCustomer", method = RequestMethod.POST)
    public Response saveCustomer(@RequestBody JSONObject jsonObject) {
        logger.info("saveCustomer request={}", jsonObject);
        Customer customer = null;
        try {
            JSONObject body = jsonObject.getJSONObject("body");
            customer = JSONObject.parseObject(body.toJSONString(), Customer.class);
        } catch (Exception e) {
            logger.error("转换对象异常={}", e);
            return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
        }

        String key = "";
        Long count = redisManager.incr(key);
        try {
            customerService.saveCustomer(customer);
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), null);
        } catch (Exception e) {
            logger.error("saveCustomer exception={}", e);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    public static void main(String[] args) {
        String u = "index.html#/approvalDetail?type=25&exampleId=%s";
        System.out.println(String.format(u, 1111));
    }

}
