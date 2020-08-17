package com.qq.weixin.mp.aes;

import com.pig4cloud.pigx.admin.service.SysPublicParamService;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnterpriseConst {

	private static SysPublicParamService sysPublicParamService;
	private static CacheManager cacheManager;
	public static  String  STOKEN ;
	public static  String  SENCODINGAESKEY ;
	public static  String  SCORPID;
	public static  String  SUITEID;
	public static  String  SUITESECRET;
	public static  String  SUITE_ACCESS_TOKEN;
	public static  String  STOKEN_B ;
	public static  String  SENCODINGAESKEY_B ;
	public static  String  SUITEID_B;
	public static  String  SUITESECRET_B;



}
