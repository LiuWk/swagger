package com.nepenthe.demo.service.repository;

import com.nepenthe.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lwk
 * @date 2019-05-08 10:39
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 查询用户
     * @param id
     * @return
     */
    User findUserById(Integer id);
}
