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
 * 班次管理
 *
 * @author GXS
 * @date 2020-05-25 10:52:00
 */
@Data
@TableName("atshift_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "班次管理")
public class AtshiftType extends Model<AtshiftType> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 班次编码
     */
    @ApiModelProperty(value="班次编码")
    private String shift;
    /**
     * 打卡模式
     */
    @ApiModelProperty(value="打卡模式")
    private Integer type;
    /**
     * 设置打卡时段
     */
    @ApiModelProperty(value="设置打卡时段")
    private Integer issection;
    /**
     * 班次名称
     */
    @ApiModelProperty(value="班次名称")
    private String title;
    /**
     * 上班时间1
     */
    @ApiModelProperty(value="上班时间1")
    private String begintime;
    /**
     * 下班时间1
     */
    @ApiModelProperty(value="下班时间1")
    private String endtime;
    /**
     * 上班时间2
     */
    @ApiModelProperty(value="上班时间2")
    private String begintime2;
    /**
     * 下班时间2
     */
    @ApiModelProperty(value="下班时间2")
    private String endtime2;
    /**
     * 上班时间3
     */
    @ApiModelProperty(value="上班时间3")
    private String begintime3;
    /**
     * 上班时间3
     */
    @ApiModelProperty(value="上班时间3")
    private String endtime3;
    /**
     * 设置休息时段
     */
    @ApiModelProperty(value="设置休息时段")
    private Integer isrest;
    /**
     * 休息开始时间
     */
    @ApiModelProperty(value="休息开始时间")
    private String restbegintime;
    /**
     * 休息结束时间
     */
    @ApiModelProperty(value="休息结束时间")
    private String restendtime;
    /**
     * 打卡有效开始时间
     */
    @ApiModelProperty(value="打卡有效开始时间")
    private String scanbegintime;
    /**
     * 打卡有效结束时间
     */
    @ApiModelProperty(value="打卡有效结束时间")
    private String scanendtime;
    /**
     * 上班时数
     */
    @ApiModelProperty(value="上班时数")
    private Double xhours;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer xorder;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 隐藏
     */
    @ApiModelProperty(value="隐藏")
    private Integer submit;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
    /**
     * 考勤账套
     */
    @ApiModelProperty(value="考勤账套")
    private Integer agentmode;
    /**
     * 是否检验
     */
    @ApiModelProperty(value="是否检验")
    private Integer ischeck;
    /**
     * 最大加班小时
     */
    @ApiModelProperty(value="最大加班小时")
    private Double maxot;
    /**
     * 上班时间1扫描开始
     */
    @ApiModelProperty(value="上班时间1扫描开始")
    private String btscanbegintime;
    /**
     * 上班时间1扫描结束
     */
    @ApiModelProperty(value="上班时间1扫描结束")
    private String btscanendtime;
    /**
     * 下班时间1扫描开始
     */
    @ApiModelProperty(value="下班时间1扫描开始")
    private String etscanbegintime;
    /**
     * 下班时间1扫描结束
     */
    @ApiModelProperty(value="下班时间1扫描结束")
    private String etscanendtime;
    /**
     * 上班时间2扫描开始
     */
    @ApiModelProperty(value="上班时间2扫描开始")
    private String btscanbegintime2;
    /**
     * 上班时间2扫描结束
     */
    @ApiModelProperty(value="上班时间2扫描结束")
    private String btscanendtime2;
    /**
     * 下班时间2扫描开始
     */
    @ApiModelProperty(value="下班时间2扫描开始")
    private String etscanbegintime2;
    /**
     * 下班时间2扫描结束
     */
    @ApiModelProperty(value="下班时间2扫描结束")
    private String etscanendtime2;
    /**
     * 上班时间3扫描开始
     */
    @ApiModelProperty(value="上班时间3扫描开始")
    private String btscanbegintime3;
    /**
     * 上班时间3扫描结束
     */
    @ApiModelProperty(value="上班时间3扫描结束")
    private String btscanendtime3;
    /**
     * 下班时间3扫描开始
     */
    @ApiModelProperty(value="下班时间3扫描开始")
    private String etscanbegintime3;
    /**
     * 下班时间3扫描结束
     */
    @ApiModelProperty(value="下班时间3扫描结束")
    private String etscanendtime3;
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
    }
