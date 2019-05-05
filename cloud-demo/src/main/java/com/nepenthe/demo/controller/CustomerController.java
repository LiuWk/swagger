package com.nepenthe.demo.controller;

import com.nepenthe.demo.repository.CustomerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author lwk
 * @date 2019-04-15 15:19
 */
@Api(tags = "测试")
@RestController
@RequestMapping(value = "customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @ApiOperation(value = "顾客信息", httpMethod = "POST")
    @RequestMapping(value = "customerInfo", method = RequestMethod.POST)
    public Object customerInfo(@RequestParam("customerId") Integer customerId) {

        return customerRepository.getOne(customerId);
    }

    @ApiOperation(value = "顾客列表", httpMethod = "POST")
    @RequestMapping(value = "customerList", method = RequestMethod.POST)
    public Object customerList(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 1 : size;
        return customerRepository.findAll(PageRequest.of(page, size));
    }

    public static void main(String[] args) {
        String u = "index.html#/approvalDetail?type=25&exampleId=%s";
        System.out.println(String.format(u,1111));
    }

}
