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
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-07-08 13:45:58
 */
@Data
@TableName("avw_attend_process")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VIEW")
public class AvwAttendProcess extends Model<AvwAttendProcess> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */

    @ApiModelProperty(value="")
    private String term;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@TableId
    private Integer agentmode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String begindate;
    /**
     * 
     */
    @ApiModelProperty(value="")
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
    private String initializedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer calc;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String calctime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer submit;
    /**
     * 
     */
    @ApiModelProperty(value="")
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
    private String closedtime;
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
