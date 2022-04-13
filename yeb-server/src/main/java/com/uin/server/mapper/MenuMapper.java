package com.uin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.uin.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过AdminId查询menu
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/8 18:19
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 8:57
     */
    List<Menu> getMenusWithRole();

    /**
     * 获取所有的菜单
     * @author wanglufei
     * @date 2022/4/13 7:54 PM
     * @return java.util.List<com.uin.server.pojo.Menu>
     */
    List<Menu> getAllMenus();
}
