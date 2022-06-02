package com.cobblers.service.impl;

import com.cobblers.base.PageParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cobblers.pojo.User;
import com.cobblers.mapper.UserMapper;
import com.cobblers.service.UserService;
import com.cobblers.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * 用户业务类
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     *没有携带参数，查询用户数据条数
     */
    @Override
    public long count() throws Exception {
        return userMapper.count();
    }

    /**
     * 携带参数，查询用户数据条数
     * @param query
     */
    @Override
    public long countByQuery(String query)throws Exception{
        return userMapper.countByQuery("%"+query+"%");
    }

    @Override
    public User findById(String id) throws Exception {
        return userMapper.findById(id);
    }

    @Override
    public User findByUserName(String userName) throws Exception {
        return userMapper.findByUserName(userName);
    }

    @Override
    public void save(User user) throws Exception {
        user.setAddTime(new Date());
        user.setDeleteStatus(0);
        userMapper.insert(user);
    }

    @Override
    public void update(User user) throws Exception {
        user.setModifyTime(new Date());
        userMapper.update(user);
    }

    @Override
    public void deleteByid(String id) throws Exception {
        userMapper.deleteByid(id);
    }

    /**
     * 携带参数查找用户
     * @param query
     * @Param pageParam
     */
    @Override
    public List<User> getUserByQuery(PageParam<?> pageParam, String query){
        PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize());
        List<User> userList = userMapper.findListByQuery("%"+query+"%");
        var pageInfo = new PageInfo<>(userList);
        return pageInfo.getList();
    }

    @Override
    public List<User> getUser(PageParam<?> pageParam){
        PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize());
        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public User login(String userName, String password) throws Exception {
        return userMapper.login(userName,password);
    }

    @Override
    public long state1count() {
        return userMapper.state1count();
    }

    @Override
    public long state2count() {
        return userMapper.state2count();
    }
}
