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
import java.util.List;

/**
 * 轮班组-轮班定义
 *
 * @author gaoxiao
 * @date 2020-07-06 16:27:03
 */
@Data
@TableName("atgroup_turn")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "轮班组-轮班定义")
public class AtgroupTurn extends Model<AtgroupTurn> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
	@ApiModelProperty(value="")
	private Integer id;
    @ApiModelProperty(value="")
    private Integer groupid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer turnid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer referpoint;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer xorder;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer initialized;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer ischeck;
	private Integer corpid;
	private String corpcode;
	@TableField(exist=false)
	private String shiftGroupTitle;
	@TableField(exist=false)
	private List<AtshiftGroupSub> atShiftGroupSubList;


}
