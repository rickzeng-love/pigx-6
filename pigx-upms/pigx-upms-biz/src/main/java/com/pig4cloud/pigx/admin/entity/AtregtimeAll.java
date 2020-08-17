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
 * 加班登记历史
 *
 * @author shishengjie
 * @date 2020-07-22 14:32:05
 */
@Data
@TableName("atregtime_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "加班登记历史")
public class AtregtimeAll extends Model<AtregtimeAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sheetid;
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
     * 分类
     */
    @ApiModelProperty(value="分类")
    private String type;
    /**
     * 请假方式
     */
    @ApiModelProperty(value="请假方式")
    private String freqtype;
    /**
     * 登记项目代码
     */
    @ApiModelProperty(value="登记项目代码")
    private String attendid;
    /**
     * 加班项目
     */
    @ApiModelProperty(value="加班项目")
    private Integer twid;
    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime begintime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endtime;
    /**
     * 长度
     */
    @ApiModelProperty(value="长度")
    private Double amount;
    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
    private String unit;
    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    private LocalDateTime begindate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    private LocalDateTime enddate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer divtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfinstanceid;
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
     * 关账
     */
    @ApiModelProperty(value="关账")
    private Integer closed;
    /**
     * 关账人
     */
    @ApiModelProperty(value="关账人")
    private String closedby;
    /**
     * 关账时间
     */
    @ApiModelProperty(value="关账时间")
    private LocalDateTime closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 原因
     */
    @ApiModelProperty(value="原因")
    private String reason;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime otterm;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer errmsg;
    /**
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String xcontent;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bshift;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfinstid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String wfguid;
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
