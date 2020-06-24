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
 * @Date 2020/5/17 13:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("authority")
public class Authority implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String path;
    private Integer rights;
    private String name;
}
