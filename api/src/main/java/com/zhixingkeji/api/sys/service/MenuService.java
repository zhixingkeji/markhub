package com.zhixingkeji.api.sys.service;

import com.zhixingkeji.api.common.dto.MenuDto;
import com.zhixingkeji.api.sys.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */
public interface MenuService extends IService<Menu> {

    List<MenuDto> getCurrentUserNav();
    List<Menu> tree();
}
