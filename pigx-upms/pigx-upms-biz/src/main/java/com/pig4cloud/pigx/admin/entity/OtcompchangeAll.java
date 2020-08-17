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
 * 公司历史
 *
 * @author gaoxiao
 * @date 2020-04-14 09:34:54
 */
@Data
@TableName("otcompchange_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "公司历史")
public class OtcompchangeAll extends Model<OtcompchangeAll> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
    /**
     * 公司编码
     */
    @ApiModelProperty(value="公司编码")
    private Integer compid;
    /**
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private String compcode;
    /**
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    private String corpcode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value="公司名称")
    private String title;
    /**
     * 公司简称
     */
    @ApiModelProperty(value="公司简称")
    private String compabbr;
    /**
     * 上级公司
     */
    @ApiModelProperty(value="上级公司")
    private Integer adminid;
    /**
     * 总经理
     */
    @ApiModelProperty(value="总经理")
    private Integer director;
    /**
     * 公司级别
     */
    @ApiModelProperty(value="公司级别")
    private Integer compgrade;
    /**
     * 公司类型
     */
    @ApiModelProperty(value="公司类型")
    private Integer comptype;
    /**
     * 公司区域
     */
    @ApiModelProperty(value="公司区域")
    private Integer comparea;
    /**
     * 成立时间
     */
    @ApiModelProperty(value="成立时间")
    private LocalDateTime effectdate;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private LocalDateTime regdate;
    /**
     * 确认
     */
    @ApiModelProperty(value="确认")
    private Integer initialized;
    /**
     * 确认人
     */
    @ApiModelProperty(value="确认人")
    private Integer initializedby;
    /**
     * 确认时间
     */
    @ApiModelProperty(value="确认时间")
    private LocalDateTime initializedtime;
    /**
     * 审核
     */
    @ApiModelProperty(value="审核")
    private Integer submit;
    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private Integer submitby;
    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private LocalDateTime submittime;
    /**
     * 封存
     */
    @ApiModelProperty(value="封存")
    private Integer closed;
    /**
     * 封存人
     */
    @ApiModelProperty(value="封存人")
    private Integer closedby;
    /**
     * 封存时间
     */
    @ApiModelProperty(value="封存时间")
    private LocalDateTime closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 原公司名称
     */
    @ApiModelProperty(value="原公司名称")
    private String titleOld;
    /**
     * 原公司简称
     */
    @ApiModelProperty(value="原公司简称")
    private String compabbrOld;
    /**
     * 原上级公司
     */
    @ApiModelProperty(value="原上级公司")
    private Integer adminidOld;
    /**
     * 原公司级别
     */
    @ApiModelProperty(value="原公司级别")
    private Integer compgradeOld;
    /**
     * 原公司类型
     */
    @ApiModelProperty(value="原公司类型")
    private Integer comptypeOld;
    /**
     * 原公司区域
     */
    @ApiModelProperty(value="原公司区域")
    private Integer compareaOld;
    /**
     * 省
     */
    @ApiModelProperty(value="省")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value="市")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(value="区")
    private String district;
    /**
     * 详细地址
     */
    @ApiModelProperty(value="详细地址")
    private String adress;
    /**
     * 原省
     */
    @ApiModelProperty(value="原省")
    private String provinceOld;
    /**
     * 原市
     */
    @ApiModelProperty(value="原市")
    private String cityOld;
    /**
     * 原区
     */
    @ApiModelProperty(value="原区")
    private String districtOld;
    /**
     * 原详细地址
     */
    @ApiModelProperty(value="原详细地址")
    private String adressOld;
    /**
     * 是否注册用户创建  1：是；0：否
     */
    @ApiModelProperty(value="是否注册用户创建  1：是；0：否")
    private Integer isregister;
    }
