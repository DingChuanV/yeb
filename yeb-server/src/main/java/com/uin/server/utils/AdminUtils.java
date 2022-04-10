package com.uin.server.utils;

import com.uin.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月12日 16:12
 * \* Description:
 * \
 */
public class AdminUtils {
    public static Admin getCurrentAdmin() {
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
