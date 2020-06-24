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
 * @Date 2020/6/14 13:18
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("parameter")
public class Parameter implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String parameter;
    private Integer pId;
}
