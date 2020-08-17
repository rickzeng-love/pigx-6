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
 * 薪资项值(输入表)
 *
 * @author gaoxiao
 * @date 2020-06-15 11:25:27
 */
@Data
@TableName("systpayiteminputvalue")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "薪资项值(输入表)")
public class Systpayiteminputvalue extends Model<Systpayiteminputvalue> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Long id;
    /**
     * 薪资期间
     */
    @ApiModelProperty(value="薪资期间")
    private String term;
    /**
     * 公司corpid
     */
    @ApiModelProperty(value="公司corpid")
    private Integer corpid;
    /**
     * 账套号
     */
    @ApiModelProperty(value="账套号")
    private Integer gid;
    /**
     * 员工eid
     */
    @ApiModelProperty(value="员工eid")
    private Integer eid;
    /**
     * 薪资项id
     */
    @ApiModelProperty(value="薪资项id")
    private Integer payitem;
    /**
     * 薪资项值
     */
    @ApiModelProperty(value="薪资项值")
    private Double factvalue;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 所属公司corpcode
     */
    @ApiModelProperty(value="所属公司corpcode")
    private String corpcode;

    /**
	 * systPayrollEmpInfo表id
	 * */
	@ApiModelProperty(value="")
	private Long seqid;
    }
