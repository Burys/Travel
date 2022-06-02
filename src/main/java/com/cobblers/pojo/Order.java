package com.cobblers.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单
 **/
@Data
@Table(name = "t_yw_order")
public class Order implements Serializable {
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
    * 用户ID
    */
    private String userId;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 产品ID
    */
    private String productId;

    /**
    * 产品名称
    */
    private String productName;

    /**
    * 订单费用
    */
    private Double fee;

    /**
    * 产品类型1：旅游 2：酒店
    */
    private Integer productType;

    /**
    * 状态
    */
    private Integer state;

    /**
    * 订单编号
    */
    private String orderCode;

    /**
    * 订单日期
    */
    private String orderTime;

    /**
    * 预定日期
    */
    private String setoffTime;

    /**
    * 联系电话
    */
    private String linkTel;

    /**
    * 人数
    */
    private Integer peopleCount;

    /**
    * 特殊要求
    */
    private String requirement;

    /**
    * 身份证号码
    */
    private String icCode;

    private String imgUrl;

    private static final long serialVersionUID = 1L;
}
