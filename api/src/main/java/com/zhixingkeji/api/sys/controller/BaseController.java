package com.zhixingkeji.api.sys.controller;

import com.zhixingkeji.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhixingkeji.api.sys.service.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    UserRoleService userRoleService;


    /**
     * 获取页面
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req, "cuurent", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 10);

        return new Page(current, size);
    }
}
