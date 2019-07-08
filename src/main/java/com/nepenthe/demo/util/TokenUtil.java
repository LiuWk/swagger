package com.nepenthe.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.util.redis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 生成token工具
 *
 * @author lwk
 * @date 2019-05-08 15:52
 */
public class TokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    /**
     * TODO 自定义一个token生成规则
     * @param user
     * @return
     */
    public static String getToken(User user) {
        if (Utils.hasEmpty(user, user.getId(), user.getPassword())) {
            return "";
        }
        String token = "";
        try {
            // 将 userId 保存到 token 里面
            token = JWT.create().withAudience(String.valueOf(user.getId()))
                    // 以 password 作为 token 的密钥
                    .sign(Algorithm.HMAC256(user.getPassword()));
        } catch (Exception e) {
            logger.error("getToken exception", e.getMessage(),e);
        }
        return token;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setPassword("111");
        System.out.println(getToken(user));
    }
}
