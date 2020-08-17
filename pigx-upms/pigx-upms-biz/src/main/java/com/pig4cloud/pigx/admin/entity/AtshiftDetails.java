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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日明细结果
 *
 * @author gaoxiao
 * @date 2020-06-22 09:59:43
 */
@Data
@TableName("atshift_details")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "日明细结果")
public class AtshiftDetails extends Model<AtshiftDetails> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term2;
    /**
     * EID
     */
    @ApiModelProperty(value="EID")
    private Integer eid;
    /**
     * 计划
     */
    @ApiModelProperty(value="计划")
    private Integer plantwid;
    /**
     * 计划开始时间
     */
    @ApiModelProperty(value="计划开始时间")
    private String planbegintime;
    /**
     * 计划结束时间
     */
    @ApiModelProperty(value="计划结束时间")
    private String planendtime;
    /**
     * 刷卡开始时间
     */
    @ApiModelProperty(value="刷卡开始时间")
    private String factbegintime;
    /**
     * 刷卡结束时间
     */
    @ApiModelProperty(value="刷卡结束时间")
    private String factendtime;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private String begintime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String endtime;
    /**
     * 考勤结果
     */
    @ApiModelProperty(value="考勤结果")
    private Integer facttwid;
    /**
     * 异常
     */
    @ApiModelProperty(value="异常")
    private Integer isexception;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer totalmins;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer netmins;
    /**
     * 时.分(60进制)
     */
    @ApiModelProperty(value="时.分(60进制)")
    private Double num;
    /**
     * 数值
     */
    @ApiModelProperty(value="数值")
    private Double amount;
    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
    private String unit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xtype;
	private String corpcode;
	private Integer corpid;
    }
