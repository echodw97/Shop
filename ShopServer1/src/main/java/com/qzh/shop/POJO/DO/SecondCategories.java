package com.qzh.shop.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 18:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("second_categories")
public class SecondCategories implements Serializable {

    @TableId(value = "cat_id", type = IdType.AUTO)
    private Integer catId;
    private String catName;
    private Integer catPid;
    private Integer catLevel;
    private Boolean catDeleted;
}
