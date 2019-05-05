package com.nepenthe.demo.repository;

import com.nepenthe.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 客户jpa处理类
 * @author lwk
 * @date 2019-04-15 16:30
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
