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
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-06-12 16:38:06
 */
@Data
@TableName("cvw_employee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VIEW")
public class CvwEmployee extends Model<CvwEmployee> {
private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    @TableId
    @ApiModelProperty(value="员工编号")
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
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private Integer compid;
    /**
     * 部门代码
     */
    @ApiModelProperty(value="部门代码")
    private Integer depid;
    /**
     * 岗位代码
     */
    @ApiModelProperty(value="岗位代码")
    private Integer jobid;
    /**
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 工作性质
     */
    @ApiModelProperty(value="工作性质")
    private Integer emptype;
    /**
     * 职级
     */
    @ApiModelProperty(value="职级")
    private Integer empgrade;
    /**
     * 入职日期
     */
    @ApiModelProperty(value="入职日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String joindate;
    /**
     * 工作城市
     */
    @ApiModelProperty(value="工作城市")
    private Integer workcity;
    /**
     * 国家
     */
    @ApiModelProperty(value="国家")
    private Integer country;
    /**
     * 离职日期
     */
    @ApiModelProperty(value="离职日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String leavedate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer costid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarystatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarytype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarygrade;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarykind;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer salarycity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer calcway;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer is80;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer paymode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer paystatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankno;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String openbankemp;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankrelateid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bankid2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bankno2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String openbankemp2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a4;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double a5;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    private Integer corpid;
    }
