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
 * 补卡登记历史
 *
 * @author shishengjie
 * @date 2020-07-22 11:43:42
 */
@Data
@TableName("atcardlost_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "补卡登记历史")
public class AtcardlostAll extends Model<AtcardlostAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序列
     */
    @TableId
    @ApiModelProperty(value="序列")
    private Integer id;
    /**
     * 登记日期
     */
    @ApiModelProperty(value="登记日期")
    private LocalDateTime term;
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
     * 补卡时间
     */
    @ApiModelProperty(value="补卡时间")
    private LocalDateTime xdatetime;
    /**
     * 进出
     */
    @ApiModelProperty(value="进出")
    private Integer inoutflag;
    /**
     * 班次日期
     */
    @ApiModelProperty(value="班次日期")
    private LocalDateTime shiftterm;
    /**
     * 原因
     */
    @ApiModelProperty(value="原因")
    private String reason;
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer closed;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String closedby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer flag;
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
    private String cardlostplace;
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
