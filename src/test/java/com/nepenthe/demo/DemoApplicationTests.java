package com.nepenthe.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void sendSimpleMail() {
        String to = "liuweikang@jiayuan.com";
        String subject = "主题测试";
        String content = "测试赛所所所所所所";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 邮件发送者
            message.setFrom("lwk_mail@126.com");
            // 邮件接受者
            message.setTo(to);
            // 主题
            message.setSubject(subject);
            // 内容
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception e) {
//            logger.error("EmailSenderUtil sendSimpleMail exception={}", e.getMessage());
        }
    }
}
