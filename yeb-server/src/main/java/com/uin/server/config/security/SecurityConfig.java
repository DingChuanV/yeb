package com.uin.server.config.security;

import com.uin.server.config.filter.CustomFilter;
import com.uin.server.config.filter.CustomUrlDecisionManager;
import com.uin.server.config.jwt.JwtAuthenticationTokenFilter;
import com.uin.server.pojo.Admin;
import com.uin.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月07日 10:15
 * \* Description: SpringSecurity配置类
 * \
 */
//WebSecurityConfigurerAdapter 自定义登陆界面
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;
    @Autowired
    private CustomFilter customFilter;

    /**
     * 放行一些请求
     *
     * @param web
     * @author wanglufei
     * @date 2021/8/7 16:09
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/ws/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha"
        );
    }

    /**
     * extends WebSecurityConfigurerAdapter
     * 自定义安全防护，解决跨站请求伪造
     *
     * @param http
     * @author wanglufei
     * @date 2022/4/10 7:24 PM
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable() 就相当于关闭防火墙 它就是一种跨域认证
        //因为我们使用了JWT 所以给他关了
        http.csrf().disable()

                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                //授权请求
                .authorizeRequests()
                //允许登录访问
                //.antMatchers("/login", "/logout") //跨域请求先进行一次options请求
                //.permitAll()//除了上面的两个请求其他的都需要认证

                //除了上面的 任何请求都需要认证
                .anyRequest().authenticated()

                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()

                //禁用缓存
                .headers().cacheControl();

        //既然用了JWt我们要添加拦截器
        //添加jwt 登录授权拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //也就是用户没有登陆
        //添加自定义 未授权 和 未登录 结果返回
        http.exceptionHandling()
                //访问拒绝处理程序
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //身份验证入口点
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /**
     * 设置执行自定义认证登录
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //因为我们重写了UserDetailsService，所以我们要在AuthenticationManagerBuilder中配置
        //当我们登陆的时候，他就会走我们自定义的登陆逻辑
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 自定义登陆逻辑-username
     * 重写 UserDetailsService以及loadUserByUsername(username) 实现自定义登陆逻辑
     *
     * @return org.springframework.security.core.userdetails.UserDetailsService
     * @author wanglufei
     * @date 2022/4/10 6:22 PM
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //去数据库查询
            Admin admin = adminService.getAdminByUserName(username);
            if (admin != null) {
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }//否则就抛出用户找不到
            throw new UsernameNotFoundException("用户名或密码不正确");
        };
    }

    /**
     * 自定义登陆逻辑-密码解码和加密
     * 在Spring容器中实现BCryptPasswordEncoder实例
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author wanglufei
     * @date 2022/4/10 6:21 PM
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Jwt 身份验证令牌过滤器
     *
     * @return com.uin.server.config.jwt.JwtAuthenticationTokenFilter
     * @author wanglufei
     * @date 2022/4/11 12:05 PM
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
