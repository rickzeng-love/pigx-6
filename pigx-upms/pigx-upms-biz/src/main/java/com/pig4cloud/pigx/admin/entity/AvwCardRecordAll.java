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
 * @date 2020-07-07 17:30:23
 */
@Data
@TableName("avw_card_record_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VIEW")
public class AvwCardRecordAll extends Model<AvwCardRecordAll> {
private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @TableId
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 部门代码
     */
    @ApiModelProperty(value="部门代码")
    private Integer depid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String macid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime xdatetime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer inoutflag;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer mode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdisable;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer handflag;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
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
