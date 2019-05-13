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
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    User findUserByMobile(String mobile);

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);
}
