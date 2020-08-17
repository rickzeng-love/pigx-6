package com.pig4cloud.pigx.admin.util;

public class QYWXURL {
	public static String SUITE_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
	public static String PRE_AUTH_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code";
	public static String PERMANENT_CODE = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code";
	public static String SET_SESSION_INFO = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info";
	public static String AUTH_CODE_URL = "https://open.work.weixin.qq.com/3rdapp/install";
	//获取授权方的access_token
	public static String GET_CORP_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token";
	//获取授权方通讯录应用的access_token
	public static String GET_ADDRESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	//user相关链接
	public static String SELECT_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
	public static String CREAT_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
	public static String UPDATE_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update";
	public static String DEL_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete";
	//部门相关连接
	public static String SELECT_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create";
	public static String CREAT_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create";
	public static String UPDATE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update";
	public static String DEL_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete";
	public static String LIST_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
	//发送消息
	public static String SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
	//更新已发送消息
	public static String UPDATE_SEND_MSG_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/update_taskcard";
	//获取访问用户身份
	public static String GET_USERINFO3RD_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/getuserinfo3rd";
	//复制/更新模板到企业
	public static String COPY_TEMPLATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/oa/approval/copytemplate";
	//获取审批模板详情
	public static String GET_TEMPLATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/oa/gettemplatedetail";
	//提交审批申请
	public static String GET_APPLYEVENT_URL = "https://qyapi.weixin.qq.com/cgi-bin/oa/applyevent";
	//查询第三方应用审批申请当前状态
	public static String GET_APPROVALINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/corp/getopenapprovaldata";
	//上传临时素材
	public static String UPLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/upload";
	//获取临时素材
	public static String GET_DOWN_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get";
	//获取审批申请详情
	public static String GET_APPROVALDETAIL_URL = "https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovaldetail";
}
