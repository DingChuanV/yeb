package com.uin.server.controller;

import com.uin.server.pojo.Admin;
import com.uin.server.service.IAdminService;
import com.uin.server.vo.AdminLogin;
import com.uin.server.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月07日 8:50
 * \* Description: 登录
 * \
 */
//swagger文档注解
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    //相当于我们平时写的注释
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLogin adminLogin,
                          HttpServletRequest request) {
        return adminService.login(
                adminLogin.getUsername(),
                adminLogin.getPassword(),
                adminLogin.getCode(),
                request);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    //在SpringSecurity全局上下文
    //Principal这个是我们通过SpringSecurity登陆之后，在他里面所管理的认证之后的用户信息
    public Admin getAdminInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        //密码不可能给前端
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }
    //主要给前端一个状态码 用户注销的话前端会把请求体中的token删除
    //前后端分离之后 退出登陆 有很多中实现方式
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");
    }

}
