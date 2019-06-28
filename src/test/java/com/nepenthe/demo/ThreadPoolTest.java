package com.nepenthe.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lwk
 * @date 2019-06-28 09:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolTest {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void threadPoolTest() {
        for (int i = 0; i < 10; i++) {

            threadPoolTaskExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程中");
            });
        }

        System.out.println("threadPoolTest end");
    }
}
