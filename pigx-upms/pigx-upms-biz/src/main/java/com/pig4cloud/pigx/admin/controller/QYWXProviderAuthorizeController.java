package com.pig4cloud.pigx.admin.controller;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationMapper;
import com.pig4cloud.pigx.admin.service.SysPublicParamService;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationService;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.JsonResult;
import com.qq.weixin.mp.aes.AesException;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/providerAuthorize" )
@Api(value = "providerAuthorize", tags = "从服务商网站发起授权，安装授权应用，会根据上一步中的预授权码，为授权企业分配永久授权码")
public class QYWXProviderAuthorizeController extends ServiceImpl<SysQywxApplicationMapper, SysQywxApplication> {

	public static final String INSTALL_URL = "https://open.work.weixin.qq.com/3rdapp/install?suite_id=";
	private final SysQywxApplicationService sysQywxApplicationService;
	private final SysPublicParamService sysPublicParamService;
	private final CacheManager cacheManager;
	private final SysQywxApplicationMapper sysQywxApplicationMapper;
	@Resource
	private RestTemplate restTemplate;
	/*
	一键授权功能,主动引入用户进入授权页后，通过用户点击调用此方法
	 */
	@PostMapping("/goAuthor" )
    public JsonResult goAuthor(HttpServletRequest request, String suiteId) throws IOException, AesException, DocumentException {
		if (StringUtil.isEmpty(suiteId)) {
			return new JsonResult(1);
        }
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String redirectUri = baseUrl + "/admin/providerAuthorize/authorCallback";
        String redirectUriEncode = URLEncoder.encode(redirectUri, "UTF-8");
        // Map<String,String> stateMap = new HashMap<String, String>();
        // stateMap.put("suiteId", suiteId);
        // 查询第三方应用，获取预授权码
		SysQywxApplication application = sysQywxApplicationMapper.queryBySuiteId(suiteId);
        if (application == null || StringUtil.isEmpty(application.getPreAuthCode())) {
        	return new JsonResult(1, "suiteId:" + suiteId + "对应的第三方应用尚未初始化，请等待10分钟或联系服务商", null);
        }        // 获取预授权码，有效期10分钟
        String preAuthCode = application.getPreAuthCode();
        String url = INSTALL_URL + suiteId + "&pre_auth_code=" + preAuthCode + "&redirect_uri=" + redirectUriEncode + "&state=" + suiteId;
        return new JsonResult(0, url);
    }
	/*
		引导授权回调 根据临时授权码（10分钟有效），换取永久授权码
	 */
	  @PostMapping("/authorCallback")
	  public void authorCallback(HttpServletRequest request, HttpServletResponse response) throws IOException, AesException, DocumentException {

		  String authCode = request.getParameter("auth_code");
		  log.info("获取永久授权方法内临时授权码:auth_code:"+authCode);
		  String expires_in = request.getParameter("expires_in");
		  long lexpires_in = Long.parseLong(expires_in);
		  String access_token_expires_time = DateUtils.getDateTimeaddMinus(lexpires_in);
		  log.info("获取永久授权方法内临时授权码过期时间:access_token_expires_time:"+access_token_expires_time);
		  String state = request.getParameter("state");        // //解析state
//		  String suiteId = request.getParameter("suite_id");
		  SysQywxApplication application = sysQywxApplicationMapper.queryBySuiteId(state);
		  application.setId(application.getId());
		  application.setAuthcode(authCode);
		  // JSONObject js = JSONObject.parseObject(state);
		  // String suiteId = js.getString("suiteId");
		 /// String suiteId = state;        // 换取永久授权码
		  sysQywxApplicationService.getPermanentCodeAndAccessToken(application);
	  }



}
