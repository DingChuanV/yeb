package com.yun.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.server.mapper.DepartmentMapper;
import com.yun.server.pojo.Department;
import com.yun.server.service.IDepartmentService;
import com.yun.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    /**
     * 获取所有部门
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 14:59
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
     * 添加部门
     *
     * @param dep
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 15:56
     */
    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        baseMapper.addDep(dep);
        if (1 == dep.getResult()) {
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除部门
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/11 16:18
     */
    @Override
    public RespBean deleteDep(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        if (-2==dep.getResult()) {
            return RespBean.error("删除失败，该部门下还有子部门！");
        }
        if (-1==dep.getResult()) {
            return RespBean.error("删除失败，该部门下还有员工！");
        }
        if (1==dep.getResult()) {
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
