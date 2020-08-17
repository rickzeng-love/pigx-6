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
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 薪资城市
 *
 * @author gaoxiao
 * @date 2020-07-10 15:44:59
 */
@Data
@TableName("etcd_city")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资城市")
public class EtcdCity extends Model<EtcdCity> {
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
    private Integer provincecode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String provincename;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer code;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xorder;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdisabled;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdefault;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer istaxcity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal taxbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    private String corpcode;
    private Integer corpid;
    }
