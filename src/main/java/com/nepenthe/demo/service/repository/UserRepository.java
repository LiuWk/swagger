package com.nepenthe.demo.service.repository;

import com.nepenthe.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lwk
 * @date 2019-05-08 10:39
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    User findUserByMobile(String mobile);
    
    /**
     * 更新最近登录时间
     *
     * @param userId
     */
    @Query(value = "UPDATE USER  SET last_login_time = SYSDATE() WHERE id = ?1 ", nativeQuery = true)
    void updateLastLoginTimeById(Integer userId);
}
