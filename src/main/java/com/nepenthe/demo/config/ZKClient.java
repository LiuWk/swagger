package com.nepenthe.demo.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * zookeeper 客户端
 *
 * @author lwk
 * @date 2019-07-02 15:33
 */
@Component
public class ZKClient {
    private CuratorFramework client = null;

    private final static Logger logger = LoggerFactory.getLogger(ZKClient.class);
    @Value("${zk.zkServer}")
    private String zkServer;
    @Value("${zk.sessionTimeoutMs}")
    private int sessionTimeoutMs;
    @Value("${zk.baseSleepTimeMs}")
    private int baseSleepTimeMs;
    @Value("${zk.maxRetries}")
    private int maxRetries;


    @Bean
    public void init() {
        if (client != null) {
            return;
        }
        //创建重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        //创建zookeeper客户端
        client = CuratorFrameworkFactory.builder().connectString(zkServer)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(retryPolicy)
                .namespace("demo_zk")
                .build();
        client.start();
    }

    /**
     * 创建节点
     *
     * @param path       路径
     * @param createMode 节点类型
     *                   * 1.PERSISTENT 持久型
     *                   * <p>
     *                   * 2.PERSISTENT_SEQUENTIAL 持久顺序型
     *                   * <p>
     *                   * 3.EPHEMERAL 临时型
     *                   * <p>
     *                   * 4.EPHEMERAL_SEQUENTIAL 临时顺序型
     * @param data       节点数据
     * @return 是否创建成功
     */
    public boolean crateNode(String path, CreateMode createMode, String data) {
        try {
            client.create().withMode(createMode).forPath(path, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            //org.apache.zookeeper.KeeperException$NodeExistsException: KeeperErrorCode = NodeExists
            return false;
        }

        return true;
    }

    /**
     * 判断节点是否存在
     *
     * @param path 路径
     * @return true-存在  false-不存在
     */
    public boolean isExistNode(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            return stat != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在quartz节点执行任务之前，创建zk节点
     *
     * @return
     */
    public boolean lock(String lockKey) {
        String path = "/".concat(lockKey);
        boolean bool = false;
        try {
            String p = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            bool = true;
            logger.info("node path:{}", p);
        } catch (Exception e) {
            logger.error("lockJob exception:{}", e.getMessage());
        }
        return bool;
    }
    /**
     * 删除锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        String path = "/".concat(lockKey);
        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            logger.error("unlockJob exception:{}", e.getMessage());
        }
    }
}