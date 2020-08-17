package com.pig4cloud.pigx.admin.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


public class EnterpriseAPI {
	//private  final  RestTemplate restTemplate;

	public String getToken(String cbecsTokenUrl) {
	/*	RestTemplate restTemplate = new RestTemplate();
		//获取token
		ResponseEntity<String> result = restTemplate.getForEntity(cbecsTokenUrl, String.class);
		if (result != null) {
			JSONObject map = JSONObject.parseObject(result.getBody());
			if ("0".equals(map.get("code"))) {
			//	throw new DeptException("获取token失败");
			}else {
				String token = (String) map.get("token");
				return token;
			}
		}*/
	return "";
	}
	public  String getSuiteAccessToken(ApiSuiteTokenRequest apiSuiteToken) {
		String suite_token_url = QYWXURL.SUITE_TOKEN_URL;
		RestTemplate restTemplate = new RestTemplate();
		HashMap<String, String> map = new HashMap<>();
		map.put("suite_id",apiSuiteToken.getSuite_id());
		map.put("suite_secret",apiSuiteToken.getSuite_secret());
		map.put("suite_ticket",apiSuiteToken.getSuite_ticket());
		//获取token
		ResponseEntity<String> result = restTemplate.postForEntity(suite_token_url,map,String.class);
		if (result != null) {
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String suite_access_token = jsonmap.get("suite_access_token").toString();
			return suite_access_token;

		}else {
			//throw new DeptException("获取token异常");
		}
		return "";
	}

	public  static String getPreAuthCode(String suiteId, String suiteAccessToken) {
		String pre_auth_code_url = QYWXURL.PRE_AUTH_CODE_URL;
		RestTemplate restTemplate = new RestTemplate();
		HashMap<String, String> map = new HashMap<>();
		map.put("suite_access_token",suiteAccessToken);
		//获取token
		ResponseEntity<String> result = restTemplate.getForEntity(pre_auth_code_url,String.class,map);
		if (result != null) {
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			String pre_auth_code = jsonmap.get("pre_auth_code").toString();
			return pre_auth_code;

		}else {
			//throw new DeptException("获取token异常");
		}
		return "";
	}

}

