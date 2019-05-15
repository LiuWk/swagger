package com.nepenthe.demo.service;

import com.nepenthe.demo.dto.response.Response;

/**
 * 通用锁
 * @author lwk
 * @date 2019-05-15 17:50
 */
public interface LockService {
    /**
     * 加锁
     * @param lockKey 加锁的redis key
     */
    Response addLock(String lockKey);

    /**
     * 删除锁
     * @param lockKey 锁redis key
     */
    void deleteLock(String lockKey);
}
