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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工信息表
 *
 * @author gaoxiao
 * @date 2020-04-08 13:22:25
 */
@Data
@TableName("etemployee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工信息表")
public class Etemployee extends Model<Etemployee> {
private static final long serialVersionUID = 1L;
/*
	@Null 被注释的元素必须为null
	@NotNull 被注释的元素不能为null
	@AssertTrue 被注释的元素必须为true
	@AssertFalse 被注释的元素必须为false
	@Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
	@Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
	@DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
	@DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
	@Size(max,min) 被注释的元素的大小必须在指定的范围内。
	@Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
	@Past 被注释的元素必须是一个过去的日期
	@Future 被注释的元素必须是一个将来的日期
	@Pattern(value) 被注释的元素必须符合指定的正则表达式。
	@Email 被注释的元素必须是电子邮件地址
	@Length 被注释的字符串的大小必须在指定的范围内
	@NotEmpty 被注释的字符串必须非空
	@Range 被注释的元素必须在合适的范围内
	*/
    /**
     * 员工编号
     */
    @TableId
    @ApiModelProperty(value="员工编号")
    private Integer eid;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
	@NotNull(message = "请输入员工号！")
    private String badge;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
	@NotNull(message = "姓名不能为空！")
    private String name;
	/**
	 * 曾姓名
	 */
	@ApiModelProperty(value="曾姓名")
	private String name2;
    /**
     * 英文名
     */
    @ApiModelProperty(value="英文名")
    private String ename;
    /**
     * 组织代码
     */
    @ApiModelProperty(value="组织代码")
    private String corpcode;
	/**
	 * 组织ID
	 */
	@ApiModelProperty(value="组织ID")
	private Integer corpid;
    /**
     * 公司代码
     */
    @ApiModelProperty(value="公司代码")
    private Integer compid;
    /**
     * 部门代码
     */
    @ApiModelProperty(value="部门代码")
	@NotNull(message = "请选择部门!")
    private Integer depid;
    /**
     * 岗位代码
     */
    @ApiModelProperty(value="岗位代码")
	@NotNull(message = "请选择岗位！")
    private Integer jobid;
    /**
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
	@NotNull(message = "请选择员工状态！")
    private Integer empstatus;
	/**
	 * 员工状态在职
	 */
	@TableField(exist=false)
	@ApiModelProperty(value="员工状态在职")
	private Integer empstatuszaizhi;



