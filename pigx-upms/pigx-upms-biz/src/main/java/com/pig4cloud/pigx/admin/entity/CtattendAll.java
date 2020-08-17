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
 * 考勤接口历史
 *
 * @author gaoxiao
 * @date 2020-06-13 11:26:18
 */
@Data
@TableName("ctattend_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考勤接口历史")
public class CtattendAll extends Model<CtattendAll> {
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
    private LocalDateTime term;
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
    private String depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 员工级别
     */
    @ApiModelProperty(value="员工级别")
    private Integer empgrade;
    /**
     * 节假日天数
     */
    @ApiModelProperty(value="节假日天数")
    private Double holidays;
    /**
     * 刷卡出勤
     */
    @ApiModelProperty(value="刷卡出勤")
    private Double wkdycard;
    /**
     * 奇次卡次数
     */
    @ApiModelProperty(value="奇次卡次数")
    private Double cder;
    /**
     * 应工作天数
     */
    @ApiModelProperty(value="应工作天数")
    private Double pndy;
    /**
     * 应工作时数
     */
    @ApiModelProperty(value="应工作时数")
    private Double pnhr;
    /**
     * 工作时数
     */
    @ApiModelProperty(value="工作时数")
    private Double wkhr;
    /**
     * 总工作天数
     */
    @ApiModelProperty(value="总工作天数")
    private Double wkdy;
    /**
     * 迟到分钟
     */
    @ApiModelProperty(value="迟到分钟")
    private Double limn;
    /**
     * 早退分钟
     */
    @ApiModelProperty(value="早退分钟")
    private Double eomn;
    /**
     * 迟到次数
     */
    @ApiModelProperty(value="迟到次数")
    private Double litm;
    /**
     * 早退次数
     */
    @ApiModelProperty(value="早退次数")
    private Double eotm;
    /**
     * 缺勤天数
     */
    @ApiModelProperty(value="缺勤天数")
    private Double abdy;
    /**
     * 缺勤时数
     */
    @ApiModelProperty(value="缺勤时数")
    private Double abhr;
    /**
     * 旷工天数
     */
    @ApiModelProperty(value="旷工天数")
    private Double uahr;
    /**
     * 加班缺勤(H)
     */
    @ApiModelProperty(value="加班缺勤(H)")
    private Double otab;
    /**
     * 加班1.5(H)
     */
    @ApiModelProperty(value="加班1.5(H)")
    private Double ot1;
    /**
     * 加班2.0(H)
     */
    @ApiModelProperty(value="加班2.0(H)")
    private Double ot2;
    /**
     * 加班3.0(H)
     */
    @ApiModelProperty(value="加班3.0(H)")
    private Double ot3;
    /**
     * 工作日出差(H)
     */
    @ApiModelProperty(value="工作日出差(H)")
    private Double bot1;
    /**
     * 双休日出差(H)
     */
    @ApiModelProperty(value="双休日出差(H)")
    private Double bot2;
    /**
     * 节假日出差(H)
     */
    @ApiModelProperty(value="节假日出差(H)")
    private Double bot3;
    /**
     * 转调休加班(H)
     */
    @ApiModelProperty(value="转调休加班(H)")
    private Double clot;
    /**
     * 病假(D)
     */
    @ApiModelProperty(value="病假(D)")
    private Double sicl;
    /**
     * 事假(D)
     */
    @ApiModelProperty(value="事假(D)")
    private Double perl;
    /**
     * 年假(D)
     */
    @ApiModelProperty(value="年假(D)")
    private Double annl;
    /**
     * 丧假(D)
     */
    @ApiModelProperty(value="丧假(D)")
    private Double coml;
    /**
     * 婚假(D)
     */
    @ApiModelProperty(value="婚假(D)")
    private Double marl;
    /**
     * 护理假(D)
     */
    @ApiModelProperty(value="护理假(D)")
    private Double prpl;
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
     * 调休(H)
     */
    @ApiModelProperty(value="调休(H)")
    private Double otcl;
    /**
     * 其他(D)
     */
    @ApiModelProperty(value="其他(D)")
    private Double puhl;
    /**
     * 哺乳假(H)
     */
    @ApiModelProperty(value="哺乳假(H)")
    private Double nurl;
    /**
     * 工伤假(D)
     */
    @ApiModelProperty(value="工伤假(D)")
    private Double isjl;
    /**
     * 出差(D)
     */
    @ApiModelProperty(value="出差(D)")
    private Double fanf;
    /**
     * 外/工出(H)
     */
    @ApiModelProperty(value="外/工出(H)")
    private Double ofal;
    /**
     * 请假出勤(H)
     */
    @ApiModelProperty(value="请假出勤(H)")
    private Double lvwk;
    /**
     * 超时工作(H)
     */
    @ApiModelProperty(value="超时工作(H)")
    private Double rewk;
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
     * 签发
     */
    @ApiModelProperty(value="签发")
    private Integer initialized;
    /**
     * 签发人
     */
    @ApiModelProperty(value="签发人")
    private Integer initializedby;
    /**
     * 签发时间
     */
    @ApiModelProperty(value="签发时间")
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
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;
    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private LocalDateTime joindate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal dnan;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private BigDecimal wkdyallwork;
    }
