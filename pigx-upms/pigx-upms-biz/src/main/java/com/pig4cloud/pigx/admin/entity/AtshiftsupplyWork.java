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
 * 手工排班
 *
 * @author GXS
 * @date 2020-05-29 10:38:12
 */
@Data
@TableName("atshiftsupply_work")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "手工排班")
public class AtshiftsupplyWork extends Model<AtshiftsupplyWork> {
private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value="ID")
    private Integer id;
    /**
     * 月份
     */
    @ApiModelProperty(value="月份")
    private LocalDateTime term;
    /**
     * EID
     */
    @ApiModelProperty(value="EID")
    private Integer eid;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private Integer depid;
    /**
     * ATBadge
     */
    @ApiModelProperty(value="ATBadge")
    private String atbadge;
    /**
     * 生产线
     */
    @ApiModelProperty(value="生产线")
    private String lineid;
    /**
     * 班组
     */
    @ApiModelProperty(value="班组")
    private String teamid;
    /**
     * 轮班组
     */
    @ApiModelProperty(value="轮班组")
    private String groupid;
    /**
     * 第1天
     */
    @ApiModelProperty(value="第1天")
    private String s1;
    /**
     * 第2天
     */
    @ApiModelProperty(value="第2天")
    private String s2;
    /**
     * 第3天
     */
    @ApiModelProperty(value="第3天")
    private String s3;
    /**
     * 第4天
     */
    @ApiModelProperty(value="第4天")
    private String s4;
    /**
     * 第5天
     */
    @ApiModelProperty(value="第5天")
    private String s5;
    /**
     * 第6天
     */
    @ApiModelProperty(value="第6天")
    private String s6;
    /**
     * 第7天
     */
    @ApiModelProperty(value="第7天")
    private String s7;
    /**
     * 第8天
     */
    @ApiModelProperty(value="第8天")
    private String s8;
    /**
     * 第9天
     */
    @ApiModelProperty(value="第9天")
    private String s9;
    /**
     * 第10天
     */
    @ApiModelProperty(value="第10天")
    private String s10;
    /**
     * 第11天
     */
    @ApiModelProperty(value="第11天")
    private String s11;
    /**
     * 第12天
     */
    @ApiModelProperty(value="第12天")
    private String s12;
    /**
     * 第13天
     */
    @ApiModelProperty(value="第13天")
    private String s13;
    /**
     * 第14天
     */
    @ApiModelProperty(value="第14天")
    private String s14;
    /**
     * 第15天
     */
    @ApiModelProperty(value="第15天")
    private String s15;
    /**
     * 第16天
     */
    @ApiModelProperty(value="第16天")
    private String s16;
    /**
     * 第17天
     */
    @ApiModelProperty(value="第17天")
    private String s17;
    /**
     * 第18天
     */
    @ApiModelProperty(value="第18天")
    private String s18;
    /**
     * 第19天
     */
    @ApiModelProperty(value="第19天")
    private String s19;
    /**
     * 第20天
     */
    @ApiModelProperty(value="第20天")
    private String s20;
    /**
     * 第21天
     */
    @ApiModelProperty(value="第21天")
    private String s21;
    /**
     * 第22天
     */
    @ApiModelProperty(value="第22天")
    private String s22;
    /**
     * 第23天
     */
    @ApiModelProperty(value="第23天")
    private String s23;
    /**
     * 第24天
     */
    @ApiModelProperty(value="第24天")
    private String s24;
    /**
     * 第25天
     */
    @ApiModelProperty(value="第25天")
    private String s25;
    /**
     * 第26天
     */
    @ApiModelProperty(value="第26天")
    private String s26;
    /**
     * 第27天
     */
    @ApiModelProperty(value="第27天")
    private String s27;
    /**
     * 第28天
     */
    @ApiModelProperty(value="第28天")
    private String s28;
    /**
     * 第29天
     */
    @ApiModelProperty(value="第29天")
    private String s29;
    /**
     * 第30天
     */
    @ApiModelProperty(value="第30天")
    private String s30;
    /**
     * 第31天
     */
    @ApiModelProperty(value="第31天")
    private String s31;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s32;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s33;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s34;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s35;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s36;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s37;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s38;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s39;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s40;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s41;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s42;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s43;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s44;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s45;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s46;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s47;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s48;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s49;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s50;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
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
