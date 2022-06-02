package com.cobblers.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 车票
 **/
@Data
@Table(name = "t_cms_car")
public class Car implements Serializable {
    /**
    * 主键
    */
    @Id
    private String id;

    /**
    * 添加人ID
    */
    private String addUserId;

    /**
    * 添加时间
    */
    private Date addTime;

    /**
    * 删除标志
    */
    //0:未删除 1:已删除
    private Integer deleteStatus;

    /**
    * 修改人ID
    */
    private String modifyUserId;

    /**
    * 修改时间
    */
    private Date modifyTime;

    /**
    * 车票标题
    */
    private String title;

    /**
    * 出发地点
    */
    private String startPlace;

    /**
    * 到达地点
    */
    private String endPlace;

    /**
    * 出发日期跟时间
    */
    private String startDateAndTime;

    /**
    * 需要时间
    */
    private Double needTime;

    /**
    * 上车集中地
    */
    private String gatherPlace;

    /**
    * 车的类型，0是飞机，1是火车，2是汽车
    */
    private Integer type;

    /**
    * 图片
    */
    private String imgUrl;

    //0:未发布 1:已发布 2:已撤销
    private Integer state;

    /**
    * 备注
    */
    private String remark;

    private Double price;

    //使用serialVersionUID常量来简化序列化数据的版本控制
    private static final long serialVersionUID = 1L;
}
