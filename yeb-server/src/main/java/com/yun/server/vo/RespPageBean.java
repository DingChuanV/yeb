package com.yun.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月13日 10:49
 * \* Description: 分页公共返回对象
 * \
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     *
     * @param
     * @return
     */
    private Long total;
    /**
     * 数据list
     *
     * @param
     * @return
     */
    private List<?> data;
}
