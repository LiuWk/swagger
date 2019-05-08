package com.nepenthe.demo.service.impl;

import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lwk
 * @date 2019-05-08 10:43
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }
}
