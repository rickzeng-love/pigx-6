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
 * @date 2020-07-07 14:07:21
 */
@Data
@TableName("avw_status")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "VIEW")
public class AvwStatus extends Model<AvwStatus> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */

    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
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
     * 轮班组
     */
    @ApiModelProperty(value="轮班组")
    private Integer groupid;
    /**
     * 班组
     */
    @ApiModelProperty(value="班组")
    private Integer teamid;
    /**
     * 生产线
     */
    @ApiModelProperty(value="生产线")
    private Integer lineid;
    /**
     * 考勤员工类型
     */
    @ApiModelProperty(value="考勤员工类型")
    private Integer atemptype;
    /**
     * 考勤管理员
     */
    @ApiModelProperty(value="考勤管理员")
    private Integer agentuserid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer atstatus;
    /**
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 入职日期
     */
    @ApiModelProperty(value="入职日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String joindate;
    /**
     * 实习结束日期
     */
    @ApiModelProperty(value="实习结束日期")
    private String pracenddate;
    /**
     * 实习转正日期
     */
    @ApiModelProperty(value="实习转正日期")
    private String pracconfdate;
    /**
     * 试用结束日期
     */
    @ApiModelProperty(value="试用结束日期")
    private String probenddate;
    /**
     * 试用转正日期
     */
    @ApiModelProperty(value="试用转正日期")
    private LocalDateTime probconfdate;
    /**
     * 离职日期
     */
    @ApiModelProperty(value="离职日期")
    private String leavedate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 考勤账套
     */
    @ApiModelProperty(value="考勤账套")
    private Integer aid;
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
