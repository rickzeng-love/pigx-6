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
 * 
 *
 * @author shishengjie
 * @date 2020-07-22 13:44:59
 */
@Data
@TableName("atshift_total")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class AtshiftTotal extends Model<AtshiftTotal> {
private static final long serialVersionUID = 1L;

    /**
     * 月份
     */
    @TableId
    @ApiModelProperty(value="月份")
    private String term;
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
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private LocalDateTime joindate;
    /**
     * 员工等级
     */
    @ApiModelProperty(value="员工等级")
    private Integer empgrade;
    /**
     * 工作类型
     */
    @ApiModelProperty(value="工作类型")
    private Integer jobtype;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 应工作时数
     */
    @ApiModelProperty(value="应工作时数")
    private Double pnhr;
    /**
     * 旷工天数
     */
    @ApiModelProperty(value="旷工天数")
    private Double uahr;
    /**
     * 迟到分钟
     */
    @ApiModelProperty(value="迟到分钟")
    private Double limn;
    /**
     * 加班3.0(H)
     */
    @ApiModelProperty(value="加班3.0(H)")
    private Double ot3;
    /**
     * 工伤假(D)
     */
    @ApiModelProperty(value="工伤假(D)")
    private Double isjl;
    /**
     * 病假(D)
     */
    @ApiModelProperty(value="病假(D)")
    private Double sicl;
    /**
     * 丧假(D)
     */
    @ApiModelProperty(value="丧假(D)")
    private Double coml;
    /**
     * 事假(D)
     */
    @ApiModelProperty(value="事假(D)")
    private Double perl;
    /**
     * 婚假(D)
     */
    @ApiModelProperty(value="婚假(D)")
    private Double marl;
    /**
     * 产假(D)
     */
    @ApiModelProperty(value="产假(D)")
    private Double matl;
    /**
     * 陪产假(D)
     */
    @ApiModelProperty(value="陪产假(D)")
    private Double patl;
    /**
     * 年假(D)
     */
    @ApiModelProperty(value="年假(D)")
    private Double annl;
    /**
     * 调休(H)
     */
    @ApiModelProperty(value="调休(H)")
    private Double otcl;
    /**
     * 哺乳假(H)
     */
    @ApiModelProperty(value="哺乳假(H)")
    private Double nurl;
    /**
     * 加班1.5(H)
     */
    @ApiModelProperty(value="加班1.5(H)")
    private Double ot1;
    /**
     * 出差(D)
     */
    @ApiModelProperty(value="出差(D)")
    private Double fanf;
    /**
     * 转调休加班(H)
     */
    @ApiModelProperty(value="转调休加班(H)")
    private Double clot;
    /**
     * 加班缺勤(H)
     */
    @ApiModelProperty(value="加班缺勤(H)")
    private Double otab;
    /**
     * 加班2.0(H)
     */
    @ApiModelProperty(value="加班2.0(H)")
    private Double ot2;
    /**
     * 请假出勤(H)
     */
    @ApiModelProperty(value="请假出勤(H)")
    private Double lvwk;
    /**
     * 应工作天数
     */
    @ApiModelProperty(value="应工作天数")
    private Double pndy;
    /**
     * 工作天数
     */
    @ApiModelProperty(value="工作天数")
    private Double wkdy;
    /**
     * 外/工出(H)
     */
    @ApiModelProperty(value="外/工出(H)")
    private Double ofal;
    /**
     * 护理假(D)
     */
    @ApiModelProperty(value="护理假(D)")
    private Double prpl;
    /**
     * 其他(D)
     */
    @ApiModelProperty(value="其他(D)")
    private Double puhl;
    /**
     * 调休天数
     */
    @ApiModelProperty(value="调休天数")
    private Double otcd;
    /**
     * 哺乳假天数
     */
    @ApiModelProperty(value="哺乳假天数")
    private Double nudy;
    /**
     * 工作时数
     */
    @ApiModelProperty(value="工作时数")
    private Double wkhr;
    /**
     * 月综合计时标准
     */
    @ApiModelProperty(value="月综合计时标准")
    private Double mhourlyst;
    /**
     * 月综合计时时数
     */
    @ApiModelProperty(value="月综合计时时数")
    private Double mhourlyh;
    /**
     * 月综合计时超时
     */
    @ApiModelProperty(value="月综合计时超时")
    private Double mhourlym;
    /**
     * 月综合计时缺时
     */
    @ApiModelProperty(value="月综合计时缺时")
    private Double mhourlyc;
    /**
     * 缺勤时数
     */
    @ApiModelProperty(value="缺勤时数")
    private Double abhr;
    /**
     * 缺勤天数
     */
    @ApiModelProperty(value="缺勤天数")
    private Double abdy;
    /**
     * 迟到次数
     */
    @ApiModelProperty(value="迟到次数")
    private Double litm;
    /**
     * 早退分钟
     */
    @ApiModelProperty(value="早退分钟")
    private Double eomn;
    /**
     * 早退次数
     */
    @ApiModelProperty(value="早退次数")
    private Double eotm;
    /**
     * 奇次卡次数
     */
    @ApiModelProperty(value="奇次卡次数")
    private Double cder;
    /**
     * 超时工作(H)
     */
    @ApiModelProperty(value="超时工作(H)")
    private Double rewk;
    /**
     * 晚餐补贴
     */
    @ApiModelProperty(value="晚餐补贴")
    private Double dnan;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;
    /**
     * 公司编码
     */
    @ApiModelProperty(value="公司编码")
    private String corpcode;
    }
