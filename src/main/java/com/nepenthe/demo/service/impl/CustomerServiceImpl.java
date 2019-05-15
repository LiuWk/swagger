package com.nepenthe.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.service.CustomerService;
import com.nepenthe.demo.entity.Customer;
import com.nepenthe.demo.service.repository.CustomerRepository;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author lwk
 * @date 2019-05-06 15:48
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RedisManager redisManager;

    @Transactional(readOnly = true)
    @Override
    public Customer findCustomerByCustomerId(Integer customerId) {
        String key = String.format(Constant.CUSTOMER_INFO, customerId);
        String json = redisManager.get(key);
        if (StringUtils.isEmpty(json)) {
            Customer c = customerRepository.findCustomerByCustomerId(customerId);
            if (c != null) {
                redisManager.set(key, JSONObject.toJSONString(c), Constant.MONTH_TIME);
            }
            return c;
        } else {
            return JSONObject.parseObject(json, Customer.class);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Customer> findCustomerList(Integer page, Integer size) {
        return customerRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
