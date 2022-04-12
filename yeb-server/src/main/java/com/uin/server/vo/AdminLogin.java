package com.uin.server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月07日 8:32
 * \* Description: 用户登录实体类 专门用来处理前端传给我们的用户名和密码
 * \
 */
@Data
//https://www.cnblogs.com/binyue-sunmk/p/12632037.html
@EqualsAndHashCode(callSuper = false)
//开启链式编程
@Accessors(chain = true)
//用于前后端分离的接口文档
@ApiModel(value = "AdminLogin对象",description = "用户登陆实体类")
public class AdminLogin {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
