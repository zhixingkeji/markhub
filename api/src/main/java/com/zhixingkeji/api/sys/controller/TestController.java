package com.zhixingkeji.api.sys.controller;

import com.zhixingkeji.api.common.ResultTemplate;
import com.zhixingkeji.api.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserService userService;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test")
    public ResultTemplate test() {
        return ResultTemplate.success(userService.list());
    }

    @GetMapping("/pass")
    public ResultTemplate pass() {

        // 加密后密码
        String password = bCryptPasswordEncoder.encode("root");

        return ResultTemplate.success(password);
    }


    //此方法只有admin角色才能访问
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/role")
    public ResultTemplate role() {
        System.out.println("角色拥有admin权限");
        return ResultTemplate.success("成功访问了有角色权限的页面");
    }


    //此方法只有sys权限才能访问
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/auth")
    public ResultTemplate auth() {

        System.out.println("用户拥有sys:user:list权限");
        return ResultTemplate.success("成功访问了有操作权限的页面");
    }


}
