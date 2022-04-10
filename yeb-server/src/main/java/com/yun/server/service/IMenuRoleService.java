package com.yun.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.server.pojo.MenuRole;
import com.yun.server.vo.RespBean;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 19:26
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
