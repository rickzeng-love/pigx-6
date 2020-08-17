package com.pig4cloud.pigx.common.core.util.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class TaskEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	String deleteReason;

	Date startTime;

	Date endTime;

	Long durationInMillis;

	Long workTimeInMillis;

	Date claimTime;

	String id;

	String name;

	String description;

	Integer priority;

	String owner;

	String assignee;

	String processInstanceId;

	String executionId;

	String processDefinitionId;

	Date ceateTime;

	String taskDefinitionKey;

	Date dueDate;

	String category;

	String parentTaskId;

	String tenantId;

	String formKey;

	Map<String, Object> taskLocalVariables;

	Map<String, Object> processVariables;

	String optflag;//通过，驳回

	String comment;//审批意见


}
