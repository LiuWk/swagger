package com.nepenthe.demo.service;

import com.nepenthe.demo.entity.User;

/**
 * @author lwk
 * @date 2019-05-08 10:42
 */
public interface UserService {
    /**
     * 查询用户
     * @param userId
     * @return
     */
    User findUserById(Integer userId);

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);
}
