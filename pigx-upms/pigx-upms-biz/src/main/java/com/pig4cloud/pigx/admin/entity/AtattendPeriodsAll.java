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
 * 
 *
 * @author gaoxiao
 * @date 2020-07-19 14:34:30
 */
@Data
@TableName("atattend_periods_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class AtattendPeriodsAll extends Model<AtattendPeriodsAll> {
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
    private Integer agentmode;
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
    private String begindate;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String enddate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String initializedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer submit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer submitby;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String submittime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer closed;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer closedby;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String closedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer iscurrentmonth;
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
