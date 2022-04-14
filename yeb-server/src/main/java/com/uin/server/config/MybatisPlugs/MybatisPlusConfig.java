package com.uin.server.config.MybatisPlugs;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月13日 10:50
 * \* Description: Mybatis 分页插件配置
 * \
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * @Bean 指示一个方法生成一个由 Spring 容器管理的 bean
     *
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @author wanglufei
     * @date 2022/4/14 8:22 PM
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
