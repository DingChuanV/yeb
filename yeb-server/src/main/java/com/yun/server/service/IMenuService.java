package com.yun.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 通过AdminId查询menu
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/8 18:10
     */
    List<Menu> getMenusByAdminId();

    /**
     * 根据角色获取菜单列表
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 8:55
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 19:25
     */
    List<Menu> getAllMenus();
}
