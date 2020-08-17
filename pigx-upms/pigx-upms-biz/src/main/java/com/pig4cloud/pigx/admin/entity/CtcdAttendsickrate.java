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
 * 病假工资比率
 *
 * @author gaoxiao
 * @date 2020-06-12 17:17:35
 */
@Data
@TableName("ctcd_attendsickrate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "病假工资比率")
public class CtcdAttendsickrate extends Model<CtcdAttendsickrate> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 病假类型
     */
    @ApiModelProperty(value="病假类型")
    private Integer stype;
    /**
     * 公司(不选通用)
     */
    @ApiModelProperty(value="公司(不选通用)")
    private Integer compid;
    /**
     * 城市编码
     */
    @ApiModelProperty(value="城市编码")
    private Integer cityid;
    /**
     * 工龄/司龄
     */
    @ApiModelProperty(value="工龄/司龄")
    private Integer relayeartype;
    /**
     * 下限(月,包括)
     */
    @ApiModelProperty(value="下限(月,包括)")
    private Integer minnum;
    /**
     * 上限(月,不包括)
     */
    @ApiModelProperty(value="上限(月,不包括)")
    private Integer maxnum;
    /**
     * 病假工资比率
     */
    @ApiModelProperty(value="病假工资比率")
    private Double rate;
    /**
     * 病假工资基准
     */
    @ApiModelProperty(value="病假工资基准")
    private Integer sickbase;
    /**
     * 病假工资基准比例
     */
    @ApiModelProperty(value="病假工资基准比例")
    private Double sickbaserate;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer corpid;
    }
