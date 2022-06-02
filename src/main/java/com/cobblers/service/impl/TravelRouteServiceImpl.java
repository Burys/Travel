package com.cobblers.service.impl;

import com.cobblers.service.TravelRouteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cobblers.pojo.TravelRoute;
import com.cobblers.mapper.TravelRouteMapper;
import com.cobblers.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * <标题>
 * <p>
 *  //TODO
 *  TravelRouteServiceImpl.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/20 22:40
 * @see  TravelRouteServiceImpl
 **/
@Service
@Slf4j
public class TravelRouteServiceImpl implements TravelRouteService {
    @Resource
    private TravelRouteMapper travelRouteMapper;

    @Override
    public long count(String query) throws Exception {
        if(Tools.notEmpty(query)){
            // return travelRouteMapper.countByQuery("%"+query+"%");
        }
        return travelRouteMapper.count();
    }

    @Override
    public long count2() throws Exception {
        return travelRouteMapper.count2();
    }

    @Override
    public TravelRoute findById(String id) throws Exception {
        return travelRouteMapper.findById(id);
    }

    @Override
    public List<TravelRoute> findList() throws Exception {
        return travelRouteMapper.findList();
    }

    @Override
    public void save(TravelRoute travelRoute) throws Exception {
        System.out.println(travelRoute);
        travelRoute.setDeleteStatus(0);
        travelRoute.setState(1);
        travelRoute.setAddTime(new Date());
        travelRouteMapper.save(travelRoute);
    }

    @Override
    public void update(TravelRoute travelRoute) throws Exception {
        travelRouteMapper.update(travelRoute);
    }

    @Override
    public void deleteById(String id) throws Exception {
        travelRouteMapper.deleteById(id);
    }

    @Override
    public List<TravelRoute> findByPage(int currentPage, int pageSize, String query) throws Exception {
        List<TravelRoute> list;
        PageHelper.startPage(currentPage, pageSize);
        if (Tools.notEmpty(query)) {
            list = travelRouteMapper.findListByQuery("%" + query + "%");
        } else {
            list = travelRouteMapper.findList();
        }
        var pageInfo=new PageInfo<TravelRoute>(list);
        return pageInfo.getList();
    }

    @Override
    public List<TravelRoute> findByPage(int currentPage, int pageSize) throws Exception {
        List<TravelRoute> list;
        PageHelper.startPage(currentPage, pageSize);
        list = travelRouteMapper.indexList();
        var pageInfo=new PageInfo<TravelRoute>(list);
        return pageInfo.getList();
    }

    @Override
    public long state0count()throws Exception{
        return travelRouteMapper.state0count();
    }

    @Override
    public long state1count()throws Exception{
        return travelRouteMapper.state1count();
    }

    @Override
    public long state2count()throws Exception{
        return travelRouteMapper.state2count();
    }

}
