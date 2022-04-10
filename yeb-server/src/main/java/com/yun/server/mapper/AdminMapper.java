package com.yun.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.server.pojo.Admin;
import com.yun.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 获取所有操作员
     *
     * @param id
     * @param keywords
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/12 16:13
     */
    List<Admin> getAllAdmins(Integer id, String keywords);
}
