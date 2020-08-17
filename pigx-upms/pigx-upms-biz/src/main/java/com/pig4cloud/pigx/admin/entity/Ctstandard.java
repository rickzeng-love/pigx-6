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
 * 薪资模板
 *
 * @author gaoxiao
 * @date 2020-04-22 14:42:54
 */
@Data
@TableName("ctstandard")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资模板")
public class Ctstandard extends Model<Ctstandard> {
private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value="ID")
    private Long id;
    /**
     * 组织ID
     */
    @ApiModelProperty(value="组织ID")
    private Integer corpid;
    /**
     * 员工EID
     */
    @ApiModelProperty(value="员工EID")
    private Integer eid;
    /**
     * 薪资项ID
     */
    @ApiModelProperty(value="薪资项ID")
    private Integer paystditemid;
    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    private String begindate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    private String enddate;
    /**
     * 薪资项值
     */
    @ApiModelProperty(value="薪资项值")
    private String xvalue;
    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
    private String regtime;
    private Integer gid;
    }
