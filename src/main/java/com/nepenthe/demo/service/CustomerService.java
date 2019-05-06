package com.nepenthe.demo.service;

import com.nepenthe.demo.entity.Customer;
import org.springframework.data.domain.Page;

/**
 * @author lwk
 * @date 2019-05-06 15:47
 */
public interface CustomerService {
    /**
     * 查询客户
     *
     * @param customerId 客户id
     * @return
     */
    Customer findCustomerByCustomerId(Integer customerId);

    /**
     * 查询列表
     *
     * @param page
     * @param size
     * @return
     */
    Page<Customer> findCustomerList(Integer page, Integer size);

    /**
     * 保存客户
     *
     * @param customer
     */
    void saveCustomer(Customer customer);
}
