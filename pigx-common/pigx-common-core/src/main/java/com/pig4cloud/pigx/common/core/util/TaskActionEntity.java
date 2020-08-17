package com.pig4cloud.pigx.common.core.util;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流相关参数实体
 */
@Data
public class TaskActionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	//流程定义id
	private String dingyiid;

	//定义key
	private String dingyikey;

	//业务id
	private String ywid;

	//流程实例id
	private String instid;

	//实例名
	private String shiliname;

	//全局变量
	private JSONObject quanjubianliang;

	//任务taskid
	private String taskid;

	// 同意/驳回
	private String comment;

	// 通过/不通过
	private String optflag;


}
