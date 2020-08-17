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
 * 任职记录
 *
 * @author gaoxiao
 * @date 2020-04-13 10:21:42
 */
@Data
@TableName("etbg_servingrecords")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任职记录")
public class EtbgServingrecords extends Model<EtbgServingrecords> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 员工编码
     */
    @ApiModelProperty(value="员工编码")
    private Integer eid;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private String begindate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private String enddate;
    /**
     * 工作单位
     */
    @ApiModelProperty(value="工作单位")
    private String company;
    /**
     * 担任职务
     */
    @ApiModelProperty(value="担任职务")
    private String job;
    /**
     * 工作地点
     */
    @ApiModelProperty(value="工作地点")
    private String workplace;
    /**
     * 证明人
     */
    @ApiModelProperty(value="证明人")
    private String reference;
    /**
     * 联系方式
     */
    @ApiModelProperty(value="联系方式")
    private String tel;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 省
     */
    @ApiModelProperty(value="省")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value="市")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(value="区")
    private String district;
    /**
     * 详细地址
     */
    @ApiModelProperty(value="详细地址")
    private String adress;
    /**
     * 职务
     */
    @ApiModelProperty(value="职务")
    private Integer position;
    /**
     * 职等
     */
    @ApiModelProperty(value="职等")
    private Integer posgrade;
    /**
     * 职级
     */
    @ApiModelProperty(value="职级")
    private Integer empgrade;
    /**
     * 工作性质 1：全职；0：非全职
     */
    @ApiModelProperty(value="工作性质 1：全职；0：非全职")
    private Integer jobcategory;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private Integer userid;
	/**
	 * 组织code
	 */
	@ApiModelProperty(value="组织code")
	private String corpcode;
    }
