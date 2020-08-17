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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工排班
 *
 * @author GXS
 * @date 2020-05-27 16:53:58
 */
@Data
@TableName("atshift_work")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工排班")
public class AtshiftWork extends Model<AtshiftWork> {
private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
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
     * 公司
     */
    @ApiModelProperty(value="公司")
    private Integer compid;
    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
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
     * 第32天
     */
    @ApiModelProperty(value="第32天")
    private String s32;
    /**
     * 第33天
     */
    @ApiModelProperty(value="第33天")
    private String s33;
    /**
     * 第34天
     */
    @ApiModelProperty(value="第34天")
    private String s34;
    /**
     * 第35天
     */
    @ApiModelProperty(value="第35天")
    private String s35;
    /**
     * 第36天
     */
    @ApiModelProperty(value="第36天")
    private String s36;
    /**
     * 第37天
     */
    @ApiModelProperty(value="第37天")
    private String s37;
    /**
     * 第38天
     */
    @ApiModelProperty(value="第38天")
    private String s38;
    /**
     * 第39天
     */
    @ApiModelProperty(value="第39天")
    private String s39;
    /**
     * 第40天
     */
    @ApiModelProperty(value="第40天")
    private String s40;
    /**
     * 第41天
     */
    @ApiModelProperty(value="第41天")
    private String s41;
    /**
     * 第42天
     */
    @ApiModelProperty(value="第42天")
    private String s42;
    /**
     * 第43天
     */
    @ApiModelProperty(value="第43天")
    private String s43;
    /**
     * 第44天
     */
    @ApiModelProperty(value="第44天")
    private String s44;
    /**
     * 第45天
     */
    @ApiModelProperty(value="第45天")
    private String s45;
    /**
     * 第46天
     */
    @ApiModelProperty(value="第46天")
    private String s46;
    /**
     * 第47天
     */
    @ApiModelProperty(value="第47天")
    private String s47;
    /**
     * 第48天
     */
    @ApiModelProperty(value="第48天")
    private String s48;
    /**
     * 第49天
     */
    @ApiModelProperty(value="第49天")
    private String s49;
    /**
     * 第50天
     */
    @ApiModelProperty(value="第50天")
    private String s50;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;
    /**
     * 公司编码
     */
    @ApiModelProperty(value="公司编码")
    private String corpcode;

	@ApiModelProperty(value="第1天班次名称")
	@TableField(exist=false)
	private String S1Name;

	@ApiModelProperty(value="第2天班次名称")
	@TableField(exist=false)
	private String S2Name;

	@ApiModelProperty(value="第3天班次名称")
	@TableField(exist=false)
	private String S3Name;

	@ApiModelProperty(value="第4天班次名称")
	@TableField(exist=false)
	private String S4Name;

	@ApiModelProperty(value="第5天班次名称")
	@TableField(exist=false)
	private String S5Name;

	@ApiModelProperty(value="第6天班次名称")
	@TableField(exist=false)
	private String S6Name;

	@ApiModelProperty(value="第7天班次名称")
	@TableField(exist=false)
	private String S7Name;

	@ApiModelProperty(value="第8天班次名称")
	@TableField(exist=false)
	private String S8Name;

	@ApiModelProperty(value="第9天班次名称")
	@TableField(exist=false)
	private String S9Name;

	@ApiModelProperty(value="第10天班次名称")
	@TableField(exist=false)
	private String S10Name;

	@ApiModelProperty(value="第11天班次名称")
	@TableField(exist=false)
	private String S11Name;

	@ApiModelProperty(value="第12天班次名称")
	@TableField(exist=false)
	private String S12Name;

	@ApiModelProperty(value="第13天班次名称")
	@TableField(exist=false)
	private String S13Name;

	@ApiModelProperty(value="第14天班次名称")
	@TableField(exist=false)
	private String S14Name;

	@ApiModelProperty(value="第15天班次名称")
	@TableField(exist=false)
	private String S15Name;

	@ApiModelProperty(value="第16天班次名称")
	@TableField(exist=false)
	private String S16Name;

	@ApiModelProperty(value="第17天班次名称")
	@TableField(exist=false)
	private String S17Name;

	@ApiModelProperty(value="第18天班次名称")
	@TableField(exist=false)
	private String S18Name;

	@ApiModelProperty(value="第19天班次名称")
	@TableField(exist=false)
	private String S19Name;

	@ApiModelProperty(value="第20天班次名称")
	@TableField(exist=false)
	private String S20Name;

	@ApiModelProperty(value="第21天班次名称")
	@TableField(exist=false)
	private String S21Name;

	@ApiModelProperty(value="第22天班次名称")
	@TableField(exist=false)
	private String S22Name;

	@ApiModelProperty(value="第23天班次名称")
	@TableField(exist=false)
	private String S23Name;

	@ApiModelProperty(value="第24天班次名称")
	@TableField(exist=false)
	private String S24Name;

	@ApiModelProperty(value="第25天班次名称")
	@TableField(exist=false)
	private String S25Name;

	@ApiModelProperty(value="第26天班次名称")
	@TableField(exist=false)
	private String S26Name;

	@ApiModelProperty(value="第27天班次名称")
	@TableField(exist=false)
	private String S27Name;

	@ApiModelProperty(value="第28天班次名称")
	@TableField(exist=false)
	private String S28Name;

	@ApiModelProperty(value="第29天班次名称")
	@TableField(exist=false)
	private String S29Name;

	@ApiModelProperty(value="第30天班次名称")
	@TableField(exist=false)
	private String S30Name;

	@ApiModelProperty(value="第31天班次名称")
	@TableField(exist=false)
	private String S31Name;

    }
