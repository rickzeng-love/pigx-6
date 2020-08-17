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
 * 证件管理
 *
 * @author gaoxiao
 * @date 2020-04-13 15:27:55
 */
@Data
@TableName("etbg_certificate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "证件管理")
public class EtbgCertificate extends Model<EtbgCertificate> {
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
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 所属公司
     */
    @ApiModelProperty(value="所属公司")
    private Integer compid;
    /**
     * 所属部门
     */
    @ApiModelProperty(value="所属部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    private Integer certtype;
    /**
     * 证件号
     */
    @ApiModelProperty(value="证件号")
    private String certno;
    /**
     * 证件名称
     */
    @ApiModelProperty(value="证件名称")
    private String certname;
    /**
     * 颁发机构
     */
    @ApiModelProperty(value="颁发机构")
    private String payorg;
    /**
     * 颁发日期
     */
    @ApiModelProperty(value="颁发日期")
    private LocalDateTime begindate;
    /**
     * 有效期
     */
    @ApiModelProperty(value="有效期")
    private String term;
    /**
     * 有效截止日期
     */
    @ApiModelProperty(value="有效截止日期")
    private String enddate;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 证件照片
     */
    @ApiModelProperty(value="证件照片")
    private String photo;
    /**
     * 级别
     */
    @ApiModelProperty(value="级别")
    private String certgrade;
    /**
     * 专业
     */
    @ApiModelProperty(value="专业")
    private String major;
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
