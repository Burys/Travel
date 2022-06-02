package com.cobblers.mapper;

import com.cobblers.pojo.Insurance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.CommonsMapper;

import java.util.List;

/**
 *  InsuranceMapper
 **/
@Mapper
public interface InsuranceMapper extends CommonsMapper<Insurance> {
    /**
     * 通过query查询
     * @param query
     * @return
     */
    @Select("SELECT * FROM t_cms_insurance WHERE DELETE_STATUS=0 AND TITLE LIKE #{query,jdbcType=VARCHAR} ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<Insurance> findByQuery(String query);

    /**
     * 查询
     * @return
     */
    @Select("SELECT * FROM t_cms_insurance WHERE DELETE_STATUS=0 AND STATE=1 ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<Insurance> indexList();

    @Select("SELECT count(*) FROM t_cms_insurance WHERE DELETE_STATUS=0 AND STATE=0")
    long state0count();
    @Select("SELECT count(*) FROM t_cms_insurance WHERE DELETE_STATUS=0 AND STATE=1")
    long state1count();
    @Select("SELECT count(*) FROM t_cms_insurance WHERE DELETE_STATUS=0 AND STATE=2")
    long state2count();

    @Select("SELECT count(*) FROM t_cms_insurance WHERE DELETE_STATUS=0 AND INSURANCE_COMPANY=0")
    long company0count();

    @Select("SELECT count(*) FROM t_cms_insurance WHERE DELETE_STATUS=0 AND INSURANCE_COMPANY=1")
    long company1count();
}
