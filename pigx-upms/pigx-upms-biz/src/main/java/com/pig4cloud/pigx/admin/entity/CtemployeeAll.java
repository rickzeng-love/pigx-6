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
 * @author shishengjie
 * @date 2020-07-21 15:58:04
 */
@Data
@TableName("ctemployee_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class CtemployeeAll extends Model<CtemployeeAll> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String badge;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String name;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer costid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarystatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarytype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarygrade;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarykind;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarycity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer paymode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer paystatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankno;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String openbankemp;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankid2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankno2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String openbankemp2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a4;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a5;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer calcway;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer is80;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankrelateid;
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
