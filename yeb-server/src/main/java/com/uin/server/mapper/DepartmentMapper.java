package com.uin.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uin.server.pojo.Department;
import com.uin.server.vo.RespBean;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface DepartmentMapper extends BaseMapper<Department> {
    /**
     * 获取所有部门
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 15:01
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 增加部门
     *
     * @param dep
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 15:58
     */
    void addDep(Department dep);

    /**
     * 删除部门
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 16:18
     */
    RespBean deleteDep(Department dep);
}
