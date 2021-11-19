package com.zhixingkeji.api.sys.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixingkeji.api.common.Const;
import com.zhixingkeji.api.common.ResultTemplate;
import com.zhixingkeji.api.sys.model.Role;
import com.zhixingkeji.api.sys.model.RoleMenu;
import com.zhixingkeji.api.sys.model.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController{

//    获取详情
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public ResultTemplate info(@PathVariable("id") Long id) {

        Role role = roleService.getById(id);

        // 获取角色相关联的菜单id
        List<RoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());

        role.setMenuIds(menuIds);
        return ResultTemplate.success(role);
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public ResultTemplate list(String name) {

        Page<Role> pageData = roleService.page(getPage(),
                new QueryWrapper<Role>()
                        .like(StrUtil.isNotBlank(name), "name", name)
        );

        return ResultTemplate.success(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public ResultTemplate save(@Validated @RequestBody Role role) {

        role.setCreated(LocalDateTime.now());
        role.setStatu(Const.STATUS_ON);

        roleService.save(role);
        return ResultTemplate.success(role);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResultTemplate update(@Validated @RequestBody Role role) {

        role.setUpdated(LocalDateTime.now());

        roleService.updateById(role);

        // 更新缓存
        userService.clearUserAuthorityInfoByRoleId(role.getId());

        return ResultTemplate.success(role);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    public ResultTemplate info(@RequestBody Long[] ids) {

        roleService.removeByIds(Arrays.asList(ids));

        // 删除中间表
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id", ids));
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id", ids));

        // 缓存同步删除
        Arrays.stream(ids).forEach(id -> {
            // 更新缓存
            userService.clearUserAuthorityInfoByRoleId(id);
        });

        return ResultTemplate.success("");
    }

    @Transactional
    @PostMapping("/perm/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public ResultTemplate info(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {

        List<RoleMenu> roleMenus = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);

            roleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        roleMenuService.saveBatch(roleMenus);

        // 删除缓存
        userService.clearUserAuthorityInfoByRoleId(roleId);

        return ResultTemplate.success(menuIds);
    }
}
