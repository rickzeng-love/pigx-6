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
 * 薪资累计期间设置
 *
 * @author gaoxiao
 * @date 2020-07-15 14:05:07
 */
@Data
@TableName("ctcd_payrollperiod")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资累计期间设置")
public class CtcdPayrollperiod extends Model<CtcdPayrollperiod> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private Integer gid;
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;
    /**
     * 期间开始(月)
     */
    @ApiModelProperty(value="期间开始(月)")
    private Integer startmonth;
    /**
     * 月份标识
     */
    @ApiModelProperty(value="月份标识")
    private Integer monflag;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;
    }
