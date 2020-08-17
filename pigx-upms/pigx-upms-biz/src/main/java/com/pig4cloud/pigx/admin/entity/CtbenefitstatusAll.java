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
 * 
 *
 * @author shishengjie
 * @date 2020-07-21 15:54:19
 */
@Data
@TableName("ctbenefitstatus_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "")
public class CtbenefitstatusAll extends Model<CtbenefitstatusAll> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private LocalDateTime term;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer eid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String badge;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String name;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer compid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer depid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer jobid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer cityid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer benstatus;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer bencity;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer benarea;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer beartype;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double benbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isaccu;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double accubase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer ismdcl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double mdclbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer ispens;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double pensbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isunem;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double unembase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isbabs;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double babsbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isinjs;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double injsbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isinss;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double inssbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer issmdcl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double smdclbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer issuaccu;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double suaccubase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer issumdcl;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double sumdclbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer issupen;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double supenbase;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer gid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer corpid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double pensbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double mdclbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double smdclbaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Double unembaseSelf;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String corpcode;	/**
	 * 生效时间
	 */
	@ApiModelProperty(value = "生效时间")
	private String effectdate;

    }
