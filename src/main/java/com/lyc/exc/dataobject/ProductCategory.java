package com.lyc.exc.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lyc94 on 2017/12/4.
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /** 类目 id */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /** 品类名称 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(){}
}
