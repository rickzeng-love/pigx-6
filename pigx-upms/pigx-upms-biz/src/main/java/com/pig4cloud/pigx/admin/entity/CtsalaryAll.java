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
 * 薪资历史
 *
 * @author gaoxiao
 * @date 2020-06-13 10:20:55
 */
@Data
@TableName("ctsalary_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资历史")
public class CtsalaryAll extends Model<CtsalaryAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * EID
     */
    @ApiModelProperty(value="EID")
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
     * 发放状态
     */
    @ApiModelProperty(value="发放状态")
    private Integer paystatus;
    /**
     * 薪资状态
     */
    @ApiModelProperty(value="薪资状态")
    private Integer salarystatus;
    /**
     * 付薪方式
     */
    @ApiModelProperty(value="付薪方式")
    private Integer paymode;
    /**
     * 成本中心
     */
    @ApiModelProperty(value="成本中心")
    private Integer costid;
    /**
     * 银行
     */
    @ApiModelProperty(value="银行")
    private Integer bankid;
    /**
     * 银行账号
     */
    @ApiModelProperty(value="银行账号")
    private String bankno;
    /**
     * 开户名
     */
    @ApiModelProperty(value="开户名")
    private String openbankemp;
    /**
     * 银行2
     */
    @ApiModelProperty(value="银行2")
    private Integer bankid2;
    /**
     * 银行账号2
     */
    @ApiModelProperty(value="银行账号2")
    private String bankno2;
    /**
     * 开户名2
     */
    @ApiModelProperty(value="开户名2")
    private String openbankemp2;
    /**
     * 薪资类别
     */
    @ApiModelProperty(value="薪资类别")
    private Integer salarytype;
    /**
     * 薪资级别
     */
    @ApiModelProperty(value="薪资级别")
    private Integer salarygrade;
    /**
     * 薪资架构
     */
    @ApiModelProperty(value="薪资架构")
    private Integer salarykind;
    /**
     * 薪资城市
     */
    @ApiModelProperty(value="薪资城市")
    private Integer salarycity;
    /**
     * 基本工资
     */
    @ApiModelProperty(value="基本工资")
    private Double a1;
    /**
     * 职务津贴
     */
    @ApiModelProperty(value="职务津贴")
    private Double a2;
    /**
     * 满勤奖
     */
    @ApiModelProperty(value="满勤奖")
    private Double a3;
    /**
     * 基本工资4
     */
    @ApiModelProperty(value="基本工资4")
    private Double a4;
    /**
     * 基本工资5
     */
    @ApiModelProperty(value="基本工资5")
    private Double a5;
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
     * 手动输入
     */
    @ApiModelProperty(value="手动输入")
    private Integer isspecial;
    /**
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private String corpcode;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    /**
     * 员工组
     */
    @ApiModelProperty(value="员工组")
    private Integer empgroup;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empkind;
    /**
     * 员工级别
     */
    @ApiModelProperty(value="员工级别")
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
    }
