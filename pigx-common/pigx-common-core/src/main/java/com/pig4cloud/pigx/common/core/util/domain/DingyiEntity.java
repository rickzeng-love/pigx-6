package com.pig4cloud.pigx.common.core.util.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DingyiEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	String id;

	String category;

	String name;

	String key;

	String description;

	Integer version;

	String resourceName;

	String deploymentId;

	String diagramResourceName;

	Boolean hasStartFormKey;

	Boolean hasGraphicalNotation;

	Boolean isSuspended;

	String tenantId;

	Date deploymentTime;




}
