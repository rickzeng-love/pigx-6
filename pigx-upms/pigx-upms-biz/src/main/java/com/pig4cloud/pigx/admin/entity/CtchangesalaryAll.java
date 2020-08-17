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
 * 薪资变动历史
 *
 * @author gaoxiao
 * @date 2020-06-12 15:16:32
 */
@Data
@TableName("ctchangesalary_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资变动历史")
public class CtchangesalaryAll extends Model<CtchangesalaryAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 变动类型
     */
    @ApiModelProperty(value="变动类型")
    private Integer changetype;
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
     * 组织
     */
    @ApiModelProperty(value="组织")
    private String corpcode;
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
     * 发放状态(原)
     */
    @ApiModelProperty(value="发放状态(原)")
    private Integer paystatusOld;
    /**
     * 薪资状态(原)
     */
    @ApiModelProperty(value="薪资状态(原)")
    private Integer salarystatusOld;
    /**
     * 支付方式(原)
     */
    @ApiModelProperty(value="支付方式(原)")
    private Integer paymodeOld;
    /**
     * 成本中心(原)
     */
    @ApiModelProperty(value="成本中心(原)")
    private Integer costidOld;
    /**
     * 银行(原)
     */
    @ApiModelProperty(value="银行(原)")
    private Integer bankidOld;
    /**
     * 卡号(原)
     */
    @ApiModelProperty(value="卡号(原)")
    private String banknoOld;
    /**
     * 开户名(原)
     */
    @ApiModelProperty(value="开户名(原)")
    private String openbankempOld;
    /**
     * 银行2(原)
     */
    @ApiModelProperty(value="银行2(原)")
    private Integer bankid2Old;
    /**
     * 卡号2(原)
     */
    @ApiModelProperty(value="卡号2(原)")
    private String bankno2Old;
    /**
     * 开户名2(原)
     */
    @ApiModelProperty(value="开户名2(原)")
    private String openbankemp2Old;
    /**
     * 薪资类型(原)
     */
    @ApiModelProperty(value="薪资类型(原)")
    private Integer salarytypeOld;
    /**
     * 薪资架构(原)
     */
    @ApiModelProperty(value="薪资架构(原)")
    private Integer salarykindOld;
    /**
     * 薪资城市(新)
     */
    @ApiModelProperty(value="薪资城市(新)")
    private Integer salarycityOld;
    /**
     * 发放状态(新)
     */
    @ApiModelProperty(value="发放状态(新)")
    private Integer paystatus;
    /**
     * 薪资状态(新)
     */
    @ApiModelProperty(value="薪资状态(新)")
    private Integer salarystatus;
    /**
     * 支付方式(新)
     */
    @ApiModelProperty(value="支付方式(新)")
    private Integer paymode;
    /**
     * 成本中心(新)
     */
    @ApiModelProperty(value="成本中心(新)")
    private Integer costid;
    /**
     * 银行(新)
     */
    @ApiModelProperty(value="银行(新)")
    private Integer bankid;
    /**
     * 账号(新)
     */
    @ApiModelProperty(value="账号(新)")
    private String bankno;
    /**
     * 开户名(新)
     */
    @ApiModelProperty(value="开户名(新)")
    private String openbankemp;
    /**
     * 银行2(新)
     */
    @ApiModelProperty(value="银行2(新)")
    private Integer bankid2;
    /**
     * 账号2(新)
     */
    @ApiModelProperty(value="账号2(新)")
    private String bankno2;
    /**
     * 开户名2(新)
     */
    @ApiModelProperty(value="开户名2(新)")
    private String openbankemp2;
    /**
     * 薪资类型(新)
     */
    @ApiModelProperty(value="薪资类型(新)")
    private Integer salarytype;
    /**
     * 薪资架构(新)
     */
    @ApiModelProperty(value="薪资架构(新)")
    private Integer salarykind;
    /**
     * 薪资城市(新)
     */
    @ApiModelProperty(value="薪资城市(新)")
    private Integer salarycity;
    /**
     * 原因
     */
    @ApiModelProperty(value="原因")
    private String reason;
    /**
     * 生效时间
     */
    @ApiModelProperty(value="生效时间")
    private String effectdate;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private String regdate;
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
    private String initializedtime;
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
    private String submittime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 薪资计算方式(新)
     */
    @ApiModelProperty(value="薪资计算方式(新)")
    private Integer calcway;
    /**
     * 薪资计算方式(原)
     */
    @ApiModelProperty(value="薪资计算方式(原)")
    private Integer calcwayOld;
    /**
     * 手动输入
     */
    @ApiModelProperty(value="手动输入")
    private Integer isspecial;
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
    private Integer empproperty;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empcategory;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarygradeOld;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarygrade;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a1Old;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a2Old;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a3Old;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a4Old;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a5Old;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a4;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal a5;
    }
