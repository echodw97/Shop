package com.qzh.shop.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/17 18:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("first_categories")
public class FirstCategories {

    @TableId(value = "cat_id", type = IdType.AUTO)
    private Integer catId;
    private String catName;
    private Integer catPid;
    private Integer catLevel;
    private Boolean catDeleted;
    private List<SecondCategories> children;

}
