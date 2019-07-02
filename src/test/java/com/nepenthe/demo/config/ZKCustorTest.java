package com.nepenthe.demo.config;

import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ZKCustorTest {
    @Autowired
    private ZKClient zkClient;

    @Test
    public void zkTest() {
        zkClient.crateNode("/lwk", CreateMode.PERSISTENT,"啛啛喳喳错错");
    }
}