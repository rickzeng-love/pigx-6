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
 * 培训经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:41:20
 */
@Data
@TableName("etbg_training")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "培训经历")
public class EtbgTraining extends Model<EtbgTraining> {
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
     * 入职前培训
     */
    @ApiModelProperty(value="入职前培训")
    private Integer isjoin;
    /**
     * 培训形式
     */
    @ApiModelProperty(value="培训形式")
    private Integer trainkind;
    /**
     * 培训类型
     */
    @ApiModelProperty(value="培训类型")
    private Integer traintype;
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
     * 培训主题
     */
    @ApiModelProperty(value="培训主题")
    private String content;
    /**
     * 培训课程
     */
    @ApiModelProperty(value="培训课程")
    private String course;
    /**
     * 举办机构
     */
    @ApiModelProperty(value="举办机构")
    private String vendor;
    /**
     * 培训地点
     */
    @ApiModelProperty(value="培训地点")
    private String place;
    /**
     * 学时
     */
    @ApiModelProperty(value="学时")
    private Double hours;
    /**
     * 证件名
     */
    @ApiModelProperty(value="证件名")
    private String certificate;
    /**
     * 证件编号
     */
    @ApiModelProperty(value="证件编号")
    private String certificateno;
    /**
     * 协议编号
     */
    @ApiModelProperty(value="协议编号")
    private String agreementno;
    /**
     * 协议开始日期
     */
    @ApiModelProperty(value="协议开始日期")
    private LocalDateTime abegindate;
    /**
     * 协议期
     */
    @ApiModelProperty(value="协议期")
    private Integer aterm;
    /**
     * 协议结束日期
     */
    @ApiModelProperty(value="协议结束日期")
    private LocalDateTime aenddate;
    /**
     * 培训费用
     */
    @ApiModelProperty(value="培训费用")
    private Double expenses;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String payorg;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime cbegindate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double cterm;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cenddate;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
	/**
	 *获得证书
	 */
	@ApiModelProperty(value="")
	private String hdzs;
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
