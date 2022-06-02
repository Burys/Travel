package com.cobblers.service;

import com.cobblers.base.PageParam;
import com.cobblers.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户服务接口
 * <p>
 *  //TODO
 *  UserService.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/20 17:00
 * @see  UserService
 **/
public interface UserService {

    /**
     * 没有携带参数，查询用户数据条数
     */
    long count()throws Exception;

    /**
     * 携带参数，查询用户数据条数
     * @param query
     */
    long countByQuery(String query)throws Exception;

    /**
     * 查询用户
     * @param id
     */
    User findById(String id)throws Exception;

    /**
     * 查询用户
     * @param userName
     */
    User findByUserName(String userName)throws Exception;

    /**
     * 保存用户
     * @param user
     */
    void save(User user)throws Exception;

    /**
     * 更新用户
     * @param user
     */
    void update(User user)throws Exception;

    /**
     * 删除用户
     * @param id
     */
    void deleteByid(String id)throws Exception;

    /**
     * 用户登录
     * @param userName
     * @param password
     */
    User login(String userName, String password)throws Exception;

    /**
     * 查询state1
     */
    long state1count();

    /**
     * 查询state2
     */
    long state2count();

    /**
     * 携带参数，分页查找用户
     * @param pageParam
     * @param query
     */
    List<User> getUserByQuery(PageParam<?> pageParam, String query);

    /**
     * 不带参数，分页查找用户
     * @param pageParam
     */
    List<User> getUser(PageParam<?> pageParam);

}
