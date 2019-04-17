//package com.nepenthe.demo;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @author lwk
// * @date 2019-04-15 17:58
// */
//@Configuration
//@EnableWebSecurity
//public class LocalSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers(
//                        "/swagger-ui.html",
//                        // swagger api json
//                        "/v2/api-docs",
//                        // 用来获取支持的动作
//                        "/swagger-resources/configuration/ui",
//                        // 用来获取api-docs的URI
//                        "/swagger-resources",
//                        // 安全选项
//                        "/swagger-resources/configuration/security",
//                        "/swagger-resources/**"
//                );
//    }
//
//    /*@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication().passwordEncoder(new LocalEncoder()).withUser("root").password("123456");
//    }*/
//}
//
//class LocalEncoder implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence charSequence) {
//        return String.valueOf(charSequence);
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String s) {
//        return false;
//    }
//}