package com.pig4cloud.pigx.admin.util;

/**
 * 微信枚举
 * @author wangli
 *
 *
 */
public class WeixinEnums {

	/**
	 * 各种url枚举
	 *
	 * @author user
	 *
	 */
	public enum UrlStyle {


		获取模板("https://qyapi.weixin.qq.com/cgi-bin/oa/gettemplatedetail"),
		获取access_token("https://qyapi.weixin.qq.com/cgi-bin/gettoken"),
		提交审批申请("https://qyapi.weixin.qq.com/cgi-bin/oa/applyevent"),
		上传临时素材("https://qyapi.weixin.qq.com/cgi-bin/media/upload"),
		获取临时素材("https://qyapi.weixin.qq.com/cgi-bin/media/get"),
		复制更新模板到企业("https://qyapi.weixin.qq.com/cgi-bin/oa/approval/copytemplate");


		private String value;

		UrlStyle(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}


	}





}
