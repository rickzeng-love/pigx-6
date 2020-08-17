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
 * 等级对应配置
 *
 * @author gaoxiao
 * @date 2020-06-12 17:04:19
 */
@Data
@TableName("ctcd_empgrade_salarygrade")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "等级对应配置")
public class CtcdEmpgradeSalarygrade extends Model<CtcdEmpgradeSalarygrade> {
private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId
    @ApiModelProperty(value="编码")
    private Integer id;
    /**
     * 员工等级（职级）
     */
    @ApiModelProperty(value="员工等级（职级）")
    private String empgrade;
    /**
     * 薪资等级
     */
    @ApiModelProperty(value="薪资等级")
    private String salarygrade;
    /**
     * 年薪标准
     */
    @ApiModelProperty(value="年薪标准")
    private Double yearstandard;
    /**
     * 月薪标准
     */
    @ApiModelProperty(value="月薪标准")
    private Double monthstandard;
    /**
     * 日新标准
     */
    @ApiModelProperty(value="日新标准")
    private Double daystandard;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 标准出勤
     */
    @ApiModelProperty(value="标准出勤")
    private Double pndy;
    /**
     * 月度发放比例
     */
    @ApiModelProperty(value="月度发放比例")
    private Double monthstandardrate;
    /**
     * 出差补助标准
     */
    @ApiModelProperty(value="出差补助标准")
    private Double fanfstandard;
    /**
     * 话费标准
     */
    @ApiModelProperty(value="话费标准")
    private Double spendstandard;
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
