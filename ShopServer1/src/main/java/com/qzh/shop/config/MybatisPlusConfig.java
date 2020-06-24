package com.qzh.shop.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Ruoany
 * @Description MybatisPlus配置类
 * @Date 2020/5/14 19:32
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 注入MybatisPlus的分页插件
     * */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
