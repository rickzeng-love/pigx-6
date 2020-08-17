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

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 对象
 *
 * @author gaoxiao
 * @date 2020-06-26 13:38:52
 */
@Data
@TableName("systdataobjs")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "对象")
public class Systdataobjs extends Model<Systdataobjs> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String title;
    /**
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String objname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String objkey;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer objidx;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer otid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String xtype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer tpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer audit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xaudit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer canaddnew;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer candelete;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer canupdate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlbeforeopen;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlbeforesave;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlaftersave;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlafterclose;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@TableField("`system`")
    private Integer system;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xorder;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer csid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer disabled;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * C：薪资计算 Q:查询
     */
    @ApiModelProperty(value="C：薪资计算 Q:查询")
    private String ftype;
    }
