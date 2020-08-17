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
 * 列
 *
 * @author gaoxiao
 * @date 2020-06-26 13:39:13
 */
@Data
@TableName("systdatacols")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "列")
public class Systdatacols extends Model<Systdatacols> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 对象id
     */
    @ApiModelProperty(value="对象id")
    private Integer objid;
    /**
     * 列名称
     */
    @ApiModelProperty(value="列名称")
    private String colname;
    /**
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String objname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String colkey;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title2;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title3;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title4;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String title5;
    /**
     * 是否主键
     */
    @ApiModelProperty(value="是否主键")
    private Integer xpkey;
    /**
     * 是否自增
     */
    @ApiModelProperty(value="是否自增")
    private Integer xidentity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xmultilingual;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xnotnull;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xencrypt;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer cansearch;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer canfilter;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer cansort;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xrefresh;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xsummary;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer datawidth;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xprec;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer audit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer dtid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String nfid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer width;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer dpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer rsid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer rsvalid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer rstran;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String rsource;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String editmask;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer locked;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer hidden;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer hiddenx;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer align;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlvaladdnew;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlvalupdate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlval;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String sqlvalcheck;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xtop;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xleft;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xheight;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xwidth;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xwidthlbl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xrow;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xcol;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xrows;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xcols;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xlblcol;
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
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xcrlf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xaudit;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer agentid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String hyperlink;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xcolspan;
    /**
     * 是否作为数据源 1：是
     */
    @ApiModelProperty(value="是否作为数据源 1：是")
    private Integer ispayds;
    }
