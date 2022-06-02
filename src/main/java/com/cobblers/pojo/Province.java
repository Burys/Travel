package com.cobblers.pojo;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "t_pz_province")
public class Province implements Serializable {
    /**
     * ID
     */
    //JPA
    @Id
    @Column(name = "id") //主键生成策略
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 省份
     */
    @Column(name = "province")
    private String province;

    private static final long serialVersionUID = 1L;
}
