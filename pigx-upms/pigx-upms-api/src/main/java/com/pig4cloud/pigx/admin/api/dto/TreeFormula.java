package com.pig4cloud.pigx.admin.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaoxiao
 * @date 2020年04月1日23:33:45
 */
@Data
@ApiModel(value = "树形节点")
public class TreeFormula {
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
	protected String stype;
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
	protected String depnum;
	@ApiModelProperty(value = "超编/缺编")
	protected String qbcb;
	@ApiModelProperty(value = "负责人")
	protected String director;
	@ApiModelProperty(value = "分管领导")
	protected String director2;
	@ApiModelProperty(value = "虚拟部门")
	protected String isou;
	@ApiModelProperty(value = "头像")
	protected String imgs;
	@ApiModelProperty(value = "人数")
	protected Integer renshu;
	@ApiModelProperty(value = "是否子节点")
	protected boolean hasChildren;
	@ApiModelProperty(value = "子节点列表")
	protected List<TreeFormula> children = new ArrayList<TreeFormula>();
	public void add(TreeFormula node) {
		children.add(node);
	}
	@ApiModelProperty(value = "是否选中")
	protected Integer ischecked;
	@ApiModelProperty(value = "表名称")
	protected String tablename;

}
