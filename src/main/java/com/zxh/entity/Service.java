package com.zxh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxh
 * @since 2022-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "server_id", type = IdType.AUTO)
    private Long serverId;

    private Long userId;

    private Long orderId;

    private Long goodsId;

    private Integer orderStatus;

    private Integer serviceCage;

    private Integer serviceNumber;

    private Float servicePrice;

    private Integer serviceStatus;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
