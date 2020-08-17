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
 * 劳务税率
 *
 * @author gaoxioa
 * @date 2020-07-14 19:13:24
 */
@Data
@TableName("ctcd_lataxrate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "劳务税率")
public class CtcdLataxrate extends Model<CtcdLataxrate> {
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
    private Double minnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double maxnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double taxrate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double taxdeduction;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;
    /**
     * 组织机构
     */
    @ApiModelProperty(value="组织机构")
    private String corpcode;
    }
