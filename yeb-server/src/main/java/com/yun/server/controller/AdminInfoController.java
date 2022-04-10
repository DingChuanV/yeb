package com.yun.server.controller;

import com.yun.server.pojo.Admin;
import com.yun.server.service.IAdminService;
import com.yun.server.utils.FastdfsUtils;
import com.yun.server.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月20日 8:57
 * \* Description: 个人中心
 * \
 */
@RestController

public class AdminInfoController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "更新当前用户对象信息")
    @PutMapping("/admin/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updatePass(@RequestBody Map<String, Object> map) {
        String oldPass = (String) map.get("oldPass");
        String pass = (String) map.get("Pass");
        Integer adminId = (Integer) map.get("adminId");
        return adminService.updatePassWord(oldPass, pass, adminId);
    }

    @ApiOperation("更新用户图像")
    @PutMapping("/admin/userface")
    public RespBean updateAdminUserface(MultipartFile multipartFile, Integer id,
                                        Authentication authentication) {
        String[] upload = FastdfsUtils.upload(multipartFile);
        String url = FastdfsUtils.getTrackerUrl() + upload[0] + '/' + upload[1];
        return adminService.updateAdminUserface(url,id,authentication);

    }
}
