package com.uin.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uin.server.pojo.Employee;
import com.uin.server.vo.RespBean;
import com.uin.server.vo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
public interface IEmployeeService extends IService<Employee> {
    /**
     * 获取所有员工
     *
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/13 11:11
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取最大工号
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/13 15:49
     */
    RespBean maxWorkId();

    /**
     * 添加员工
     *
     * @param employee
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/17 10:30
     */
    RespBean insertEmployee(Employee employee);

    /**
     * 查询员工
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/17 16:08
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工工资套账
     *
     * @param currentPage
     * @param size
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/19 15:53
     */
    RespPageBean getEmployeewithSalary(Integer currentPage, Integer size);
}
