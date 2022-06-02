package com.cobblers.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 保险
 **/
@Data
@Table(name = "t_cms_insurance")
public class Insurance implements Serializable {
    @Id
    private String id;

    private String addUserId;

    private Date addTime;

    private Integer deleteStatus;

    private String modifyUserId;

    private Date modifyTime;

    /**
    * 保险公司
    */
    private String title;

    //1:中国人寿 0:中国平安
    private Integer insuranceCompany;

    private Double price;

    private Integer type;

    private String resume;

    //0:未发布 1:已发布 2:已撤销
    private Integer state;

    private String imgUrl;

    private static final long serialVersionUID = 1L;
}
