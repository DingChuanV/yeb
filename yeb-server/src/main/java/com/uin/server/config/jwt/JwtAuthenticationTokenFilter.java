package com.uin.server.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月07日 11:00
 * \* Description:对JWT的令牌（token）判断
 * Jwt 身份验证令牌过滤器
 * \
 */

/**
 * 用户成功登陆之后，每次发起请求都会先经过这个拦截器，判断用户请求体中的token是否有效
 * 判断不通过之后我们会对他进行一个拦截，不让它进行其他的请求
 * 判断通过之后，我们就会（验证通过了）允许它去访问其他资源
 */

//OncePerRequestFilter 一次请求过滤器
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 重写 doFilterInternal
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @author wanglufei
     * @date 2022/4/12 11:34 PM
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //我们正常前端请求的时候 key：Authorization
        //                   value：Bearer（此处有一个空隔） 具体的Jwt token
        //

        String authHeader = httpServletRequest.getHeader(tokenHeader);
        //判断是否存在Bearer  并且 authHeader是否是以Bearer 开头
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            //
            String authToken = authHeader.substring(tokenHead.length());
            //根据Bearer 获取 用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            //token存在,但是未登录
            //如果username不为null 并且SpringSecurity中的全局上下文为null 说明是用户的第一次登陆 所以接下来让他登陆
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //此操作就相当于登录 是SpringSecurity中的登陆方法 我们自定义登陆的关键所在
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //验证token是否有效,重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    //现在将用户放到SpringSecurity全局上下文中 要不然就一直
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
