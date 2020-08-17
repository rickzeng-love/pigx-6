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
 * 时间记录
 *
 * @author gaoxiao
 * @date 2020-08-06 09:53:59
 */
@Data
@TableName("etevent")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "时间记录")
public class Etevent extends Model<Etevent> {
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
    private Integer type;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String effectdate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
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
    private Integer reportto;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfreportto;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer jobstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer emptype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empgrade;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empgroup;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empkind;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empcategory;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empproperty;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer workcity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    }
