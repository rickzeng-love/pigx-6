package com.pig4cloud.pigx.admin.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gaoxiao
 * @date 2020年04月1日23:33:45
 */
@Data
@ApiModel(value = "树形节点")
public class DeptTreeOrg {
	@ApiModelProperty(value = "当前节点id")
	protected String id;
	@ApiModelProperty(value = "父节点id")
	protected String pid;
	@ApiModelProperty(value = "OrgChart-name")
	protected String name;
	@ApiModelProperty(value = "OrgChart-title")
	protected String title;
	@ApiModelProperty(value = "排序")
	protected String xorder;
	@ApiModelProperty(value = "类型(公司|部门)")
	protected Integer stype;
	@ApiModelProperty(value = "isExpand")
	protected boolean isExpand;
	@ApiModelProperty(value = "人数")
	protected String people;
	@ApiModelProperty(value = "部门编码")
	protected String depcode;
	@ApiModelProperty(value = "在职")
	protected String zaizhi;
	@ApiModelProperty(value = "全日制")
	protected String qrz;
	@ApiModelProperty(value = "非全日制")
	protected String fqrz;
	@ApiModelProperty(value = "人员编制")
	protected Integer depnum;
	@ApiModelProperty(value = "超编/缺编")
	protected String qbcb;
	@ApiModelProperty(value = "负责人")
	protected Integer director;
	@ApiModelProperty(value = "分管领导")
	protected Integer director2;
	@ApiModelProperty(value = "虚拟部门")
	protected Integer isou;
	@ApiModelProperty(value = "是否子节点")
	protected boolean hasChildren;

	/**
	 * 部门简称
	 */
	@ApiModelProperty(value="部门简称")
	private String depabbr;
	/**
	 * 所属公司
	 */
	@ApiModelProperty(value="所属公司")
	private Integer compid;
	/**
	 * 上级部门
	 */
	@ApiModelProperty(value="上级部门")
	private Integer adminid;
	/**
	 * 职能上级
	 */
	@ApiModelProperty(value="职能上级")
	private Integer functionid;
	/**
	 * 部门级别
	 */
	@ApiModelProperty(value="部门级别")
	private Integer depgrade;
	/**
	 * 部门类型
	 */
	@ApiModelProperty(value="部门类型")
	private Integer deptype;
	/**
	 * 部门类别
	 */
	@ApiModelProperty(value="部门类别")
	private Integer depproperty;


	/**
	 * 部门人事专员
	 */
	@ApiModelProperty(value="部门人事专员")
	private Integer depemp;

	/**
	 * 成立日期
	 */
	@ApiModelProperty(value="成立日期")
	private String effectdate;
	/**
	 * 部门职能
	 */
	@ApiModelProperty(value="部门职能")
	private String orgfunction;
	/**
	 * 部门描述
	 */
	@ApiModelProperty(value="部门描述")
	private String decription;
	/**
	 * 上级部门名称
	 */
	@ApiModelProperty(value="上级部门名称")
	private String adminname;
	private String deptypename;
	private String directorname;
	private String director2name;
	private String isouname;
	private Integer depid;
	private String remark;




}
