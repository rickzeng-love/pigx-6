package com.pig4cloud.pigx.common.core.util.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class InstEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	String id;

	String processInstanceId;

	String processDefinitionId;

	String processDefinitionName;

	String processDefinitionKey;

	Integer processDefinitionVersion;

	String deploymentId;

	String businessKey;

	Boolean isSuspended;

	Map<String, Object> processVariables;

	String tenantId;

	String name;

	String description;

	String localizedName;

	String localizedDescription;

	Date startTime;

	Date endTime;

	String startUserId;

	String deleteReason;

	String corpcode;



}
