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
 * 导入日志
 *
 * @author gaoxiao
 * @date 2020-07-07 14:56:11
 */
@Data
@TableName("atimport_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "导入日志")
public class AtimportLog extends Model<AtimportLog> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 导入类型
     */
    @ApiModelProperty(value="导入类型")
    private String dstype;
    /**
     * 数据源
     */
    @ApiModelProperty(value="数据源")
    private String datasource;
    /**
     * 导入日期
     */
    @ApiModelProperty(value="导入日期")
    private LocalDateTime term;
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
     * 总记录
     */
    @ApiModelProperty(value="总记录")
    private Integer recordnum;
    /**
     * 有效记录
     */
    @ApiModelProperty(value="有效记录")
    private Integer validnum;
    /**
     * 无效记录
     */
    @ApiModelProperty(value="无效记录")
    private Integer errnum;
    /**
     * 操作用户
     */
    @ApiModelProperty(value="操作用户")
    private String userid;
    /**
     * 导入方式
     */
    @ApiModelProperty(value="导入方式")
    private Integer importmode;
    /**
     * 文件名称
     */
    @ApiModelProperty(value="文件名称")
    private String filename;
    /**
     * 错误信息
     */
    @ApiModelProperty(value="错误信息")
    private String errmsg;
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
