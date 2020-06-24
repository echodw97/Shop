package com.qzh.shop.POJO.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/13 15:58
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T,S> implements Serializable {

    private T data;
    private S meta;

    public Result(S meta){
      this.meta = meta;
    }
}
