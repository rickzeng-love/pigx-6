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
 * @date 2020-07-22 11:28:44
 */
@Data
@TableName("atshift_details_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class AtshiftDetailsAll extends Model<AtshiftDetailsAll> {
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
    private LocalDateTime term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime term2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer plantwid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime planbegintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime planendtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime factbegintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime factendtime;
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
    private Integer facttwid;
    /**
     * 
     */
    @ApiModelProperty(value="")
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
     * 
     */
    @ApiModelProperty(value="")
    private Double num;
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
    private Integer seqid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xtype;
    }
