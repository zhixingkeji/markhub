package com.zhixingkeji.api.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixingkeji.api.common.Const;
import com.zhixingkeji.api.common.ResultTemplate;
import com.zhixingkeji.api.common.dto.PassDto;
import com.zhixingkeji.api.sys.model.Role;
import com.zhixingkeji.api.sys.model.User;
import com.zhixingkeji.api.sys.model.UserRole;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController{
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //用户注册
    @GetMapping("/register")
    public String register(User user){
        try {
            userService.register(user);
            return "注册成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "注册失败";
        }
    }


    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResultTemplate info(@PathVariable("id") Long id) {

        User user = userService.getById(id);
        Assert.notNull(user, "找不到该管理员");

        List<Role> roles = roleService.listRolesByUserId(id);

        user.setRoles(roles);
        return ResultTemplate.success(user);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public ResultTemplate list(String username) {

        Page<User> pageData = userService.page(getPage(), new QueryWrapper<User>()
                .like(StrUtil.isNotBlank(username), "username", username));

        pageData.getRecords().forEach(u -> {

            u.setRoles(roleService.listRolesByUserId(u.getId()));
        });

        return ResultTemplate.success(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public ResultTemplate save(@Validated @RequestBody User user) {

        user.setCreated(LocalDateTime.now());
        user.setStatus(Const.STATUS_ON);

        // 默认密码
        String password = bCryptPasswordEncoder.encode(Const.DEFULT_PASSWORD);
        user.setPassword(password);

        // 默认头像
        user.setAvatar(Const.DEFULT_AVATAR);

        userService.save(user);
        return ResultTemplate.success(user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public ResultTemplate update(@Validated @RequestBody User user) {

        user.setUpdated(LocalDateTime.now());

        userService.updateById(user);
        return ResultTemplate.success(user);
    }

    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResultTemplate delete(@RequestBody Long[] ids) {

        userService.removeByIds(Arrays.asList(ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", ids));

        return ResultTemplate.success("");
    }

    @Transactional
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public ResultTemplate rolePerm(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {

        List<UserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(r);
            userRole.setUserId(userId);

            userRoles.add(userRole);
        });

        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        userRoleService.saveBatch(userRoles);

        // 删除缓存
        User user = userService.getById(userId);
        userService.clearUserAuthorityInfo(user.getUsername());

        return ResultTemplate.success("");
    }

    @PostMapping("/repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public ResultTemplate repass(@RequestBody Long userId) {

        User user = userService.getById(userId);

        user.setPassword(bCryptPasswordEncoder.encode(Const.DEFULT_PASSWORD));
        user.setUpdated(LocalDateTime.now());

        userService.updateById(user);
        return ResultTemplate.success("");
    }






    //用户修改密码
    @PostMapping("/updatePass")
    public ResultTemplate updatePass(@Validated @RequestBody PassDto passDto, Principal principal) {

        User user = userService.getByUsername(principal.getName());

        boolean matches = bCryptPasswordEncoder.matches(passDto.getCurrentPass(), user.getPassword());
        if (!matches) {
            return ResultTemplate.fail("旧密码不正确");
        }

        user.setPassword(bCryptPasswordEncoder.encode(passDto.getPassword()));
        user.setUpdated(LocalDateTime.now());

        userService.updateById(user);
        return ResultTemplate.success("");
    }

}
