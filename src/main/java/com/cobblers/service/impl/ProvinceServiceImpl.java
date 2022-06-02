package com.cobblers.service.impl;
/**
 * 省份信息服务实现
 * <p>
 *  //TODO
 *  ProvinceServiceImpl.java
 * </p>
 * @version v1.0.0
 * @author 佐斯特勒
 * @date 2020/7/19 21:55
 * @see  ProvinceServiceImpl
 **/
import com.cobblers.mapper.ProvinceMapper;
import com.cobblers.service.ProvinceService;
import com.cobblers.vo.ProvinceVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceMapper provinceMapper;

    @Override
    public List<ProvinceVO> getProvinceCount() {
        return provinceMapper.countProvince();
    }
}
