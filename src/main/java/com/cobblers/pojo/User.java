package com.cobblers.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户类
 **/

@Table(name = "t_pz_user")
@Data
public class User implements Serializable {
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
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String linkTel;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String icCode;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 省份
     */
    private Integer province;

    private static final long serialVersionUID = 1L;
}
