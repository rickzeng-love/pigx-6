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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 考勤信息查看

 *
 * @author gaoxiao
 * @date 2020-05-30 11:42:38
 */
@Data
@TableName("atstatus")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "考勤信息查看 ")
public class Atstatus extends Model<Atstatus> {
private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="")
	private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String term;
    /**
     * 
     */

    /**
     * 
     */
    @ApiModelProperty(value="")
    private String badge;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer groupid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer teamid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer lineid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String atbadge;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer atemptype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer agentuserid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer aid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer atstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String bus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String cardaddrid;
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
