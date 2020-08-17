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
 * 员工附件
 *
 * @author gaoxiao
 * @date 2020-04-16 11:36:31
 */
@Data
@TableName("etbg_accessorymaterials")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工附件")
public class EtbgAccessorymaterials extends Model<EtbgAccessorymaterials> {
private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value="ID")
    private Integer id;
	private Integer eid;
    /**
     * 附件材料编码UUID
     */
    @ApiModelProperty(value="附件材料编码UUID")
    private String code;
    /**
     * 组织编号
     */
    @ApiModelProperty(value="组织编号")
    private String corpcode;
    /**
     * 账号(手机号)
     */
    @ApiModelProperty(value="账号(手机号)")
    private String account;
	/**
	 * 员工id
	 */
	@ApiModelProperty(value="员工id")
	private Integer userid;
    /**
     * 离职证明
     */
    @ApiModelProperty(value="离职证明")
    private String lzzm;
    /**
     * 体检证明
     */
    @ApiModelProperty(value="体检证明")
    private String tjzm;
    /**
     * 身份证正面
     */
    @ApiModelProperty(value="身份证正面")
    private String sfzzm;
    /**
     * 身份证反面
     */
    @ApiModelProperty(value="身份证反面")
    private String sfzfm;
    /**
     * 学历证
     */
    @ApiModelProperty(value="学历证")
    private String xlz;
    /**
     * 学位证
     */
    @ApiModelProperty(value="学位证")
    private String xwz;
    /**
     * 学信网验证
     */
    @ApiModelProperty(value="学信网验证")
    private String xxwyz;
    /**
     * 银行卡
     */
    @ApiModelProperty(value="银行卡")
    private String yhk;
    /**
     * 是否有效0：有效 1：失效
     */
    @ApiModelProperty(value="是否有效0：有效 1：失效")
    private Integer isdisable;
	@TableField(exist=false)
    private String name;
    }
