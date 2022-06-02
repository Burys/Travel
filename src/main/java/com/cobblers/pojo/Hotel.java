package com.cobblers.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 酒店类
 **/
@Data
@Table(name = "t_cms_hotel")
public class Hotel implements Serializable {
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
    * 酒店名称
    */
    private String hotelName;

    /**
    * 酒店简介
    */
    private String hotelIntro;

    /**
    * 酒店星级
    */
    private Integer hotelStar;

    /**
    * 联系方式
    */
    private String linkPhone;

    /**
    * 地址
    */
    private String address;

    /**
    * 状态
    */
    //0:未发布 1:已发布 2:已撤销
    private Integer state;

    private String imgUrl;

    private Double price;

    private static final long serialVersionUID = 1L;
}
