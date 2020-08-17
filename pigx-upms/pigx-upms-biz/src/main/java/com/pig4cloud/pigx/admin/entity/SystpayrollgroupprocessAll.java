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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 薪资流程查看历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:12:19
 */
@Data
@TableName("systpayrollgroupprocess_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资流程查看历史")
public class SystpayrollgroupprocessAll extends Model<SystpayrollgroupprocessAll> {
private static final long serialVersionUID = 1L;

    /**
     * 月份
     */
    @ApiModelProperty(value="月份")
    private String term;
    /**
     * 薪资账套
     */
    @TableId
    @ApiModelProperty(value="薪资账套")
    private Integer gid;
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private String begindate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String enddate;
    /**
     * 初始化
     */
    @ApiModelProperty(value="初始化")
    private Integer initialized;
    /**
     * 初始人
     */
    @ApiModelProperty(value="初始人")
    private Integer initializedby;
    /**
     * 初始化时间
     */
    @ApiModelProperty(value="初始化时间")
    private String initializedtime;
    /**
     * 已计算
     */
    @ApiModelProperty(value="已计算")
    private Integer submit;
    /**
     * 计算人
     */
    @ApiModelProperty(value="计算人")
    private Integer submitby;
    /**
     * 计算时间
     */
    @ApiModelProperty(value="计算时间")
    private String submittime;
    /**
     * 签发
     */
    @ApiModelProperty(value="签发")
    private Integer confirm;
    /**
     * 签发人
     */
    @ApiModelProperty(value="签发人")
    private Integer confirmby;
    /**
     * 签发时间
     */
    @ApiModelProperty(value="签发时间")
    private String confirmtime;
    /**
     * 已封帐
     */
    @ApiModelProperty(value="已封帐")
    private Integer closed;
    /**
     * 封账人
     */
    @ApiModelProperty(value="封账人")
    private Integer closedby;
    /**
     * 封账时间
     */
    @ApiModelProperty(value="封账时间")
    private String closedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer corpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String corpcode;
    }
