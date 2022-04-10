package com.uin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uin.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 21:33
     */

    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

}
