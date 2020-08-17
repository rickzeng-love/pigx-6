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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 社保福利历史
 *
 * @author gaoxiao
 * @date 2020-06-13 11:10:11
 */
@Data
@TableName("ctbencalc_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "社保福利历史")
public class CtbencalcAll extends Model<CtbencalcAll> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 薪资账套
     */
    @ApiModelProperty(value="薪资账套")
    private Integer gid;
    /**
     * 月份
     */
    @ApiModelProperty(value="月份")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
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
     * 公司
     */
    @ApiModelProperty(value="公司")
    private Integer compid;
    /**
     * 部门
     */
    @ApiModelProperty(value="部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 福利状态
     */
    @ApiModelProperty(value="福利状态")
    private Integer benstatus;
    /**
     * 福利城市
     */
    @ApiModelProperty(value="福利城市")
    private Integer bencity;
    /**
     * 福利地区
     */
    @ApiModelProperty(value="福利地区")
    private Integer benarea;
    /**
     * 承担方式
     */
    @ApiModelProperty(value="承担方式")
    private Integer beartype;
    /**
     * 福利基数
     */
    @ApiModelProperty(value="福利基数")
    private Double benbase;
    /**
     * 是否缴纳公积金
     */
    @ApiModelProperty(value="是否缴纳公积金")
    private Integer isaccu;
    /**
     * 公积金基数
     */
    @ApiModelProperty(value="公积金基数")
    private Double accubase;
    /**
     * 公积金个人月缴
     */
    @ApiModelProperty(value="公积金个人月缴")
    private Double accuSelf;
    /**
     * 公积金公司月缴
     */
    @ApiModelProperty(value="公积金公司月缴")
    private Double accuCorp;
    /**
     * 是否缴纳养老保险
     */
    @ApiModelProperty(value="是否缴纳养老保险")
    private Integer ispens;
    /**
     * 养老保险基数
     */
    @ApiModelProperty(value="养老保险基数")
    private Double pensbase;
    /**
     * 养老保险个人月缴
     */
    @ApiModelProperty(value="养老保险个人月缴")
    private Double penSelf;
    /**
     * 养老保险公司月缴
     */
    @ApiModelProperty(value="养老保险公司月缴")
    private Double penCorp;
    /**
     * 是否缴纳医疗保险
     */
    @ApiModelProperty(value="是否缴纳医疗保险")
    private Integer ismdcl;
    /**
     * 医疗保险基数
     */
    @ApiModelProperty(value="医疗保险基数")
    private Double mdclbase;
    /**
     * 医疗保险个人月缴
     */
    @ApiModelProperty(value="医疗保险个人月缴")
    private Double mdclSelf;
    /**
     * 医疗保险公司月缴
     */
    @ApiModelProperty(value="医疗保险公司月缴")
    private Double mdclCorp;
    /**
     * 是否缴纳失业保险
     */
    @ApiModelProperty(value="是否缴纳失业保险")
    private Integer isunem;
    /**
     * 失业保险基数
     */
    @ApiModelProperty(value="失业保险基数")
    private Double unembase;
    /**
     * 失业保险个人月缴
     */
    @ApiModelProperty(value="失业保险个人月缴")
    private Double uempSelf;
    /**
     * 失业保险公司月缴
     */
    @ApiModelProperty(value="失业保险公司月缴")
    private Double uempCorp;
    /**
     * 是否缴纳生育保险
     */
    @ApiModelProperty(value="是否缴纳生育保险")
    private Integer isbabs;
    /**
     * 生育保险基数
     */
    @ApiModelProperty(value="生育保险基数")
    private Double babsbase;
    /**
     * 生育保险个人月缴
     */
    @ApiModelProperty(value="生育保险个人月缴")
    private Double babySelf;
    /**
     * 生育保险公司月缴
     */
    @ApiModelProperty(value="生育保险公司月缴")
    private Double babyCorp;
    /**
     * 是否缴纳工伤保险
     */
    @ApiModelProperty(value="是否缴纳工伤保险")
    private Integer isinjs;
    /**
     * 工伤保险基数
     */
    @ApiModelProperty(value="工伤保险基数")
    private Double injsbase;
    /**
     * 工伤保险个人月缴
     */
    @ApiModelProperty(value="工伤保险个人月缴")
    private Double injurySelf;
    /**
     * 工伤保险公司月缴
     */
    @ApiModelProperty(value="工伤保险公司月缴")
    private Double injuryCorp;
    /**
     * 组织
     */
    @ApiModelProperty(value="组织")
    private String corpcode;
    /**
     * 是否缴纳大病保险
     */
    @ApiModelProperty(value="是否缴纳大病保险")
    private Integer issmdcl;
    /**
     * 大病保险基数
     */
    @ApiModelProperty(value="大病保险基数")
    private Double smdclbase;
    /**
     * 大病保险个人月缴
     */
    @ApiModelProperty(value="大病保险个人月缴")
    private Double smdclSelf;
    /**
     * 大病保险公司月缴
     */
    @ApiModelProperty(value="大病保险公司月缴")
    private Double smdclCorp;
    /**
     * 是否缴纳补充公积金
     */
    @ApiModelProperty(value="是否缴纳补充公积金")
    private Integer issuaccu;
    /**
     * 补充公积金基数
     */
    @ApiModelProperty(value="补充公积金基数")
    private Double suaccubase;
    /**
     * 补充公积金个人补缴
     */
    @ApiModelProperty(value="补充公积金个人补缴")
    private Double suaccSelf;
    /**
     * 补充公积金公司补缴
     */
    @ApiModelProperty(value="补充公积金公司补缴")
    private Double suaccCorp;
    /**
     * 是否缴纳补充医疗保险
     */
    @ApiModelProperty(value="是否缴纳补充医疗保险")
    private Integer issumdcl;
    /**
     * 补充医疗保险基数
     */
    @ApiModelProperty(value="补充医疗保险基数")
    private Double sumdclbase;
    /**
     * 医疗保险个人补缴
     */
    @ApiModelProperty(value="医疗保险个人补缴")
    private Double sumdclSelf;
    /**
     * 医疗保险公司补缴
     */
    @ApiModelProperty(value="医疗保险公司补缴")
    private Double sumdclCorp;
    /**
     * 是否缴纳补充养老保险
     */
    @ApiModelProperty(value="是否缴纳补充养老保险")
    private Integer issupen;
    /**
     * 补充养老保险基数
     */
    @ApiModelProperty(value="补充养老保险基数")
    private Double supenbase;
    /**
     * 养老保险个人补缴
     */
    @ApiModelProperty(value="养老保险个人补缴")
    private Double supenSelf;
    /**
     * 养老保险公司补缴
     */
    @ApiModelProperty(value="养老保险公司补缴")
    private Double supenCorp;
    /**
     * 公积金公司补缴
     */
    @ApiModelProperty(value="公积金公司补缴")
    private Double accubucorp;
    /**
     * 公积金个人补缴
     */
    @ApiModelProperty(value="公积金个人补缴")
    private Double accubuself;
    /**
     * 公司社保补缴
     */
    @ApiModelProperty(value="公司社保补缴")
    private Double benebucorp;
    /**
     * 个人社保补缴
     */
    @ApiModelProperty(value="个人社保补缴")
    private Double benebuself;
    /**
     * 公司缴纳福利小计
     */
    @ApiModelProperty(value="公司缴纳福利小计")
    private Double bencorpTotal;
    /**
     * 个人缴纳福利小计
     */
    @ApiModelProperty(value="个人缴纳福利小计")
    private Double benselfTotal;
    /**
     * 公积金公司缴纳小计
     */
    @ApiModelProperty(value="公积金公司缴纳小计")
    private Double accucorpTotal;
    /**
     * 公积金个人缴纳小计
     */
    @ApiModelProperty(value="公积金个人缴纳小计")
    private Double accuselfTotal;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal pensbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal mdclbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal smdclbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal unembaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isinss;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal inssbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal inssCorp;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal inssSelf;
    }
