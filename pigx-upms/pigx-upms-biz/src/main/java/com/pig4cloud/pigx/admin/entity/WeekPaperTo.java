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
 * 周报汇报人、抄送人
 *
 * @author gaoxiao
 * @date 2020-06-18 15:29:31
 */
@Data
@TableName("sys_week_paper_to")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "周报汇报人、抄送人")
public class WeekPaperTo extends Model<WeekPaperTo> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer pid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer userid;
    /**
     * 0:汇报 1：抄送
     */
    @ApiModelProperty(value="0:汇报 1：抄送")
    private int type;
	private String portrait;
	private String name;
    }
