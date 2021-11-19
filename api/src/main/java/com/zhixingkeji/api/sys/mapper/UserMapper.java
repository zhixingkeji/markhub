package com.zhixingkeji.api.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixingkeji.api.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2021-11-11
 */

@Mapper
//@Repository
public interface UserMapper extends BaseMapper<User> {

    List<Long> getNavMenuIds(Long userId);

    List<User> listByMenuId(Long menuId);
}
