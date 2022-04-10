package com.uin.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.uin.server.pojo.Employee;
import com.uin.server.pojo.Salary;
import com.uin.server.service.IEmployeeService;
import com.uin.server.service.ISalaryService;
import com.uin.server.vo.RespBean;
import com.uin.server.vo.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月18日 23:23
 * \* Description: 员工套账
 * \
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取员工工资套账")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工工资套账")
    @GetMapping("/")
    public RespPageBean getEmployeewithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeewithSalary(currentPage,size);

    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid){
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

}
