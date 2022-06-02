package com.cobblers.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cobblers.pojo.Car;
import com.cobblers.mapper.CarMapper;
import com.cobblers.service.CarService;
import com.cobblers.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.MapperUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 交通业务实现类
 **/
@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;

    @Override
    public long count() {
        var example = new Example(Car.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0);
        return carMapper.selectCountByExample(example);
    }

    @Override
    public Car findById(String id) {
        var example = new Example(Car.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0)
                .andEqualTo("id",id);
        return carMapper.selectOneByExample(example);
    }

    @Override
    public List<Car> findList() {
        var example = new Example(Car.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0);
        example.orderBy("addTime").desc();
        return carMapper.selectByExample(example);
    }

    @Override
    public void save(Car car) {
        car.setAddTime(new Date());
        carMapper.insertSelective(car);
    }

    @Override
    public void update(Car car) {
        car.setModifyTime(new Date());
        var id = car.getId();
        car.setId(null);
        var example = new Example(Car.class);
        example.createCriteria()
                .andEqualTo("deleteStatus",0)
                .andEqualTo("id",id);
        carMapper.updateByExampleSelective(car,example);
    }

    @Override
    public void deleteById(String id) {
        var car = new Car();
        car.setId(id);
        car.setDeleteStatus(1);
        carMapper.updateByPrimaryKeySelective(car);
    }

    @Override
    public List<Car> findByPage(int currentPage, int pageSize, String query) {
        List<Car> list;
        PageHelper.startPage(currentPage, pageSize);
        if (Tools.notEmpty(query)) {
            list = carMapper.findByQuery("%" + query + "%");
        } else {
            list = findList();
        }
        var pageInfo=new PageInfo<Car>(list);
        return pageInfo.getList();
    }

    @Override
    public List<Car> findByPage(int currentPage, int pageSize) {
        List<Car> list;
        PageHelper.startPage(currentPage, pageSize);
        list = carMapper.indexList();
        var pageInfo=new PageInfo<Car>(list);
        return pageInfo.getList();
    }

    @Override
    public long state0count()throws Exception{
        return  carMapper.state0count();
    }
    @Override
    public long state1count()throws Exception{
        return  carMapper.state1count();
    }
    @Override
    public long state2count()throws Exception{
        return  carMapper.state2count();
    }
    @Override
    public long type0count()throws Exception{
        return  carMapper.type0count();
    }
    @Override
    public long type1count()throws Exception{
        return  carMapper.type1count();
    }
    @Override
    public long type2count()throws Exception{
        return  carMapper.type2count();
    }

}
