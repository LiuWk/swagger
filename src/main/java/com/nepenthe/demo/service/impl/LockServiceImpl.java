package com.nepenthe.demo.service.impl;

import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.dto.response.Response;
import com.nepenthe.demo.service.LockService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import com.nepenthe.demo.util.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通用锁
 *
 * @author lwk
 * @date 2019-05-15 17:51
 */
@Service("lockService")
public class LockServiceImpl implements LockService {
    private static final Logger logger = LoggerFactory.getLogger(LockServiceImpl.class);
    @Autowired
    private RedisManager redisManager;

    @Override
    public Response addLock(String lockKey) {
        try {
            Long count = redisManager.incr(lockKey);
            if (count > 1) {
                return new ErrorResponse(Code.DUPLICATE_SUBMISSION, Constant.getMsg(Code.DUPLICATE_SUBMISSION));
            } else {
                redisManager.expire(lockKey, 60);
            }
            return new Response(Code.SUCCESS, Constant.getMsg(Code.SUCCESS), null);
        } catch (Exception e) {
            logger.error("addLock exception={}",e);
        }
        return new ErrorResponse(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR));
    }

    @Override
    public void deleteLock(String lockKey) {
        try {
            redisManager.delete(lockKey);
        } catch (Exception e) {
            logger.error("deleteLock exception={}",e);
        }
    }
}
