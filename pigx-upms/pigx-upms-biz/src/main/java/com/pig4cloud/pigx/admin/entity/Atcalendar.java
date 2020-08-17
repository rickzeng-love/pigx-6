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
 * 日历
 *
 * @author gaoxiao
 * @date 2020-07-02 13:49:23
 */
@Data
@TableName("atcalendar")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "日历")
public class Atcalendar extends Model<Atcalendar> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 考勤帐套
     */
    @ApiModelProperty(value="考勤帐套")
    private Integer agentmode;
    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
    /**
     * 日历类型
     */
    @ApiModelProperty(value="日历类型")
    private Integer xcategory;
    /**
     * 日期类型
     */
    @ApiModelProperty(value="日期类型")
    private Integer xtype;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private String initializedtime;
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
    }
