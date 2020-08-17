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
 * 紧急联系人
 *
 * @author gaoxiao
 * @date 2020-04-13 09:39:12
 */
@Data
@TableName("etbg_emergency")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "紧急联系人")
public class EtbgEmergency extends Model<EtbgEmergency> {
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
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    private String emergencyname;
    /**
     * 关系
     */
    @ApiModelProperty(value="关系")
    private Integer relationship;
    /**
     * 现居住地址
     */
    @ApiModelProperty(value="现居住地址")
    private String address;
    /**
     * 紧急联系人电话
     */
    @ApiModelProperty(value="紧急联系人电话")
    private String telephone;
    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String postcode;
    /**
     * 手机
     */
    @ApiModelProperty(value="手机")
    private String mobile;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
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
	private  Integer isdisabled;
    }
