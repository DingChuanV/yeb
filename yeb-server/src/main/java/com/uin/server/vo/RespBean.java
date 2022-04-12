package com.uin.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月06日 22:37
 * \* Description: 公共返回对象
 * \
 */

/**
 * 公共返回对象
 *
 * @author wanglufei
 * @date 2022/4/12 9:32 PM
 * @return null
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    //状态码
    private long code;
    //响应消息
    private String message;
    //返回的对象
    private Object obj;

    /**
     * @param message
     * @param
     * @return 成功返回结果
     * @author wanglufei
     * @date 2021/8/6 22:39
     */
    public static RespBean success(String message) {
        return new RespBean(200, message, null);
    }

    /**
     * @param message
     * @param obj
     * @param
     * @return 成功返回结果
     * @author wanglufei
     * @date 2021/8/6 22:42
     */
    public static RespBean success(String message, Object obj) {
        return new RespBean(200, message, obj);
    }

    /**
     * @param message
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/6 22:44
     */
    public static RespBean error(String message) {
        return new RespBean(500, message, null);
    }

    /**
     * @param message
     * @param obj
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/6 22:44
     */
    public static RespBean error(String message, Object obj) {
        return new RespBean(500, message, obj);
    }

}
