package com.cobblers.mapper;

import com.cobblers.pojo.ScenicSpot;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.CommonsMapper;

import java.util.List;

/**
 * 景点mapper
 **/
@Mapper
@Component
public interface ScenicSpotMapper extends CommonsMapper<ScenicSpot> {

    @Select("SELECT * FROM t_cms_scenic_spot WHERE ID = #{id} AND DELETE_STATUS=0")
    @ResultMap("BaseResultMap")
    ScenicSpot findById(@Param("id") String id);


    @Select("SELECT * FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<ScenicSpot> findList();


    @Select("SELECT * FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND STATE=1 ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<ScenicSpot> indexList();


    @Select("SELECT * FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND SPOT_NAME LIKE #{query,jdbcType=VARCHAR} ORDER BY ADD_TIME DESC")
    @ResultMap("BaseResultMap")
    List<ScenicSpot> findListByQuery(String query);


    @Insert("INSERT INTO t_cms_scenic_spot(ID,ADD_USER_ID,ADD_TIME,SPOT_NAME,SPOT_INTRO,SPOT_STAR,SPOT_ADDRESS,OPEN_TIME,TICKETS_MESSAGE,STATE,IMG_URL) VALUES(#{id},#{addUserId},NOW(),#{spotName},#{spotIntro},#{spotStar},#{spotAddress},#{openTime},#{ticketsMessage},#{state},#{imgUrl})")
    void save(ScenicSpot article);


    @Update("UPDATE t_cms_scenic_spot SET MODIFY_USER_ID=#{modifyUserId},MODIFY_TIME=NOW(),SPOT_NAME=#{spotName},SPOT_INTRO=#{spotIntro},SPOT_STAR=#{spotStar},SPOT_ADDRESS=#{spotAddress},OPEN_TIME=#{openTime},TICKETS_MESSAGE=#{ticketsMessage},STATE=#{state},IMG_URL=#{imgUrl} WHERE id=#{id}")
    void update(ScenicSpot article);


    @Update("UPDATE t_cms_scenic_spot SET DELETE_STATUS=1 WHERE id=#{id}")
    void deleteByid(@Param("id") String id);


    @Select("SELECT count(*) FROM t_cms_scenic_spot WHERE DELETE_STATUS=0")
    long count();


    @Select("SELECT count(*) FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND STATE=1")
    long count2();


    @Select("SELECT count(*) FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND STATE=0")
    long state0count();


    @Select("SELECT count(*) FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND STATE=1")
    long state1count();


    @Select("SELECT count(*) FROM t_cms_scenic_spot WHERE DELETE_STATUS=0 AND STATE=2")
    long state2count();
}
