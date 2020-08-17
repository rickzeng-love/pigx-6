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
 * gaoxiao
 *
 * @author gaoxiao
 * @date 2020-08-07 17:12:59
 */
@Data
@TableName("avw_shiftdayeasy")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "gaoxiao")
public class AvwShiftdayeasy extends Model<AvwShiftdayeasy> {
private static final long serialVersionUID = 1L;

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
    private Integer groupid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String shift;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isexception;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer analymode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardendtime1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardendtime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardendtime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double cder;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double pnhr;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double pndy;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double wkhr;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double wkdy;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double limn;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double eomn;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double dnan;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double litm;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double eotm;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double abhr;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double abdy;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double uahr;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double otab;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double ot1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double ot2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double ot3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double clot;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double sicl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double perl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double annl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double coml;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double marl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double prpl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double matl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double patl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double otcl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double puhl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double nurl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double isjl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double fanf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double ofal;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double lvwk;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double rewk;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double otcd;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double nudy;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
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
