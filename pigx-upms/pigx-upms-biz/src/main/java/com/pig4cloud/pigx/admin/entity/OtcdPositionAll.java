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
 * 职务历史
 *
 * @author gaoxiao
 * @date 2020-05-29 10:13:57
 */
@Data
@TableName("otcd_position_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "职务历史")
public class OtcdPositionAll extends Model<OtcdPositionAll> {
private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableId
    @ApiModelProperty(value="编码")
    private Integer id;
    /**
     * 代码
     */
    @ApiModelProperty(value="代码")
    private String code;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 所属公司
     */
    @ApiModelProperty(value="所属公司")
    private Integer compid;
    /**
     * 原所属公司
     */
    @ApiModelProperty(value="原所属公司")
    private Integer compidOld;
    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;
    /**
     * 原名称
     */
    @ApiModelProperty(value="原名称")
    private String titleOld;
    /**
     * 职务描述
     */
    @ApiModelProperty(value="职务描述")
    private String description;
    /**
     * 原职务描述
     */
    @ApiModelProperty(value="原职务描述")
    private String descriptionOld;
    /**
     * 能力要求
     */
    @ApiModelProperty(value="能力要求")
    private String competencies;
    /**
     * 原能力要求
     */
    @ApiModelProperty(value="原能力要求")
    private String competenciesOld;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer xorder;
    /**
     * 是否失效
     */
    @ApiModelProperty(value="是否失效")
    private Integer isdisabled;
    /**
     * 是否默认
     */
    @ApiModelProperty(value="是否默认")
    private Integer isdefault;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer regby;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String regname;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private LocalDateTime regdate;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer type;
	private Integer corpid;
    }
