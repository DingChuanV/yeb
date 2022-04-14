package com.uin.server.controller;


import com.uin.server.pojo.Position;
import com.uin.server.service.IPositionService;
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
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    /**
     * 获取职位信息
     *
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:16
     */
    @ApiOperation(value = "获取职位信息")
    @GetMapping("/")
    private List<Position> getAllPosition() {
        return positionService.list();
    }

    /**
     * 添加职位信息
     *
     * @param position
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:15
     */
    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    private RespBean addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        //插入一条记录（选择字段，策略插入）
        if (positionService.save(position)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * 修改职位信息
     *
     * @param position
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:16
     */
    @ApiOperation(value = "修改职位信息")
    @PutMapping("/")
    private RespBean updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    /**
     * 删除职位信息
     *
     * @param id
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:16
     */
    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    private RespBean deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 批量删除职位信息
     *
     * @param ids
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:17
     */
    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids) {
        //删除（根据ID 批量删除）
        //Params:
        //idList – 主键ID列表
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }

}
