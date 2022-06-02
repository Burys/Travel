package com.cobblers.mapper;

import com.cobblers.pojo.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.CommonsMapper;

import java.util.List;

/**
 * UserMapper
 **/
@Mapper
public interface UserMapper extends CommonsMapper<User> {

    /**
     * 分页查询用户列表
     * @param query
     * @return
     */
    @Select("SELECT * FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_NAME LIKE #{query,jdbcType=VARCHAR} ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<User> findListByQuery(@Param("query")String query);

    //搜索查询到的用户数量
    @Select("SELECT count(*) FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_NAME LIKE #{query,jdbcType=VARCHAR}")
    long countByQuery(@Param("query")String query);

    //添加用户
    @Insert("INSERT INTO t_pz_user(ID,ADD_USER_ID,ADD_TIME,DELETE_STATUS,MODIFY_USER_ID,MODIFY_TIME,USER_NAME,PASSWORD,LINK_TEL,NAME,IC_CODE,STATE,PROVINCE) VALUES(#{id},#{addUserId},#{addTime},#{deleteTime},#{modifyUserId},#{modifyTime},#{userName},#{password},#{linkTel},#{name},#{icCode},#{state},#{province})")
    void save(User user);

    //删除用户
    @Update("UPDATE t_pz_user SET DELETE_STATUS=1 WHERE ID=#{id}")
    void deleteByid(@Param("id") String id);

    //更新用户
    @Update("UPDATE t_pz_user SET MODIFY_USER_ID=#{modifyUserId},MODIFY_TIME=#{modifyTime},USER_NAME=#{userName},PASSWORD=#{password},LINK_TEL=#{linkTel},NAME=#{name},IC_CODE=#{icCode},STATE=#{state},PROVINCE=#{province} WHERE ID=#{id}")
    void update(User user);

    //登录
    @Select("SELECT * FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_NAME=#{userName} AND PASSWORD=#{userPassword}")
    @ResultMap("BaseResultMap")
    User login(@Param("userName") String userName,@Param("userPassword") String userPassword);

    //查询用户数量
    @Select("SELECT count(*) FROM t_pz_user WHERE DELETE_STATUS=0")
    long count();

    //根据id查询用户
    @Select("SELECT * FROM t_pz_user WHERE DELETE_STATUS=0 AND ID=#{id}")
    @ResultMap("BaseResultMap")
    User findById(@Param("id") String id);

    //通过用户名查询用户
    @Select("SELECT * FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_NAME=#{userName}")
    @ResultMap("BaseResultMap")
    User findByUserName(@Param("userName") String userName);

    //查询所有用户
    @Select("SELECT * FROM t_pz_user WHERE DELETE_STATUS=0 ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<User> findAll();

    //未删除已发布的用户数量
    @Select("SELECT count(*) FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_STATE=1")
    long state1count();

    //未删除已撤销的用户数量
    @Select("SELECT count(*) FROM t_pz_user WHERE DELETE_STATUS=0 AND USER_STATE=2")
    long state2count();




}
