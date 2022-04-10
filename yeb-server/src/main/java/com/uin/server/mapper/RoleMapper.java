package com.uin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.uin.server.pojo.Role;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 9:52
     */
    List<Role> getRoles(Integer adminId);
}
