package com.uin.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.server.config.jwt.JwtTokenUtil;
import com.uin.server.mapper.AdminMapper;
import com.uin.server.mapper.AdminRoleMapper;
import com.uin.server.mapper.RoleMapper;
import com.uin.server.pojo.Admin;
import com.uin.server.pojo.AdminRole;
import com.uin.server.pojo.Role;
import com.uin.server.service.IAdminService;
import com.uin.server.utils.AdminUtils;
import com.uin.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * 自定义登陆逻辑
 * 实现UserDetailsService
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

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
     * @date 2021/8/7 9:02
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //登录
        //因为在在Security中重写了userDetailsService方法，让它根据用户输入的username去数据库查询，而不是SpringSecurity中的username
        //去登陆。也就实现了自定义的登陆逻辑。
        //loadUserByUsername
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //!passwordEncoder.matches(password, userDetails.getPassword())
        //数据库中已经加密的密码和用户传过来的明文密码在加密 进行匹配
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        //判断账号是否被禁用
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用");
        }
        //验证验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equals(code)) {
            return RespBean.error("验证码填写错误");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", token);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 实现自定义登陆逻辑 代替SpringSecurity的UserName
     * 从数据库中查询username
     * 根据username获取Admin
     *
     * @param username
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/7 10:01
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", username)
                .eq("enabled", true));
    }

    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 9:51
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有操作员
     *
     * @param keywords
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/12 16:10
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);
    }

    /**
     * 更新操作员的角色
     *
     * @param adminId
     * @param rids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/13 10:24
     */
    @Override
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if (rids.length == result) {
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 更新用户密码
     *
     * @param oldPass
     * @param pass
     * @param adminId
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/20 9:15
     */
    @Override
    public RespBean updatePassWord(String oldPass, String pass, Integer adminId) {
        //要先获取当前adminId的对象，用来比较旧密码和新密码
        // 如果旧密码输入错误的话，就不能修改密码
        Admin admin = adminMapper.selectById(adminId);
        //因为输入密码是明文 所以要用到springsecurity的编码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //判读输入旧密码是否正确
        if (encoder.matches(oldPass, admin.getPassword())) {
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if (result == 1) {
                return RespBean.success("更新密码成功！");
            }
        }
        return RespBean.error("输入原密码不对，请重新输入！");
    }

    /**
     * 更新用户图像
     *
     * @param url
     * @param id
     * @param authentication
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/24 23:53
     */
    @Override
    public RespBean updateAdminUserface(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if (result == 1) {
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            RespBean.success("更新图像成功！", url);
        }
        return RespBean.error("更新图像失败！");
    }

}
