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
 * 普通税率(累计)
 *
 * @author gaoxioa
 * @date 2020-07-14 19:11:15
 */
@Data
@TableName("ctcd_taxratesum")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "普通税率(累计)")
public class CtcdTaxratesum extends Model<CtcdTaxratesum> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 最小值
     */
    @ApiModelProperty(value="最小值")
    private Double minnum;
    /**
     * 最大值(包含)
     */
    @ApiModelProperty(value="最大值(包含)")
    private Double maxnum;
    /**
     * 税率
     */
    @ApiModelProperty(value="税率")
    private Double taxrate;
    /**
     * 速算扣除数
     */
    @ApiModelProperty(value="速算扣除数")
    private Double taxdeduction;
    /**
     * 实发最小值
     */
    @ApiModelProperty(value="实发最小值")
    private Double factminnum;
    /**
     * 实发最大值
     */
    @ApiModelProperty(value="实发最大值")
    private Double factmaxnum;
    /**
     * 最小税值
     */
    @ApiModelProperty(value="最小税值")
    private Double mintax;
    /**
     * 最大税值
     */
    @ApiModelProperty(value="最大税值")
    private Double maxtax;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    /**
     * CorpCode
     */
    @ApiModelProperty(value="CorpCode")
    private String corpcode;
    }
