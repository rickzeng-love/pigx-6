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

/**
 * 入职登记历史
 *
 * @author gaoxiao
 * @date 2020-04-27 13:39:41
 */
@Data
@TableName("etstaff_all")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "入职登记历史")
public class EtstaffAll extends Model<EtstaffAll> {
private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    @TableId
    @ApiModelProperty(value="员工编号")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer rappid;
    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
    /**
     * 原员工编号
     */
    @ApiModelProperty(value="原员工编号")
    private Integer oldeid;
    /**
     * 原工号
     */
    @ApiModelProperty(value="原工号")
    private String oldbadge;
    /**
     * 工号
     */
    @ApiModelProperty(value="工号")
    private String badge;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
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
     * 公司ID
     */
    @ApiModelProperty(value="公司ID")
    private Integer compid;
    /**
     * 部门ID
     */
    @ApiModelProperty(value="部门ID")
    private Integer depid;
    /**
     * 岗位ID
     */
    @ApiModelProperty(value="岗位ID")
    private Integer jobid;
    /**
     * 上级领导
     */
    @ApiModelProperty(value="上级领导")
    private Integer reportto;
    /**
     * 职能上级
     */
    @ApiModelProperty(value="职能上级")
    private Integer wfreportto;
    /**
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 员工类型
     */
    @ApiModelProperty(value="员工类型")
    private Integer emptype;
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
     * 招聘方式
     */
    @ApiModelProperty(value="招聘方式")
    private Integer jointype;
    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private String joindate;
    /**
     * 参加工作日
     */
    @ApiModelProperty(value="参加工作日")
    private String workbegindate;
    /**
     * 工龄调整(月)
     */
    @ApiModelProperty(value="工龄调整(月)")
    private Double wyearAdjust;
    /**
     * 司龄调整(月)
     */
    @ApiModelProperty(value="司龄调整(月)")
    private Double cyearAdjust;
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
     * 签约单位
     */
    @ApiModelProperty(value="签约单位")
    private Integer contract;
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
     * 国家
     */
    @ApiModelProperty(value="国家")
    private Integer country;
    /**
     * 民族
     */
    @ApiModelProperty(value="民族")
    private Integer nation;
    /**
     * 证件类型
     */
    @ApiModelProperty(value="证件类型")
    private Integer certtype;
    /**
     * 证件号码
     */
    @ApiModelProperty(value="证件号码")
    private String certno;
    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
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
     * 手机
     */
    @ApiModelProperty(value="手机")
    private String mobile;
    /**
     * 办公电话
     */
    @ApiModelProperty(value="办公电话")
    private String officePhone;
    /**
     * 出生地
     */
    @ApiModelProperty(value="出生地")
    private String birthplace;
    /**
     * 政治面貌
     */
    @ApiModelProperty(value="政治面貌")
    private Integer party;
    /**
     * 加人日期
     */
    @ApiModelProperty(value="加人日期")
    private String partydate;
    /**
     * 学历
     */
    @ApiModelProperty(value="学历")
    private Integer highlevel;
    /**
     * 学位
     */
    @ApiModelProperty(value="学位")
    private Integer highdegree;
    /**
     * 职称
     */
    @ApiModelProperty(value="职称")
    private Integer hightitle;
    /**
     * 专业
     */
    @ApiModelProperty(value="专业")
    private String major;
    /**
     * 婚姻状况
     */
    @ApiModelProperty(value="婚姻状况")
    private Integer marriage;
    /**
     * 健康状况
     */
    @ApiModelProperty(value="健康状况")
    private Integer health;
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
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String tel;
    /**
     * 邮政编码
     */
    @ApiModelProperty(value="邮政编码")
    private String postcode;
    /**
     * QQ号
     */
    @ApiModelProperty(value="QQ号")
    private String qq;
    /**
     * 个人邮箱
     */
    @ApiModelProperty(value="个人邮箱")
    private String emailPers;
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
     * 血型
     */
    @ApiModelProperty(value="血型")
    private String bloodtype;
    /**
     * 星座
     */
    @ApiModelProperty(value="星座")
    private String constellation;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记日期
     */
    @ApiModelProperty(value="登记日期")
    private String regdate;
    /**
     * 确认标识 1：审核通过 2：不通过3：不审核
     */
    @ApiModelProperty(value="确认标识")
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
    private String initializedtime;
    /**
     * 审核
     */
    @ApiModelProperty(value="审核")
    private Integer submit;
    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private String submitby;
    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private String submittime;
    /**
     * 封存
     */
    @ApiModelProperty(value="封存")
    private Integer closed;
    /**
     * 处理人
     */
    @ApiModelProperty(value="处理人")
    private Integer closedby;
    /**
     * 处理时间
     */
    @ApiModelProperty(value="处理时间")
    private String closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 证件地址
     */
    @ApiModelProperty(value="证件地址")
    private String certadd;
    /**
     * 入职时间(评估)
     */
    @ApiModelProperty(value="入职时间(评估)")
    private String joindate2;
    /**
     * 在公司吃饭
     */
    @ApiModelProperty(value="在公司吃饭")
    private Integer ismeal;
    /**
     * 证件签发日期
     */
    @ApiModelProperty(value="证件签发日期")
    private String issuancedate;
    /**
     * 证件到期日
     */
    @ApiModelProperty(value="证件到期日")
    private String expirydate;
    /**
     * 内部举荐人
     */
    @ApiModelProperty(value="内部举荐人")
    private Integer refereeempid;
    /**
     * 域账号
     */
    @ApiModelProperty(value="域账号")
    private String adaccount;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer isdelidcard;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer distancecomp;
    /**
     * 班组
     */
    @ApiModelProperty(value="班组")
    private Integer empgroup;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empkind;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer empcategory;
    /**
     * 员工性质
     */
    @ApiModelProperty(value="员工性质")
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
     * 
     */
    @ApiModelProperty(value="")
    private Integer wfinstid;
    /**
     * 省
     */
    @ApiModelProperty(value="省")
    private Integer province;
    /**
     * 市
     */
    @ApiModelProperty(value="市")
    private Integer city;
    /**
     * 区
     */
    @ApiModelProperty(value="区")
    private Integer district;
    /**
     * 详细地址
     */
    @ApiModelProperty(value="详细地址")
    private String adress;
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
    private String certindate;
    /**
     * 体检单
     */
    @ApiModelProperty(value="体检单")
    private String healthreport;
    /**
     * 户口性质
     */
    @ApiModelProperty(value="户口性质")
    private Integer residenttype;
	//0：需审核 1：不需要审核
	private Integer isneedaudit;
	private Integer corpid;
	private String spno;
    }
