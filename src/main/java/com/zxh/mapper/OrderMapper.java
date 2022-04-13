package com.zxh.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxh.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    public Long addoneeturnid(@Param(value = "order") Orders order);

}
