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

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位信息
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:28
 */
@Data
@TableName("otjob")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位信息")
public class Otjob extends Model<Otjob> {
private static final long serialVersionUID = 1L;

    /**
     * 岗位编码
     */
    @TableId
    @ApiModelProperty(value="岗位编码")
    private Integer jobid;
    /**
     * 岗位代码
     */
    @ApiModelProperty(value="岗位代码")
    private String jobcode;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    private String title;
    /**
     * 岗位简称
     */
    @ApiModelProperty(value="岗位简称")
    private String jobabbr;
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
     * 上级岗位
     */
    @ApiModelProperty(value="上级岗位")
    private Integer adminid;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer functionid;
    /**
     * 岗位级别
     */
    @ApiModelProperty(value="岗位级别")
    private Integer jobgrade;
    /**
     * 岗位类别
     */
    @ApiModelProperty(value="岗位类别")
    private Integer jobtype;
    /**
     * 岗位序列
     */
    @ApiModelProperty(value="岗位序列")
    private Integer jobproperty;
    /**
     * 岗位编制(人数)
     */
    @ApiModelProperty(value="岗位编制(人数)")
    private Integer jobnum;
    /**
     * 岗位版本
     */
    @ApiModelProperty(value="岗位版本")
    private String jobversion;
    /**
     * 是否核心岗位
     */
    @ApiModelProperty(value="是否核心岗位")
    private Integer iscore;
    /**
     * 是否计件
     */
    @ApiModelProperty(value="是否计件")
    private Integer ispiece;
    /**
     * 高温津贴
     */
    @ApiModelProperty(value="高温津贴")
    private Double hotfee;
    /**
     * 成立时间
     */
    @ApiModelProperty(value="成立时间")
    private String effectdate;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private String xorder;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
    /**
     * 失效日期
     */
    @ApiModelProperty(value="失效日期")
    private String disableddate;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 所属组织代码
     */
    @ApiModelProperty(value="所属组织代码")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double restjobnum;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdriver;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private String updatedate;
    /**
     * 对应职级
     */
    @ApiModelProperty(value="对应职级")
    private Integer empgradeid;
    /**
     * 对应职等
     */
    @ApiModelProperty(value="对应职等")
    private Integer posgradeid;
    /**
     * 岗位职责
     */
    @ApiModelProperty(value="岗位职责")
    private String jobdescription;
    /**
     * 任职要求
     */
    @ApiModelProperty(value="任职要求")
    private String jobrequirements;
	/**
	 * 岗位下的人数
	 */
	@ApiModelProperty(value="岗位下的人数")
	@TableField(exist = false)
	private String people;
	/**
	 * 对应职级
	 */
	@ApiModelProperty(value="对应职级")
	private Integer empgradeid1;
	/**
	 * 对应职等
	 */
	@ApiModelProperty(value="对应职等")
	private Integer posgradeid1;
	/**
	 * 对应职级
	 */
	@ApiModelProperty(value="对应职级")
	private Integer empgradeid2;
	/**
	 * 对应职等
	 */
	@ApiModelProperty(value="对应职等")
	private Integer posgradeid2;
	private Integer corpid;
    }
