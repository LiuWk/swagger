package com.nepenthe.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nepenthe.demo.entity.User;

/**
 * 生成token工具
 *
 * @author lwk
 * @date 2019-05-08 15:52
 */
public class TokenUtil {
    public static String getToken(User user) {
        String token = "";
        // 将 userId 保存到 token 里面
        token = JWT.create().withAudience(String.valueOf(user.getId()))
                // 以 password 作为 token 的密钥
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
