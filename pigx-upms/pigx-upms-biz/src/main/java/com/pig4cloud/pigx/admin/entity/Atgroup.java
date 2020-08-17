/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮班组管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:51
 */
@Data
@TableName("atgroup")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "轮班组管理")
public class Atgroup extends Model<Atgroup> {
private static final long serialVersionUID = 1L;

    /**
     * 轮班组编码
     */
    @TableId
    @ApiModelProperty(value="轮班组编码")
    private Integer groupid;
    /**
     * 轮班组名称
     */
    @ApiModelProperty(value="轮班组名称")
    private String title;
    /**
     * 参考日期
     */
    @ApiModelProperty(value="参考日期")
    private String referdate;
    /**
     * 日历类型
     */
    @ApiModelProperty(value="日历类型")
    private Integer xcalendar;
    /**
     * 排班策略
     */
    @ApiModelProperty(value="排班策略")
    private Integer shiftmode;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 有效截止日期
     */
    @ApiModelProperty(value="有效截止日期")
    private String disabledate;
    /**
     * 次序
     */
    @ApiModelProperty(value="次序")
    private Integer xorder;
    /**
     * 轮班周期
     */
    @ApiModelProperty(value="轮班周期")
    private String turndaytype;
    /**
     * 周期
     */
    @ApiModelProperty(value="周期")
    private Integer periods;
    /**
     * 间隔
     */
    @ApiModelProperty(value="间隔")

	@TableField("`interval`")
    private Integer interval;
    /**
     * 参照点(第1次)
     */
    @ApiModelProperty(value="参照点(第1次)")
    private Integer referpoint;
    /**
     * 所属帐套
     */
    @ApiModelProperty(value="所属帐套")
    private Integer agentmode;
    /**
     * 检测
     */
    @ApiModelProperty(value="检测")
    private Integer ischeck;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 重置
     */
    @ApiModelProperty(value="重置")
    private Integer isreset;
    /**
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    private Integer corpid;
    /**
     * 组织编码
     */
    @ApiModelProperty(value="组织编码")
    private String corpcode;

    //private List<AtgroupShift> shiftlist;
	@TableField(exist=false)
	private List<AtgroupTurn> atgroupTurnList;
    }
