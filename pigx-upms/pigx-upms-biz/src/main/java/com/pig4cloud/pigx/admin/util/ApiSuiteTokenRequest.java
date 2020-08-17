package com.pig4cloud.pigx.admin.util;

import lombok.Data;

@Data
public class ApiSuiteTokenRequest {
	private String  suite_id;
	private String  suite_secret;
	private String  suite_ticket;
}
