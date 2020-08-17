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
 * 周报
 *
 * @author gaoxiao
 * @date 2020-04-28 14:43:23
 */
@Data
@TableName("sys_week_paper")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "周报")
public class SysWeekPaper extends Model<SysWeekPaper> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;
    /**
     * 本周工作
     */
    @ApiModelProperty(value="本周工作")
    private String todaywork;
    /**
     * 下周工作
     */
    @ApiModelProperty(value="下周工作")
    private String tomorrowwork;
    /**
     * 附件
     */
    @ApiModelProperty(value="附件")
    private String fujian;
    /**
     * 汇报对象
     */
    @ApiModelProperty(value="汇报对象")
    private String reportto;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String reporttoname;
    /**
     * 抄送
     */
    @ApiModelProperty(value="抄送")
    private String duplicateto;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String duplicatetoname;
    /**
     * 其他事项、备注
     */
    @ApiModelProperty(value="其他事项、备注")
    private String remark;
    /**
     * 汇报人
     */
    @ApiModelProperty(value="汇报人")
    private Integer oper;
    /**
     * 汇报人姓名
     */
    @ApiModelProperty(value="汇报人姓名")
    private String opername;
    /**
     * 汇报时间
     */
    @ApiModelProperty(value="汇报时间")
    private String operdate;
    /**
     * 是否有效0：有效1失效
     */
    @ApiModelProperty(value="是否有效0：有效1失效")
    private Integer isdisable;
	private String corpcode;
	private Integer corpid;
	@TableField(exist=false)
	private List<WeekPaperTo> reporttoList;
	@TableField(exist=false)
	private List<WeekPaperTo> duplicatetoList;
	private String portrait;
	private String updatedate;
	private String filename;
    }
