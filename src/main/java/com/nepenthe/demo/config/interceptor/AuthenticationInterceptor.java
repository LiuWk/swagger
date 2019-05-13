package com.nepenthe.demo.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nepenthe.demo.config.annotation.UserLoginToken;
import com.nepenthe.demo.dto.response.ErrorResponse;
import com.nepenthe.demo.entity.User;
import com.nepenthe.demo.service.UserService;
import com.nepenthe.demo.util.Code;
import com.nepenthe.demo.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 拦截器，自定义异常处理
 *
 * @author lwk
 * @date 2019-05-08 10:39
 */
@RestControllerAdvice
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private UserService userService;

    @ExceptionHandler(BaseServiceException.class)
    public ErrorResponse baseServiceException(BaseServiceException e) {
        logger.error("BaseServiceException={}", e);
        // 自定义消息
        String msg = e.getMsg();
        return new ErrorResponse(e.getCode(), msg, e.getMessage());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException {
        /**
         * TODO 打印请求的数据
         */
        logger.info("[http request] <=====> url={} , json={}", request.getRequestURI(), request.getParameter("json"));

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从 http 中取出 token
        String token = getHttpToken(request.getParameter("json"));

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new BaseServiceException(Code.TOKEN_IS_NULL, Constant.getMsg(Code.TOKEN_IS_NULL), "验证登录异常");
                }
                // 获取 token 中的 userId
                Integer userId;
                try {
                    userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
                } catch (JWTDecodeException e) {
                    throw new BaseServiceException(Code.TOKEN_IS_NULL, Constant.getMsg(Code.TOKEN_IS_NULL), e.getMessage());
                }
                User user = userService.findUserById(userId);
                if (user == null) {
                    throw new BaseServiceException(Code.USER_NOT_EXIST, Constant.getMsg(Code.USER_NOT_EXIST), "用户信息不存在");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new BaseServiceException(Code.TOKEN_IS_NULL, Constant.getMsg(Code.TOKEN_IS_NULL), e.getMessage());
                }
                return true;
            }
            return true;
        }

        throw new BaseServiceException(Code.SYSTEM_ERROR, Constant.getMsg(Code.SYSTEM_ERROR), "系统异常");
    }

    /**
     * 根据请求参数json取出token信息
     *
     * @param json
     * @return
     */
    private String getHttpToken(String json) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(json);
            if (jsonObj != null) {
                return jsonObj.getString("token");
            }
        } catch (Exception e) {
            logger.error("getHttpToken exception={}", e);
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {

    }

    /**
     * 流被关闭
     * <p>
     * Resolved [org.springframework.http.converter.HttpMessageNotReadableException: I/O error while reading input message;
     * nested exception is java.io.IOException: Stream closed]
     *
     * @param request
     * @return
     */

    private Object getBody(HttpServletRequest request) {
        BufferedReader bufferedReader = null;
        try {
            AtomicReference<StringBuilder> stringBuilder = new AtomicReference<>(new StringBuilder());
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.get().append(charBuffer, 0, bytesRead);
                }
                return stringBuilder.toString();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }


}
