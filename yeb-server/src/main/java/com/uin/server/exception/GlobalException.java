package com.uin.server.exception;

import com.uin.server.vo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月09日 15:30
 * \* Description:全局异常处理
 * \
 */
@RestControllerAdvice
public class GlobalException {
    /**
     * @param e
     * @param
     * @return
     * @author wanglufei
     * @date 2021/8/9 15:34
     */
    @ExceptionHandler(SQLException.class)
    public RespBean mySQLException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
