package com.pig4cloud.pigx.common.core.util.domain;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 完成任务
 */
@Data
public class BscInstTaskOpt implements Serializable{
//	流程实例id
	private String instid;
//	任务id
	private String taskid;
//  完成人
	private String usercode;
//	审批操作
	private String optflag;
//	审批意见
	private String comment;

//	是否关闭同节点的所有代办
	private Boolean closeOthers;

//	参数变量
	private Map<String,Object> params = Maps.newHashMap();

}
