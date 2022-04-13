package com.zxh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
