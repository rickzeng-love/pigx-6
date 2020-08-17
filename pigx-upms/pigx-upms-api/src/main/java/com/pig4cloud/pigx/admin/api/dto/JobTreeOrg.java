package com.pig4cloud.pigx.admin.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gaoxiao
 * @date 2020年04月1日23:33:45
 */
@Data
@ApiModel(value = "树形节点")
public class JobTreeOrg {
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
	protected String jobcode;
	@ApiModelProperty(value = "所属部门")
	protected String depname;
	@ApiModelProperty(value = "在职")
	protected String zaizhi;
	@ApiModelProperty(value = "全日制")
	protected String qrz;
	@ApiModelProperty(value = "非全日制")
	protected String fqrz;
	@ApiModelProperty(value = "人员编制")
	protected String jobnum;
	@ApiModelProperty(value = "超编/缺编")
	protected String qbcb;
	@ApiModelProperty(value = "负责人")
	protected Integer director;
	@ApiModelProperty(value = "分管领导")
	protected Integer director2;
	@ApiModelProperty(value = "虚拟部门")
	protected String isou;
	@ApiModelProperty(value = "是否子节点")
	protected boolean hasChildren;

	/**
	 * 部门简称
	 */
	@ApiModelProperty(value="部门简称")
	private String jobabbr;
	/**
	 * 所属公司
	 */
	@ApiModelProperty(value="所属公司")
	private Integer compid;
	/**
	 * 岗位编码
	 */
	@ApiModelProperty(value="岗位编码")
	private Integer jobid;
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
	@ApiModelProperty(value="上级岗位名称")
	private String adminname;
	/**
	 * 职等1
	 */
	@ApiModelProperty(value="职等1")
	private Integer posgrade1;
	/**
	 * 职等2
	 */
	@ApiModelProperty(value="职等2")
	private Integer posgrade2;

	private String jobtypename;
	private String isouname;
	private String posgrade;
	private String empgrade;
	private String jobdescription;
	private String jobrequirements;



	/**
	 * 所属部门
	 */
	@ApiModelProperty(value="所属部门")
	private Integer depid;

	/**
	 * 岗位级别
	 */
	@ApiModelProperty(value="岗位级别")
	private Integer jobgrade;
	/**
	 * 岗位类别
	 */
	@ApiModelProperty(value="岗位类别")
	private Integer jobtype;
	/**
	 * 岗位序列
	 */
	@ApiModelProperty(value="岗位序列")
	private Integer jobproperty;

	@ApiModelProperty(value="岗位版本")
	private String jobversion;
	/**
	 * 是否核心岗位
	 */
	@ApiModelProperty(value="是否核心岗位")
	private Integer iscore;
	/**
	 * 是否计件
	 */
	@ApiModelProperty(value="是否计件")
	private Integer ispiece;
	/**
	 * 高温津贴
	 */
	@ApiModelProperty(value="高温津贴")
	private Double hotfee;

	/**
	 * 是否失效
	 */
	@ApiModelProperty(value="是否失效")
	private Integer isdisabled;
	/**
	 * 失效日期
	 */
	@ApiModelProperty(value="失效日期")
	private String disableddate;
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;
	/**
	 * 所属组织代码
	 */
	@ApiModelProperty(value="所属组织代码")
	private String corpcode;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer xtype;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Double restjobnum;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private Integer isdriver;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间")
	private String updatedate;
	/**
	 * 对应职级
	 */
	@ApiModelProperty(value="对应职级")
	private Integer empgradeid;
	/**
	 * 对应职等
	 */
	@ApiModelProperty(value="对应职等")
	private Integer posgradeid;

	/**
	 * 对应职级
	 */
	@ApiModelProperty(value="对应职级")
	private Integer empgradeid1;
	/**
	 * 对应职等
	 */
	@ApiModelProperty(value="对应职等")
	private Integer posgradeid1;
	/**
	 * 对应职级
	 */
	@ApiModelProperty(value="对应职级")
	private Integer empgradeid2;
	/**
	 * 对应职等
	 */
	@ApiModelProperty(value="对应职等")
	private Integer posgradeid2;
	private Integer corpid;









}
