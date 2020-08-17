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
 * 
 *
 * @author shishengjie
 * @date 2020-07-22 14:39:13
 */
@Data
@TableName("atregtimeleavelong_register")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class AtregtimeleavelongRegister extends Model<AtregtimeleavelongRegister> {
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
    private String sheetid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String badge;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String name;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer compid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer depid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer jobid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String type;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String freqtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String attendid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer twid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime begintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime endtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double amount;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String unit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime begindate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime enddate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime sbegintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime sendtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double samount;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer recnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String initializedby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime initializedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfinstid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer submit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String submitby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime submittime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer closed;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String closedby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime closedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String reason;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String wfguid;
    }
