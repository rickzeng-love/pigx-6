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
 * 城市最低工资
 *
 * @author gaoxiao
 * @date 2020-06-10 17:21:36
 */
@Data
@TableName("ctcd_citypay")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "城市最低工资")
public class CtcdCitypay extends Model<CtcdCitypay> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 年份
     */
    @ApiModelProperty(value="年份")
    private String term;
    /**
     * 城市
     */
    @ApiModelProperty(value="城市")
    private Integer cityid;
    /**
     * 最低工资
     */
    @ApiModelProperty(value="最低工资")
    private Double cityminpay;
    /**
     * 平均工资
     */
    @ApiModelProperty(value="平均工资")
    private Double cityavgpay;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
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
