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
 * 关怀提醒
 *
 * @author gaoxiao
 * @date 2020-04-26 19:51:15
 */
@Data
@TableName("sys_warn")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "关怀提醒")
public class Warn extends Model<Warn> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Long id;
    /**
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    private Integer corpid;
    /**
     * 组织code
     */
    @ApiModelProperty(value="组织code")
    private String corpcode;
    /**
     * 员工序号
     */
    @ApiModelProperty(value="员工序号")
    private Integer eid;
    /**
     * 提醒内容
     */
    @ApiModelProperty(value="提醒内容")
    private String content;
    /**
     * 提醒类型 1、员工关怀2、入职周年
     */
    @ApiModelProperty(value="提醒类型 1、员工关怀2、入职周年")
    private Integer type;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private String createdate;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private String updatedate;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Integer createuserid;
    /**
     * 是否有效 0：有效1失效
     */
    @ApiModelProperty(value="是否有效 0：有效1失效")
    private Integer isdisable;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer issubmit;
    }
