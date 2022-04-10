package com.yun.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.server.pojo.Admin;
import com.yun.server.pojo.Role;
import com.yun.server.vo.RespBean;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/7 9:00
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 通过username获取管理员的信息
     *
     * @param username
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/8 14:42
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 9:50
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 获取所有操作员
     *
     * @param keywords
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/12 16:09
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员的角色
     *
     * @param adminId
     * @param rids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/13 10:23
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * 更新用户密码
     *
     * @param oldPass
     * @param pass
     * @param adminId
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/20 9:14
     */
    RespBean updatePassWord(String oldPass, String pass, Integer adminId);

    /**
     * 更新用户图像
     *
     * @param url
     * @param id
     * @param authentication
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/24 23:52
     */
    RespBean updateAdminUserface(String url, Integer id, Authentication authentication);
}
