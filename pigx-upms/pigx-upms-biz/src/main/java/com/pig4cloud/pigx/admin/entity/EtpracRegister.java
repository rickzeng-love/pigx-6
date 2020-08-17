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
 * 实习期变动
 *
 * @author gaoxiao
 * @date 2020-04-15 11:12:57
 */
@Data
@TableName("etprac_register")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "实习期变动")
public class EtpracRegister extends Model<EtpracRegister> {
private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId
    @ApiModelProperty(value="序号")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private Integer seqid;
    /**
     * 类型
     */
    @ApiModelProperty(value="类型")
    private Integer type;
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
     * 员工状态
     */
    @ApiModelProperty(value="员工状态")
    private Integer empstatus;
    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private String joindate;
    /**
     * 实习开始时间
     */
    @ApiModelProperty(value="实习开始时间")
    private String pracbegindate;
    /**
     * 实习期(月)
     */
    @ApiModelProperty(value="实习期(月)")
    private Integer practerm;
    /**
     * 实习结束时间
     */
    @ApiModelProperty(value="实习结束时间")
    private String pracenddate;
    /**
     * 新实习开始时间
     */
    @ApiModelProperty(value="新实习开始时间")
    private String newPracbegindate;
    /**
     * 新实习期(月)
     */
    @ApiModelProperty(value="新实习期(月)")
    private Integer newPracterm;
    /**
     * 新实习结束时间
     */
    @ApiModelProperty(value="新实习结束时间")
    private String newPracenddate;
    /**
     * 生效时间
     */
    @ApiModelProperty(value="生效时间")
    private String effectdate;
    /**
     * 评估结果
     */
    @ApiModelProperty(value="评估结果")
    private String result;
    /**
     * 所属组织
     */
    @ApiModelProperty(value="所属组织")
    private String corpcode;
    /**
     * 
     */
    @ApiModelProperty(value="")
    private String errormsg;
    /**
     * 登记人
     */
    @ApiModelProperty(value="登记人")
    private Integer regby;
    /**
     * 登记时间
     */
    @ApiModelProperty(value="登记时间")
    private String regdate;
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
    private Integer submitby;
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
     * 封存人
     */
    @ApiModelProperty(value="封存人")
    private Integer closedby;
    /**
     * 封存时间
     */
    @ApiModelProperty(value="封存时间")
    private String closedtime;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 是否见习
     */
    @ApiModelProperty(value="是否见习")
    private Integer isproc;
    /**
     * 见习开始时间
     */
    @ApiModelProperty(value="见习开始时间")
    private String procbegindate;
    /**
     * 见习期(月)
     */
    @ApiModelProperty(value="见习期(月)")
    private Integer procterm;
    /**
     * 见习结束时间
     */
    @ApiModelProperty(value="见习结束时间")
    private String procenddate;
    /**
     * 是否试用
     */
    @ApiModelProperty(value="是否试用")
    private Integer isprob;
    /**
     * 试用开始时间
     */
    @ApiModelProperty(value="试用开始时间")
    private String probbegindate;
    /**
     * 试用期(月)
     */
    @ApiModelProperty(value="试用期(月)")
    private Integer probterm;
    /**
     * 试用结束时间
     */
    @ApiModelProperty(value="试用结束时间")
    private String probenddate;
    private Integer corpid;
    private String spno;
    private Integer isneedaudit;
    }
