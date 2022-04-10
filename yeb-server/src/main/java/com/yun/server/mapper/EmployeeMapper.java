package com.yun.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.server.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 查询全部员工(分页)
     *
     * @param page
     * @param employee
     * @param beginDateScope
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/17 16:12
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, Employee employee, LocalDate[] beginDateScope);

    /**
     * 查询员工
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/17 16:12
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工工资套账
     *
     * @param page
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/19 15:58
     */
    IPage<Employee> getEmpployeewithSalary(Page<Employee> page);
}
