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

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 福利比例
 *
 * @author gaoxiao
 * @date 2020-06-13 10:31:07
 */
@Data
@TableName("ctcd_benrate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "福利比例")
public class CtcdBenrate extends Model<CtcdBenrate> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 福利地区
     */
    @ApiModelProperty(value="福利地区")
    private Integer benarea;
    /**
     * 福利类型
     */
    @ApiModelProperty(value="福利类型")
    private Integer bentype;
    /**
     * 个人比例(%)
     */
    @ApiModelProperty(value="个人比例(%)")
    private Double selfRate;
    /**
     * 个人加项
     */
    @ApiModelProperty(value="个人加项")
    private Double selfAddnum;
    /**
     * 个人尾数小数位数
     */
    @ApiModelProperty(value="个人尾数小数位数")
    private Integer digital;
    /**
     * 个人尾数策略
     */
    @ApiModelProperty(value="个人尾数策略")
    private String selfMantissa;
    /**
     * 公司比例(%)
     */
    @ApiModelProperty(value="公司比例(%)")
    private Double compRate;
    /**
     * 公司加项
     */
    @ApiModelProperty(value="公司加项")
    private Double compAddnum;
    /**
     * 公司尾数小数位数
     */
    @ApiModelProperty(value="公司尾数小数位数")
    private Integer compdigital;
    /**
     * 公司尾数策略
     */
    @ApiModelProperty(value="公司尾数策略")
    private String compMantissa;
    /**
     * 上限额度
     */
    @ApiModelProperty(value="上限额度")
	@TableField("`maxvalue`")
    private Double maxvalue;
    /**
     * 下限额度
     */
    @ApiModelProperty(value="下限额度")
    private Double minvalue;
    /**
     * 基数调整月份
     */
    @ApiModelProperty(value="基数调整月份")
    private String adjmonth;
    /**
     * 基数获取类型
     */
    @ApiModelProperty(value="基数获取类型")
    private String benstdtype;
    /**
     * 是否默认
     */
    @ApiModelProperty(value="是否默认")
    private Integer isdefault;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
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
    private Integer compid;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    }
