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
 * 累计扣税期间
 *
 * @author gaoxiao
 * @date 2020-06-10 18:22:31
 */
@Data
@TableName("ctcd_decsumperiods")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "累计扣税期间")
public class CtcdDecsumperiods extends Model<CtcdDecsumperiods> {
private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @ApiModelProperty(value="年份")
    private String term;
    /**
     * 代码
     */
    @ApiModelProperty(value="账套")
    private Integer gid;
    /**
     * 开始月份
     */
    @ApiModelProperty(value="开始月份")
    private String begindate;
    /**
     * 结束月份
     */
    @ApiModelProperty(value="结束月份")
    private String enddate;
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
     * 时间
     */
    @ApiModelProperty(value="时间")
    private String initializedtime;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer corpid;

    }
