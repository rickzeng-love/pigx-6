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
 * 员工排班历史
 *
 * @author gaoxiao
 * @date 2020-07-08 17:11:03
 */
@Data
@TableName("atshift_work_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工排班历史")
public class AtshiftWorkAll extends Model<AtshiftWorkAll> {
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
    private String s1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s4;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s5;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s6;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s7;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s8;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s9;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s10;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s11;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s12;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s13;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s14;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s15;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s16;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s17;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s18;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s19;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s20;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s21;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s22;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s23;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s24;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s25;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s26;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s27;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s28;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s29;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s30;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s31;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s32;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s33;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s34;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s35;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s36;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s37;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s38;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s39;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s40;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s41;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s42;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s43;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s44;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s45;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s46;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s47;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s48;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s49;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String s50;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer corpid;
    }
