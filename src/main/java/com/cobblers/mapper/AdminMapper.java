package com.cobblers.mapper;

import com.cobblers.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.CommonsMapper;

import java.util.List;

/**
 * AdminMapper
 **/
@Mapper
public interface AdminMapper extends CommonsMapper<Admin> {

    /**
     * 模糊查询
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM t_pz_admin_user WHERE DELETE_STATUS=0 AND " +
            "USER_NAME LIKE #{keyword,jdbcType=VARCHAR} ORDER BY ADD_TIME DESC")
    List<Admin>  findListByQuery(String keyword);

}
