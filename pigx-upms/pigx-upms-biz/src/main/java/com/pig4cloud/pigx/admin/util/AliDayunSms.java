package com.pig4cloud.pigx.admin.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Random;

/**
 * @Title AliDayunSms
 * @Description 短信接口开发
 * @author gaoxiao
 * @date: 2020/04/13下午1:28
 */
@AllArgsConstructor
public class AliDayunSms {



	public  void sendMessage(String accessKeyId,String accessSecret,String phoneNumber, String code) throws ClientException { //手机号、验证码

		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", phoneNumber);
		request.putQueryParameter("SignName", "安特君合");//输入你的短信签名名称
		request.putQueryParameter("TemplateCode", "SMS_181865719");//输入你的短信模板ID
		request.putQueryParameter("TemplateParam", " { \"code\":"+"\""+code+"\"}");

		CommonResponse response = client.getCommonResponse(request);


	}

	/**
	 * 生成随机的6位数，短信验证码
	 * @return
	 */
	private static String getMsgCode() {
		int n = 6;
		StringBuilder code = new StringBuilder();
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			code.append(Integer.valueOf(ran.nextInt(10)).toString());
		}
		return code.toString();
	}
}
