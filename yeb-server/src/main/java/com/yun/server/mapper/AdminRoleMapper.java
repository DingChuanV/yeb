package com.yun.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.server.pojo.AdminRole;
import com.yun.server.vo.RespBean;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 更新操作员的角色
     *
     * @param adminId
     * @param rids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/13 10:26
     */
    Integer addAdminRole(Integer adminId, Integer[] rids);
}
