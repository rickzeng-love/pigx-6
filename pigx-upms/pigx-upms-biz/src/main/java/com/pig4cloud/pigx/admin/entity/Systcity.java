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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author gaoxiao
 * @date 2020-03-24 16:01:55
 */
@Data
@TableName("systcity")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class Systcity extends Model<Systcity> {
private static final long serialVersionUID = 1L;

    /**
     * 城市code
     */
    @ApiModelProperty(value="城市code")
    private Integer  code;

    /**
     * 城市名称
     */
    @ApiModelProperty(value="城市名称")
    private String name;

    }
