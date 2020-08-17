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
 * @author gaoxiao
 * @date 2020-07-10 17:09:04
 */
@Data
@TableName("ctcd_benrate_common")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class CtcdBenrateCommon extends Model<CtcdBenrateCommon> {
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
    private Integer benarea;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bentype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double selfRate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double selfAddnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer digital;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String selfMantissa;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double compRate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double compAddnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer compdigital;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String compMantissa;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double maxvalue;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double minvalue;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String adjmonth;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String benstdtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdefault;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdisabled;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer compid;
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
