package com.uin.server.controller;


import com.uin.server.pojo.Joblevel;
import com.uin.server.service.IJoblevelService;
import com.uin.server.vo.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanglufei
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    /**
     * 查询所有职称
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 16:13
     */
    @ApiOperation(value = "查询所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevel() {
        //查询所有
        return joblevelService.list();
    }

    /**
     * 添加职称
     *
     * @param joblevel
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 16:13
     */
    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * 修改职称
     *
     * @param joblevel
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 16:14
     */
    @ApiOperation(value = "修改职称")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel) {
        System.out.println(joblevel);
        //根据 ID 选择修改
        if (joblevelService.updateById(joblevel)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");

    }

    /**
     * 删除职称
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 16:14
     */
    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id) {
        //根据 ID 删除
        if (joblevelService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 批量删除职称
     *
     * @param ids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 16:14
     */
    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteJoblevelByIds(Integer[] ids) {
        //removeByIds批量删除职称
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