    /**
     * 上级领导
     */
    @ApiModelProperty(value="上级领导")
    private Integer reportto;
    /**
     * 上级领导名称
     */
    @ApiModelProperty(value="上级领导名称")
    private String reporttoname;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer wfreportto;
    /**
     * 职能上级名称
     */
    @ApiModelProperty(value="职能上级名称")
    private String wfreporttoname;
    /**
     * 员工类型
     */
    @ApiModelProperty(value="员工类型")
    private Integer emptype;
	/**
	 * 员工类型-全职
	 */
	@ApiModelProperty(value="员工类型-全职")
	@TableField(exist=false)
	private Integer emptypequanzhi;
	/**
	 * 员工类型-非全职
	 */
	@ApiModelProperty(value="员工类型-非全职")
	@TableField(exist=false)
	private Integer emptypefeiquanzhi;
    /**
     * 职务
     */
    @ApiModelProperty(value="职务")
    private Integer position;
    /**
     * 职等
     */
    @ApiModelProperty(value="职等")
    private Integer posgrade;
    /**
     * 职级
     */
    @ApiModelProperty(value="职级")
    private Integer empgrade;
    /**
     * 工作城市
     */
    @ApiModelProperty(value="工作城市")
    private Integer workcity;
    /**
     * 入职类型
     */
    @ApiModelProperty(value="入职类型")
    private Integer jointype;
    /**
     * 入职日期
     */
    @ApiModelProperty(value="入职日期")
	@NotNull(message = "请选择入职日期！")
    private String joindate;
    /**
     * 调整司龄(月)
     */
    @ApiModelProperty(value="调整司龄(月)")
    private Double addsyear;
    /**
     * 参加工作日
     */
    @ApiModelProperty(value="参加工作日")
    private String workbegindate;
    /**
     * 调整工龄(月)
     */
    @ApiModelProperty(value="调整工龄(月)")
    private Double addwyear;
    /**
     * 从岗日期
     */
    @ApiModelProperty(value="从岗日期")
    private String jobbegindate;
    /**
     * 实习开始日期
     */
    @ApiModelProperty(value="实习开始日期")
    private String pracbegindate;
    /**
     * 实习期(月)
     */
    @ApiModelProperty(value="实习期(月)")
    private Integer practerm;
    /**
     * 实习结束日期
     */
    @ApiModelProperty(value="实习结束日期")
    private String pracenddate;
    /**
     * 实习转正日期
     */
    @ApiModelProperty(value="实习转正日期")
    private String pracconfdate;
    /**
     * 见习开始日期
     */
    @ApiModelProperty(value="见习开始日期")
    private String procbegindate;
    /**
     * 见习期(月)
     */
    @ApiModelProperty(value="见习期(月)")
    private Integer procterm;
    /**
     * 见习结束日期
     */
    @ApiModelProperty(value="见习结束日期")
    private String procenddate;
    /**
     * 见习转正日期
     */
    @ApiModelProperty(value="见习转正日期")
    private String procconfdate;
    /**
     * 试用开始日期
     */
    @ApiModelProperty(value="试用开始日期")
    private String probbegindate;
    /**
     * 试用期(月)
     */
    @ApiModelProperty(value="试用期(月)")
    private Integer probterm;
    /**
     * 试用结束日期
     */
    @ApiModelProperty(value="试用结束日期")
    private String probenddate;
    /**
     * 试用转正日期
     */
    @ApiModelProperty(value="试用转正日期")
    private String probconfdate;
    /**
     * 合同签订次数
     */
    @ApiModelProperty(value="合同签订次数")
    private Integer concount;
    /**
     * 签约单位
     */
    @ApiModelProperty(value="签约单位")
    private Integer contractunit;
    /**
     * 合同类型
     */
    @ApiModelProperty(value="合同类型")
    private Integer contype;
    /**
     * 合同属性
     */
    @ApiModelProperty(value="合同属性")
    private Integer conproperty;
    /**
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    private String conno;
    /**
     * 合同开始日期
     */
    @ApiModelProperty(value="合同开始日期")
    private String conbegindate;
    /**
     * 合同期(月)
     */
    @ApiModelProperty(value="合同期(月)")
    private Integer conterm;
    /**
     * 合同结束日期
     */
    @ApiModelProperty(value="合同结束日期")
    private String conenddate;
    /**
     * 合同中止日期
     */
    @ApiModelProperty(value="合同中止日期")
    private String constopdate;
    /**
     * 离职日期
     */
    @ApiModelProperty(value="离职日期")
    private String leavedate;
    /**
     * 离职类型
     */
    @ApiModelProperty(value="离职类型")
    private Integer leavetype;
    /**
     * 离职原因
     */
    @ApiModelProperty(value="离职原因")
    private Integer leavereason;
    /**
     * 国家
     */
    @ApiModelProperty(value="国家")
	@NotNull(message = "请选择国籍！")
    private Integer country;
    /**
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
	@NotNull(message = "请选择证件类型！")
    private Integer certtype;
    /**
     * 证件号
     */
    @ApiModelProperty(value="证件号")
	@NotNull(message = "请输入证件号！")
    private String certno;
    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
	@NotNull(message = "请选择性别！")
    private Integer gender;
    /**
     * 出生日期
     */
    @ApiModelProperty(value="出生日期")
    private String birthday;
    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * 域账号
     */
    @ApiModelProperty(value="域账号")
    private String adaccount;
    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
	@NotNull(message = "请输入手机号！")
    private String mobile;
    /**
     * 办公室电话
     */
    @ApiModelProperty(value="办公室电话")
    private String officePhone;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 班组
     */
    @ApiModelProperty(value="班组")
    private Integer empgroup;
    /**
     * 员工性质
     */
    @ApiModelProperty(value="员工性质")
    private Integer empkind;
    /**
     * 员工类别
     */
    @ApiModelProperty(value="员工类别")
    private Integer empcategory;
    /**
     * 员工属性
     */
    @ApiModelProperty(value="员工属性")
    private Integer empproperty;
    /**
     * 工种
     */
    @ApiModelProperty(value="工种")
    private Integer jobgz;
    /**
     * 工序
     */
    @ApiModelProperty(value="工序")
    private Integer wpid;
    /**
     * 机台
     */
    @ApiModelProperty(value="机台")
    private Integer machineid;
    /**
     * 更新日期
     */
    @ApiModelProperty(value="更新日期")
    private String updatedate;
    /**
     * 创建日期
     */
    @ApiModelProperty(value="创建日期")
    private String createdate;
	/**
	 * 省
	 */
	@ApiModelProperty(value="省")
	@NotNull(message = "请选择工作地点！")
	private Integer province;
	/**
	 * 市
	 */
	@ApiModelProperty(value="市")
	@NotNull(message = "请选择工作地点！")
	private Integer city;
	/**
	 * 区
	 */
	@ApiModelProperty(value="区")
	@NotNull(message = "请选择工作地点！")
	private Integer district;
	/**
	 * 地址
	 */
	@ApiModelProperty(value="地址")
	private String adress;
	/**
	 * 工作性质
	 */
	@ApiModelProperty(value="工作性质")
	private Integer jobcategory;
	/**
	 * 婚姻状况
	 */
	@ApiModelProperty(value="婚姻状况")
	private Integer marriage;
	/**
	 * 民族
	 */
	@ApiModelProperty(value="民族")
	private Integer nation;
	/**
	 * 政治面貌
	 */
	@ApiModelProperty(value="政治面貌")
	private Integer party;
	/**
	 * 加人日期
	 */
	@ApiModelProperty(value="入党日期")
	private LocalDateTime partydate;
	/**
	 * 籍贯
	 */
	@ApiModelProperty(value="籍贯")
	private String resident;
	/**
	 * 籍贯2
	 */
	@ApiModelProperty(value="籍贯2")
	private String resident2;
	/**
	 * 户口性质
	 */
	@ApiModelProperty(value="户口性质")
	private Integer residenttype;
	/**
	 * 户口所在地
	 */
	@ApiModelProperty(value="户口所在地")
	private String residentaddress;
	/**
	 * 家庭住址
	 */
	@ApiModelProperty(value="家庭住址")
	private String address;
	/**
	 * 身份证正面
	 */
	@ApiModelProperty(value="身份证正面")
	private String cert1;
	/**
	 * 身份证反面
	 */
	@ApiModelProperty(value="身份证反面")
	private String cert2;
	/**
	 * 身份证有效期
	 */
	@ApiModelProperty(value="身份证有效期")
	private LocalDateTime certindate;
	/**
	 * 健康状况
	 */
	@ApiModelProperty(value="健康状况")
	private Integer wyear;
	/**
	 * 健康状况
	 */
	@ApiModelProperty(value="健康状况")
	private Integer health;
	/**
	 * 体检单
	 */
	@ApiModelProperty(value="体检单")
	private String healthreport;
	/**
	 * QQ号
	 */
	@ApiModelProperty(value="QQ号")
	private String qq;
	/**
	 * 微信
	 */
	@ApiModelProperty(value="微信")
	private String wechart;
	/**
	 * 微博
	 */
	@ApiModelProperty(value="微博")
	private String weibo;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer age;
	/**
	 * 头像
	 */
	@ApiModelProperty(value="头像")
	private String portrait;
	/**
	 * 计算年龄段开始
	 */
	@ApiModelProperty(value="")
	@TableField(exist=false)
	private Integer age1;
	/**
	 *计算年龄段结束
	 */
	@ApiModelProperty(value="")
	@TableField(exist=false)
	private Integer age2;
	/**
	 *计算用
	 */
	@ApiModelProperty(value="")
	@TableField(exist=false)
	private Integer day;
	@TableField(exist=false)
	private Integer id;


}
