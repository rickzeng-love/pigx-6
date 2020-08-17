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
 * 薪资考勤账套对应
 *
 * @author gaoxiao
 * @date 2020-07-22 14:07:55
 */
@Data
@TableName("ctcd_gid_agentmode")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资考勤账套对应")
public class CtcdGidAgentmode extends Model<CtcdGidAgentmode> {
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
     * 考勤账套
     */
    @ApiModelProperty(value="考勤账套")
    private Integer agentmode;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer compid;
    }
