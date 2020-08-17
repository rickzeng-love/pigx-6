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
 * 日结果汇总
 *
 * @author gaoxiao
 * @date 2020-06-22 11:40:32
 */
@Data
@TableName("atplan_range")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "日结果汇总")
public class AtplanRange extends Model<AtplanRange> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private String term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer groupid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String shift;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer daytype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String begintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String endtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String scanbegintime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String scanendtime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer analymode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer processflag;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String initializedby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String initializedtime;
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
    private String submittime;
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
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardendtime1;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardendtime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardbegintime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime cardendtime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double shouldworktime;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String begintime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime endtime2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String begintime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String endtime3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer worknum;
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
