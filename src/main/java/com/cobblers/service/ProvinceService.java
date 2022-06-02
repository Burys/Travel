package com.cobblers.service;

import com.cobblers.vo.ProvinceVO;

import java.util.List;

public interface ProvinceService {
    /**
     * 获取城市类型和计数
     */
    List<ProvinceVO> getProvinceCount();
}
