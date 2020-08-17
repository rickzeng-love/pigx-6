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
 * 教育经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:36:11
 */
@Data
@TableName("etbg_education")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "教育经历")
public class EtbgEducation extends Model<EtbgEducation> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 员工编码
     */
    @ApiModelProperty(value="员工编码")
    private Integer eid;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    private String begindate;
    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    private String enddate;
    /**
     * 学校名称
     */
    @ApiModelProperty(value="学校名称")
    private String schoolname;
    /**
     * 毕业类型
     */
    @ApiModelProperty(value="毕业类型")
    private Integer gradtype;
    /**
     * 学习形式
     */
    @ApiModelProperty(value="学习形式")
    private Integer studytype;
    /**
     * 学历类型
     */
    @ApiModelProperty(value="学历类型")
    private Integer edutype;
    /**
     * 学位类型
     */
    @ApiModelProperty(value="学位类型")
    private Integer degreetype;
    /**
     * 学位名称
     */
    @ApiModelProperty(value="学位名称")
    private String degreename;
    /**
     * 专业
     */
    @ApiModelProperty(value="专业")
    private String major;
    /**
     * 学历证书编号
     */
    @ApiModelProperty(value="学历证书编号")
    private String eduno;
    /**
     * 学历获得时间
     */
    @ApiModelProperty(value="学历获得时间")
    private String edunodate;
    /**
     * 学位证书编号
     */
    @ApiModelProperty(value="学位证书编号")
    private String degreeno;
    /**
     * 学位获得时间
     */
    @ApiModelProperty(value="学位获得时间")
    private String degreenodate;
    /**
     * 学校地址
     */
    @ApiModelProperty(value="学校地址")
    private String schoolplace;
    /**
     * 证明人
     */
    @ApiModelProperty(value="证明人")
    private String reference;
    /**
     * 联系方式
     */
    @ApiModelProperty(value="联系方式")
    private String tel;
    /**
     * 海外教育
     */
    @ApiModelProperty(value="海外教育")
    private Integer isout;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 是否全日制
     */
    @ApiModelProperty(value="是否全日制")
    private Integer isfulltimehigh;
    /**
     * 学历证书(附件)
     */
    @ApiModelProperty(value="学历证书(附件)")
    private String educert;
    /**
     * 学位证书(附件)
     */
    @ApiModelProperty(value="学位证书(附件)")
    private String degreecert;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 所属公司
     */
    @ApiModelProperty(value="所属公司")
    private Integer compid;
    /**
     * 所属部门
     */
    @ApiModelProperty(value="所属部门")
    private Integer depid;
    /**
     * 岗位
     */
    @ApiModelProperty(value="岗位")
    private Integer jobid;
    /**
     * 学信网验证
     */
    @ApiModelProperty(value="学信网验证")
    private String educver;
    /**
     * 其他语种
     */
    @ApiModelProperty(value="其他语种")
    private Integer otherlanguages;
    /**
     * 熟练程度
     */
    @ApiModelProperty(value="熟练程度")
    private Integer proficiency;
    /**
     * 所获证书
     */
    @ApiModelProperty(value="所获证书")
    private String credLanguage;
    /**
     * 获得日期
     */
    @ApiModelProperty(value="获得日期")
    private String creddate;
	/**
	 * 学制
	 */
	@ApiModelProperty(value="学制")
	private Integer edu_length;


	/**
	 * 组织code
	 */
	@ApiModelProperty(value="组织code")
	private String corpcode;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private Integer userid;
	private Integer isdisabled;

}
