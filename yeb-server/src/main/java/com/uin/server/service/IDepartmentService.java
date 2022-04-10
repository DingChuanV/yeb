package com.uin.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uin.server.pojo.Department;
import com.uin.server.vo.RespBean;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 14:59
     */
    List<Department> getAllDepartments();

    /**
     * 增加部门
     *
     * @param dep
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 15:55
     */
    RespBean addDep(Department dep);

    /**
     * 删除部门
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 16:17
     */
    RespBean deleteDep(Integer id);
}
