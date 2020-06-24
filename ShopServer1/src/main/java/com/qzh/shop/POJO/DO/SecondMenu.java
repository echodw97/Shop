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
 * @Date 2020/5/18 8:48
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("second_menu")
public class SecondMenu implements Serializable {

    @TableId(value = "s_id", type = IdType.AUTO)
    private Integer sId;
    private String sAuthName;
    private String sPath;
    private Integer sOneMenu;

}
