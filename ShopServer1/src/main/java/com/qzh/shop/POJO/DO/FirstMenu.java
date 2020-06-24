package com.qzh.shop.POJO.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/18 8:43
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("first_menu")
public class FirstMenu implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String authName;
    private String path;
    private List<SecondMenu> children;
}
