package com.uin.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uin.server.vo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月07日 15:01
 * \* Description: 当未登录或者token失效时访问接口时，自定义返回结果
 * \
 */
/**
 * SpringSecurity登陆拦截器 未登陆或token失效自定义返回结果
 * @author wanglufei
 * @date 2022/4/13 8:18 AM
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        //RespBean bean = RespBean.error("RestAuthorizationEntryPoint + 未登录!");
        RespBean bean = RespBean.error("尚未登录，请登录！");
        //401 请求要求用户的身份认证
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
