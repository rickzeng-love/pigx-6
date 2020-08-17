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
 * 规章制度
 *
 * @author gaoxiao
 * @date 2020-04-17 15:24:49
 */
@Data
@TableName("oatportaletdocument")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "规章制度")
public class Oatportaletdocument extends Model<Oatportaletdocument> {
private static final long serialVersionUID = 1L;

    /**
     * 组织code
     */
    @ApiModelProperty(value="组织code")
    private String corpcode;
    /**
     * 文档ID
     */
    @TableId
    @ApiModelProperty(value="文档ID")
    private Integer docid;
    /**
     * 文档名称
     */
    @ApiModelProperty(value="文档名称")
    private String title;
    /**
     * 附件ID
     */
    @ApiModelProperty(value="附件ID")
    private Integer docfileid;
    /**
     * 附件名称
     */
    @ApiModelProperty(value="附件名称")
    private String docfilename;
    /**
     * 附件路径
     */
    @ApiModelProperty(value="附件路径")
    private String docfilepath;
    /**
     * 附件大小（KB）
     */
    @ApiModelProperty(value="附件大小（KB）")
    private Integer filesize;
    /**
     * 附件类型
     */
    @ApiModelProperty(value="附件类型")
    private String filetype;
    /**
     * 发布
     */
    @ApiModelProperty(value="发布")
    private Integer isshow;
    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private String createdate;
    /**
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    private Integer createuserid;
    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String createusername;
    /**
     * 更新日期
     */
    @ApiModelProperty(value="更新日期")
    private String updatedate;
    /**
     * 更新人ID
     */
    @ApiModelProperty(value="更新人ID")
    private Integer updateuserid;
    /**
     * 文档类型
     */
    @ApiModelProperty(value="文档类型")
    private Integer doctype;
    /**
     * 是否置顶
     */
    @ApiModelProperty(value="是否置顶")
    private Integer istop;
    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private String updateusername;
    /**
     * 是否课件
     */
    @ApiModelProperty(value="是否课件")
    private Integer isdisabled;
    /**
     * 最后修改人
     */
    @ApiModelProperty(value="最后修改人")
    private Integer lastuser;
    }
