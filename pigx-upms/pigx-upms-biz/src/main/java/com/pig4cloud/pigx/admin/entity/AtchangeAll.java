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
 * 变动登记历史
 *
 * @author gaoxiao
 * @date 2020-07-07 14:02:36
 */
@Data
@TableName("atchange_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "变动登记历史")
public class AtchangeAll extends Model<AtchangeAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 登记日期
     */
    @ApiModelProperty(value="登记日期")
    private LocalDateTime term;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * EID
     */
    @ApiModelProperty(value="EID")
    private Integer eid;
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
     * 原生产线
     */
    @ApiModelProperty(value="原生产线")
    private Integer lineidOld;
    /**
     * 原班组
     */
    @ApiModelProperty(value="原班组")
    private Integer teamidOld;
    /**
     * 原轮班组
     */
    @ApiModelProperty(value="原轮班组")
    private Integer groupidOld;
    /**
     * 原考勤帐套
     */
    @ApiModelProperty(value="原考勤帐套")
    private Integer aidOld;
    /**
     * 原卡号
     */
    @ApiModelProperty(value="原卡号")
    private String cardidOld;
    /**
     * 原考勤状态
     */
    @ApiModelProperty(value="原考勤状态")
    private String atstatusOld;
    /**
     * 生产线
     */
    @ApiModelProperty(value="生产线")
    private Integer lineid;
    /**
     * 班组
     */
    @ApiModelProperty(value="班组")
    private Integer teamid;
    /**
     * 轮班组
     */
    @ApiModelProperty(value="轮班组")
    private Integer groupid;
    /**
     * 考勤帐套
     */
    @ApiModelProperty(value="考勤帐套")
    private Integer aid;
    /**
     * 卡号
     */
    @ApiModelProperty(value="卡号")
    private String cardid;
    /**
     * 考勤状态
     */
    @ApiModelProperty(value="考勤状态")
    private String atstatus;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime begindate;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime enddate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer grpid;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 确认人
     */
    @ApiModelProperty(value="确认人")
    private String initializedby;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private LocalDateTime initializedtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer submit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String submitby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime submittime;
    /**
     * 封账
     */
    @ApiModelProperty(value="封账")
    private Integer closed;
    /**
     * 封账人
     */
    @ApiModelProperty(value="封账人")
    private String closedby;
    /**
     * 封账时间
     */
    @ApiModelProperty(value="封账时间")
    private LocalDateTime closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 原打卡地点
     */
    @ApiModelProperty(value="原打卡地点")
    private String cardaddridOld;
    /**
     * 打卡地点
     */
    @ApiModelProperty(value="打卡地点")
    private String cardaddrid;
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
