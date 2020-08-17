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
 * 绩效接口
 *
 * @author shishengjie
 * @date 2020-07-21 14:09:06
 */
@Data
@TableName("ctperformancebonus")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "绩效接口")
public class Ctperformancebonus extends Model<Ctperformancebonus> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 薪资账套
     */
    @ApiModelProperty(value="薪资账套")
    private Integer gid;
    /**
     * 期间
     */
    @ApiModelProperty(value="期间")
    private LocalDateTime term;
    /**
     * 员工编号
     */
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
     * 岗位类型
     */
    @ApiModelProperty(value="岗位类型")
    private Integer jobtype;
    /**
     * 考核周期
     */
    @ApiModelProperty(value="考核周期")
    private Integer pterm;
    /**
     * 最终得分
     */
    @ApiModelProperty(value="最终得分")
    private Double score;
    /**
     * 考核分调整
     */
    @ApiModelProperty(value="考核分调整")
    private Double adjscore;
    /**
     * 绩效奖金标准
     */
    @ApiModelProperty(value="绩效奖金标准")
    private Double perstandard;
    /**
     * 考核分数合计
     */
    @ApiModelProperty(value="考核分数合计")
    private Double standardscore;
    /**
     * 绩效系数
     */
    @ApiModelProperty(value="绩效系数")
    private Double payrate;
    /**
     * 绩效系数(发放)
     */
    @ApiModelProperty(value="绩效系数(发放)")
    private Double payratefact;
    /**
     * 工资标准
     */
    @ApiModelProperty(value="工资标准")
    private Double paystandard;
    /**
     * 绩效奖金
     */
    @ApiModelProperty(value="绩效奖金")
    private Double bonuspay;
    /**
     * 异常
     */
    @ApiModelProperty(value="异常")
    private Integer isexception;
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
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 组织
     */
    @ApiModelProperty(value="组织")
    private String corpcode;
    /**
     * CorpID
     */
    @ApiModelProperty(value="CorpID")
    private Integer corpid;
    }
