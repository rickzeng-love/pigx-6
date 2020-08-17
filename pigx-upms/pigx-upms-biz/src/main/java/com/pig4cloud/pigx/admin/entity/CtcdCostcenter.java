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
 * 成本中心
 *
 * @author gaoxiao
 * @date 2020-06-10 14:07:08
 */
@Data
@TableName("ctcd_costcenter")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "成本中心")
public class CtcdCostcenter extends Model<CtcdCostcenter> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
    /**
     * 编码
     */
    @ApiModelProperty(value="编码")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;
    /**
     * 是否默认 0：默认 1:不磨人
     */
    @ApiModelProperty(value="是否默认 0：默认 1:不磨人")
    private Integer isdefault;
    /**
     * 是否有效 0：有效1失效
     */
    @ApiModelProperty(value="是否有效 0：有效1失效")
    private Integer isdisabled;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 公司corpcode
     */
    @ApiModelProperty(value="公司corpcode")
    private String corpcode;
    /**
     * 公司corpid
     */
    @ApiModelProperty(value="公司corpid")
    private Integer corpid;
    }
