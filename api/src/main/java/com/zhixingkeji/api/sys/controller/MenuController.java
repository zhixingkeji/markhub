package com.zhixingkeji.api.sys.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixingkeji.api.common.ResultTemplate;
import com.zhixingkeji.api.common.dto.MenuDto;
import com.zhixingkeji.api.sys.model.Menu;
import com.zhixingkeji.api.sys.model.RoleMenu;
import com.zhixingkeji.api.sys.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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
@RequestMapping("/sys/menu")
public class MenuController extends BaseController{

//    获取左侧菜单列表
    @GetMapping("/nav")
    public ResultTemplate nav(Principal principal) {
        User user = userService.getByUsername(principal.getName());

//         获取权限信息
        String authorityInfo = userService.getUserAuthorityInfo(user.getId());// ROLE_admin,ROLE_normal,sys:user:list,....
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<MenuDto> navs = menuService.getCurrentUserNav();

        return ResultTemplate.success(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map()
        );
    }


//    菜单管理界面获取菜单数据
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public ResultTemplate info(@PathVariable(name = "id") Long id) {
        return ResultTemplate.success(menuService.getById(id));
    }

    //    菜单管理界面获取菜单数据
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public ResultTemplate list() {

        List<Menu> menus = menuService.tree();
        return ResultTemplate.success(menus);
    }



    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public ResultTemplate save(@Validated @RequestBody Menu menu) {

        menu.setCreated(LocalDateTime.now());

        menuService.save(menu);
        return ResultTemplate.success(menu);
    }



    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public ResultTemplate update(@Validated @RequestBody Menu menu) {

        menu.setUpdated(LocalDateTime.now());

        menuService.updateById(menu);

        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(menu.getId());
        return ResultTemplate.success(menu);
    }



    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public ResultTemplate delete(@PathVariable("id") Long id) {

        int count = menuService.count(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0) {
            return ResultTemplate.fail("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(id);

        menuService.removeById(id);

        // 同步删除中间关联表
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("menu_id", id));
        return ResultTemplate.success("");
    }

}
