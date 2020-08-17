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

/**
 * 考勤打卡原始表
 *
 * @author gaoxiao
 * @date 2020-06-19 12:11:01
 */
@Data
@TableName("oatattendancedetail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考勤打卡原始表")
public class Oatattendancedetail extends Model<Oatattendancedetail> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer attendanceid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer userid;
    /**
     * 打卡日期
     */
    @ApiModelProperty(value="打卡日期")
    private String attendancedate;
    /**
     * 打卡时间
     */
    @ApiModelProperty(value="打卡时间")
    private String attendancetime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer attendancetype;
    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private Double userlng;
    /**
     * 维度
     */
    @ApiModelProperty(value="维度")
    private Double userlat;
    /**
     * 距离
     */
    @ApiModelProperty(value="距离")
    private Double userdistance;
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
	@TableField(exist=false)
    private String day;
	@TableField(exist=false)
    private Integer eid;
	@TableField(exist=false)
	private String term;
    }
