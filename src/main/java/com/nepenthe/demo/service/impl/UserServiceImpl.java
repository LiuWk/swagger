package com.nepenthe.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nepenthe.demo.dto.LoginDto;
import com.nepenthe.demo.dto.RegisterDto;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.service.repository.UserRepository;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.TokenUtil;
import com.nepenthe.demo.util.redis.RedisManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author lwk
 * @date 2019-05-08 10:43
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisManager redisManager;

    @Transactional(readOnly = true)
    @Override
    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByMobile(String mobile) {
        String key = String.format(Constant.USER_INFO, mobile);
        User user = null;
        String json = redisManager.get(key);
        if (StringUtils.isEmpty(json)) {
            user = userRepository.findUserByMobile(mobile);
            if (user != null) {
                redisManager.set(key, JSONObject.toJSONString(user), Constant.MONTH_TIME);
            }
        } else {
            return JSONObject.parseObject(json, User.class);
        }
        return user;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public User userRegister(RegisterDto registerDto) {
        User entity = new User();
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        BeanUtils.copyProperties(registerDto, entity);
        User user = userRepository.save(entity);
        String key = String.format(Constant.USER_INFO, user.getMobile());
        redisManager.set(key, JSONObject.toJSONString(user), Constant.MONTH_TIME);
        return user;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public LoginDto login(User user) {
        String token = TokenUtil.getToken(user);
        // 存储token
        String tokenKey = String.format(Constant.USER_TOKEN, user.getId());
        redisManager.set(tokenKey, token, Constant.WEEK_TIME);
        LoginDto loginDto = new LoginDto();
        loginDto.setMobile(user.getMobile());
        loginDto.setToken(token);
        loginDto.setUserId(user.getId());
        return loginDto;
    }
}
