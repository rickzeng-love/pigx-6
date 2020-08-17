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
 * 福利变动历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:25:58
 */
@Data
@TableName("ctchangebenefit_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "福利变动历史")
public class CtchangebenefitAll extends Model<CtchangebenefitAll> {
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
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 员工类型
     */
    @ApiModelProperty(value="员工类型")
    private Integer emptype;
    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private LocalDateTime joindate;
    /**
     * 工作城市
     */
    @ApiModelProperty(value="工作城市")
    private Integer workcity;
    /**
     * 福利状态(原)
     */
    @ApiModelProperty(value="福利状态(原)")
    private Integer benstatusOld;
    /**
     * 福利状态(新)
     */
    @ApiModelProperty(value="福利状态(新)")
    private Integer benstatus;
    /**
     * 福利城市(原)
     */
    @ApiModelProperty(value="福利城市(原)")
    private Integer bencityOld;
    /**
     * 福利城市(新)
     */
    @ApiModelProperty(value="福利城市(新)")
    private Integer bencity;
    /**
     * 福利地区(原)
     */
    @ApiModelProperty(value="福利地区(原)")
    private Integer benareaOld;
    /**
     * 福利地区(新)
     */
    @ApiModelProperty(value="福利地区(新)")
    private Integer benarea;
    /**
     * 承担方式(原)
     */
    @ApiModelProperty(value="承担方式(原)")
    private Integer beartypeOld;
    /**
     * 承担方式(新)
     */
    @ApiModelProperty(value="承担方式(新)")
    private Integer beartype;
    /**
     * 福利基数(原)
     */
    @ApiModelProperty(value="福利基数(原)")
    private Double benbaseOld;
    /**
     * 福利基数(新)
     */
    @ApiModelProperty(value="福利基数(新)")
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
     * 组织
     */
    @ApiModelProperty(value="组织")
    private String corpcode;
    /**
     * 是否缴纳大病医疗
     */
    @ApiModelProperty(value="是否缴纳大病医疗")
    private Integer issmdcl;
    /**
     * 大病医疗基数
     */
    @ApiModelProperty(value="大病医疗基数")
    private Double smdclbase;
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
     * 生效时间
     */
    @ApiModelProperty(value="生效时间")
    private LocalDateTime effectdate;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private LocalDateTime regdate;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 确认人
     */
    @ApiModelProperty(value="确认人")
    private Integer initializedby;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private LocalDateTime initializedtime;
    /**
     * 审核
     */
    @ApiModelProperty(value="审核")
    private Integer submit;
    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private Integer submitby;
    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private LocalDateTime submittime;
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
    private Integer empgroup;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empkind;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empgrade;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empcategory;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empproperty;
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
    }
