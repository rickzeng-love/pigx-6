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
 * 打卡地点
 *
 * @author GXS
 * @date 2020-05-22 16:30:27
 */
@Data
@TableName("systattendanceaddress")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "打卡地点")
public class Systattendanceaddress extends Model<Systattendanceaddress> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 打卡地点名称
     */
    @ApiModelProperty(value="打卡地点名称")
    private String attendancename;
    /**
     * 打开地点的经纬度
     */
    @ApiModelProperty(value="打开地点的经纬度")
    private String lnglat;
    /**
     * 打开地点的经度
     */
    @ApiModelProperty(value="打开地点的经度")
    private Double lnglatLng;
    /**
     * 打开地点的维度
     */
    @ApiModelProperty(value="打开地点的维度")
    private Double lnglatLat;
    /**
     * 打卡地址
     */
    @ApiModelProperty(value="打卡地址")
    private String lnglatAddress;
    /**
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    private Integer isused;
    /**
     * 上班时间
     */
    @ApiModelProperty(value="上班时间")
    private String workhour;
    /**
     * 午休时间
     */
    @ApiModelProperty(value="午休时间")
    private String lunchhour;
    /**
     * 下班时间
     */
    @ApiModelProperty(value="下班时间")
    private String closehour;
    /**
     * 打卡有效距离
     */
    @ApiModelProperty(value="打卡有效距离")
    private Double attendancedistance;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String compcode;
    /**
     * 
     */
    @ApiModelProperty(value="组织ID")
    private Integer corpid;
    /**
     * 
     */
    @ApiModelProperty(value="组织编码")
    private String corpcode;
    }
