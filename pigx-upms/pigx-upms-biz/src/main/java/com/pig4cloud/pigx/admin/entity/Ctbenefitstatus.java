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

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 福利信息
 *
 * @author gaoxiao
 * @date 2020-06-13 10:46:06
 */
@Data
@TableName("ctbenefitstatus")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "福利信息")
public class Ctbenefitstatus extends Model<Ctbenefitstatus> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String term;
	/**
	 * EID
	 */
	@TableId
	@ApiModelProperty(value = "EID")
	private Integer eid;
	/**
	 * 工号
	 */
	@ApiModelProperty(value = "工号")
	private String badge;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;
	/**
	 * 公司ID
	 */
	@ApiModelProperty(value = "公司ID")
	private Integer compid;
	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "部门ID")
	private Integer depid;
	/**
	 * 岗位ID
	 */
	@ApiModelProperty(value = "岗位ID")
	private Integer jobid;
	/**
	 * 城市
	 */
	@ApiModelProperty(value = "城市")
	private Integer cityid;
	/**
	 * 福利状态
	 */
	@ApiModelProperty(value = "福利状态")
	private Integer benstatus;
	/**
	 * 福利城市
	 */
	@ApiModelProperty(value = "福利城市")
	private Integer bencity;
	/**
	 * 福利地区
	 */
	@ApiModelProperty(value = "福利地区")
	private Integer benarea;
	/**
	 * 承担方式
	 */
	@ApiModelProperty(value = "承担方式")
	private Integer beartype;
	/**
	 * 福利基数
	 */
	@ApiModelProperty(value = "福利基数")
	private Double benbase;
	/**
	 * 缴纳公积金
	 */
	@ApiModelProperty(value = "缴纳公积金")
	private Integer isaccu;
	/**
	 * 公积金基数
	 */
	@ApiModelProperty(value = "公积金基数")
	private Double accubase;
	/**
	 * 缴纳医疗保险
	 */
	@ApiModelProperty(value = "缴纳医疗保险")
	private Integer ismdcl;
	/**
	 * 医疗保险基数
	 */
	@ApiModelProperty(value = "医疗保险基数")
	private Double mdclbase;
	/**
	 * 缴纳养老保险
	 */
	@ApiModelProperty(value = "缴纳养老保险")
	private Integer ispens;
	/**
	 * 养老保险基数
	 */
	@ApiModelProperty(value = "养老保险基数")
	private Double pensbase;
	/**
	 * 缴纳失业保险
	 */
	@ApiModelProperty(value = "缴纳失业保险")
	private Integer isunem;
	/**
	 * 失业保险基数
	 */
	@ApiModelProperty(value = "失业保险基数")
	private Double unembase;
	/**
	 * 缴纳生育保险
	 */
	@ApiModelProperty(value = "缴纳生育保险")
	private Integer isbabs;
	/**
	 * 生育保险基数
	 */
	@ApiModelProperty(value = "生育保险基数")
	private Double babsbase;
	/**
	 * 缴纳工伤保险
	 */
	@ApiModelProperty(value = "缴纳工伤保险")
	private Integer isinjs;
	/**
	 * 工伤保险基数
	 */
	@ApiModelProperty(value = "工伤保险基数")
	private Double injsbase;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Integer isinss;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private Double inssbase;
	/**
	 * 缴纳大病保险
	 */
	@ApiModelProperty(value = "缴纳大病保险")
	private Integer issmdcl;
	/**
	 * 大病保险基数
	 */
	@ApiModelProperty(value = "大病保险基数")
	private Double smdclbase;
	/**
	 * 缴纳补充公积金
	 */
	@ApiModelProperty(value = "缴纳补充公积金")
	private Integer issuaccu;
	/**
	 * 补充公积金基数
	 */
	@ApiModelProperty(value = "补充公积金基数")
	private Double suaccubase;
	/**
	 * 缴纳补充医疗保险
	 */
	@ApiModelProperty(value = "缴纳补充医疗保险")
	private Integer issumdcl;
	/**
	 * 补充医疗保险基数
	 */
	@ApiModelProperty(value = "补充医疗保险基数")
	private Double sumdclbase;
	/**
	 * 缴纳补充养老保险
	 */
	@ApiModelProperty(value = "缴纳补充养老保险")
	private Integer issupen;
	/**
	 * 补充养老保险基数
	 */
	@ApiModelProperty(value = "补充养老保险基数")
	private Double supenbase;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * GID
	 */
	@ApiModelProperty(value = "GID")
	private Integer gid;
	/**
	 * CorpID
	 */
	@ApiModelProperty(value = "CorpID")
	private Integer corpid;
	/**
	 * CorpCode
	 */
	@ApiModelProperty(value = "CorpCode")
	private String corpcode;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal pensbaseSelf;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal mdclbaseSelf;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal smdclbaseSelf;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private BigDecimal unembaseSelf;
	/**
	 * 生效时间
	 */
	@ApiModelProperty(value = "生效时间")
	private String effectdate;
}
