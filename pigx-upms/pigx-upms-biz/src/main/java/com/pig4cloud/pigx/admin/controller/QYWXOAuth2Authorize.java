package com.pig4cloud.pigx.admin.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.admin.entity.SysQywxApplication;
import com.pig4cloud.pigx.admin.entity.SysQywxApplicationAuthorizer;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationMapper;
import com.pig4cloud.pigx.admin.mapper.SysUserMapper;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationAuthorizerService;
import com.pig4cloud.pigx.admin.service.SysQywxApplicationService;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.admin.service.impl.SysQywxApplicationServiceImpl;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.EnterpriseAPI;
import com.pig4cloud.pigx.admin.util.QYWXURL;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.constant.enums.LoginTypeEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.EnterpriseConst;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/*
 * 用户在不告知第三方自己的帐号密码情况下，通过授权方式，让第三方服务可以获取自己的资源信息 网页授权登陆 第三方应用oauth2链接
 *
 * @author XP
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/oauth2")
@Api(value = "oauth2", tags = "用户在不告知第三方自己的帐号密码情况下，通过授权方式，让第三方服务可以获取自己的资源信息 网页授权登陆 第三方应用oauth2链接")
public class QYWXOAuth2Authorize {

	// oauth2地址
	public static final String OAUTH2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
	private SysQywxApplicationService sysQywxApplicationService;
	private SysQywxApplicationAuthorizerService sysQywxApplicationAuthorizerService;
	private final SysQywxApplicationMapper sysQywxApplicationMapper;
	private SysUserService sysuserservice;
	@Resource
	private RestTemplate restTemplate;
	private final RedisTemplate redisTemplate;
	/**
	 * 企业微信网页授权API
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getRedirectUrl")
	@ResponseBody
	public R getRedirectUrl(String redirectRoot,HttpServletRequest request, HttpServletResponse response) {
		/*if (StringUtil.isEmpty(state)) {
			R.failed(null);
		}*/
	/*	if (StringUtil.isEmpty(scope)) {
			R.failed(null);
		}*/
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID;

		// 第一步：引导用户进入授权页面,根据应用授权作用域，获取code
		//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		//String redirectRoot = "http://hrcloud.antechunion.com";
		//String backUrl = basePath + "/admin/oauth2/redirect/" + suitedid;
		String backUrl =  "http://hrcloud.antechunion.com/static/Authorize3rdCallBack.html";
		///http://hrcloud.antechunion.com/static/index.html
		log.info("------------------------回调地址:----" + backUrl);
		// 微信授权地址
		String redirectUrl = oAuth2Url(suitedid, backUrl, "snsapi_userinfo", suitedid);
		log.info("------------------------授权地址:----" + redirectUrl);

		return R.ok(redirectUrl);
	}

	/**
	 * 构造带员工身份信息的URL
	 *
	 * @param suiteId      第三方应用id（即ww或wx开头的suite_id）
	 * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param state        重定向后会带上state參数，企业能够填写a-zA-Z0-9的參数值
	 * @return
	 */
	private String oAuth2Url(String suiteId, String redirect_uri, String scope, String state) {
		try {
			redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String oauth2Url = OAUTH2_URL + suiteId + "&redirect_uri=" + redirect_uri + "&response_type=code" + "&scope=" + scope + "&state=" + state
				+ "#wechat_redirect";
		//System.out.println("oauth2Url=" + oauth2Url);
		return oauth2Url;
	}

	/**
	 * 微信回调地址
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redirectDetail")
	@ApiOperation(value = "微信回调地址", httpMethod = "GET", hidden = true, notes = "微信回调地址")
	public R redirectDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, AesException, DocumentException {
		String code = request.getParameter("code");
		log.info("------------------返回值code:" + code);
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID;
		log.info("------------------url中suitedid:" + suitedid);
		// 用户授权
		SysQywxApplication application = sysQywxApplicationMapper.queryBySuiteId(suitedid);
		if (application == null) {
			//throw new BusinessException("suiteId:" + suiteId + "对应的第三方应用尚未初始化，请等待10分钟或联系服务商管理员");
		}

		//获取suiteAccessToken
		String suiteAccessToken = application.getSuiteAccessToken();
		log.info("------------------------------------表中查出suiteAccessToken:" + suiteAccessToken);
		String suite_access_token_expires_time = application.getTokenExpiresTime();
		log.info("------------------------------------表中查出suite_access_token_expires_time过期时间:" + suite_access_token_expires_time);
		String nowtime = DateUtils.getNow("yyyy-MM-dd HH:mm:ss");
		//查看当前用户企业的access_token是否过期(true:未过期 false：已过期)
		boolean falg = DateUtils.compareDate(suite_access_token_expires_time, nowtime);
		//已过期，重新获取
		if (!falg) {
			//没有过期，直接返回
			suiteAccessToken = sysQywxApplicationService.getSuiteAccessToken(application);
		}

		log.info("------------------通过接口获取到的suiteAccessToken:" + suiteAccessToken);

		// 授权
		String userid = sysQywxApplicationService.getuserinfo3red(suiteAccessToken, code);
		ResponseEntity<String> result = null;
		Integer Iuserid = null;
		try{
			Iuserid = Integer.parseInt(userid);
		}catch (Exception e){
			return R.failed("员工账号转化错误，请先绑定员工账号！");
		}


		SysUser user = sysuserservice.getById(Integer.parseInt(userid));
		String phone = user.getPhone();
		if(StringUtils.isEmpty(phone)){
			return R.failed("请在系统中维护员工手机号！");
		}
		String verycode = sendSmsCode(phone);
		user.setCode(verycode);
		return R.ok(user);
		/*if (user != null && !StringUtil.isEmpty(user.getUserId().toString())) {
			String suite_token_url = "http://39.99.202.116:9999/auth/mobile/token/sms";
			String phone = user.getPhone();
			if(StringUtils.isEmpty(phone)){
				return R.failed("请在系统中维护员工手机号！");
			}
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("TENANT-ID", "1");
			requestHeaders.add("Authorization", "Basic cGlnOnBpZw==");
			String verycode = sendSmsCode(phone);
			//body
			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("mobile", phone);
			requestBody.add("code", verycode);
			requestBody.add("grant_type", "mobile");
			//HttpEntity
			HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, requestHeaders);


			//获取返回信息
			result = restTemplate.postForEntity(suite_token_url  , requestEntity, String.class);
			log.info("result:" + result);
			log.info("result.getBody:" + result.getBody());
			JSONObject jsonmap = JSONObject.parseObject(result.getBody());
			//response.sendRedirect("http://hrcloud.antechunion.com/static/index.html");
			return R.ok(result);
		}else{
			return R.ok(result);
		}*/

		//response.sendRedirect("http://hrcloud.antechunion.com/static/index.html");
		/*ModelAndView mv = new ModelAndView();
		try {
			String code = request.getParameter("code");
			// state为跳转路径，微信不识别&符号，顾前端如有参数，请使用@代替，在此处做转换
			// 页面路径必须在jsp目录下，后缀名可自定义
*//*			String state = request.getParameter("state");
			log.info("------------------------state:----" + state);
			state = state.replaceAll("@", "&");
			log.info("------------------------state:----" + state);*//*
			// 用户授权
			SysQywxApplication application = sysQywxApplicationMapper.queryBySuiteId(suitedid);
			if (application == null) {
				//throw new BusinessException("suiteId:" + suiteId + "对应的第三方应用尚未初始化，请等待10分钟或联系服务商管理员");
			}
			String suiteAccessToken = application.getSuiteAccessToken();
			log.info("参数==================================");
			log.info("appid:" + suitedid);
			log.info("code:" + code);
			log.info("suiteAccessToken:" + suiteAccessToken);

			// 授权
			ApiUserInfoResponse userInfo = EnterpriseAPI.getuserinfo3rd(suiteAccessToken, code);
			if (userInfo != null && StringUtil.isEmpty(userInfo.getUserId()) && StringUtil.isEmpty(userInfo.getUserTicket())) {
				mv.addObject("code", "5001");
				mv.addObject("msg", "成员没有加入企业微信~");
				mv.setViewName("qywx/error.jsp"); // 跳转等待页面，然后再跳回之前页面
				return mv;
			}
			// 授权方的企业id
			String corpId = userInfo.getCorpId();
			ApiUserDetailResponse userDetail3rd = EnterpriseAPI.getUserDetail3rd(suiteAccessToken, userInfo.getUserTicket());                // 验证用户是否咨询师身份
			boolean isConsole = false;
			String accessToken = EnterpriseAPI.getCorpAccessToken(suiteAccessToken, suiteId, corpId);
			ApiUserDetailResponse userDetail = EnterpriseAPI.getUserDepartment(accessToken, userInfo.getUserId());
			List<Integer> departmentList = userDetail.getDepartmentList();
			List<String> departmentNameList = userDetail.getDepartmentNameList();
			if (departmentList.contains(EnterpriseConst.SUITE_ID_1_DEPARTMENT_CONSOLE)) {

				isConsole = true;
			}
			mv.addObject("error", false);
			mv.addObject("isConsole", isConsole);
			mv.addObject("userId", userInfo.getUserId());
			mv.addObject("corpId", corpId);
			mv.addObject("departmentName", departmentNameList);
			mv.addObject("name", userDetail3rd.getName());
			mv.addObject("avatar", userDetail3rd.getAvatar());
			mv.addObject("pageView", state);
			} else if (EnterpriseConst.SUITE_ID_2.equals(suiteId)) {                // 授权
				ApiUserInfoResponse userInfo = EnterpriseAPI.getuserinfo3rd(suiteAccessToken, code);                if (userInfo != null && StringUtil.isEmpty(userInfo.getUserId()) && StringUtil.isEmpty(userInfo.getUserTicket())) {
				mv.addObject("code", "5001");
				mv.addObject("msg", "成员没有加入企业微信~");
				mv.setViewName("qywx/error.jsp"); // 跳转等待页面，然后再跳回之前页面
				return mv;
			}
			String corpId = userInfo.getCorpId(); // 授权方的企业id
			ApiUserDetailResponse userDetail3rd = EnterpriseAPI.getUserDetail3rd(suiteAccessToken, userInfo.getUserTicket());                // 验证用户是否咨询师身份
			boolean isConsole = false;
			String accessToken = EnterpriseAPI.getCorpAccessToken(suiteAccessToken, suiteId, corpId);
			ApiUserDetailResponse userDetail = EnterpriseAPI.getUserDepartment(accessToken, userInfo.getUserId());
			List<Integer> departmentList = userDetail.getDepartmentList();
			List<String> departmentNameList = userDetail.getDepartmentNameList();                if (departmentList.contains(EnterpriseConst.SUITE_ID_2_DEPARTMENT_CONSOLE)) {
				isConsole = true;
			}
			log.info("返回用户======================" + userDetail);
			log.info("返回部门======================" + departmentNameList);
			mv.addObject("error", false);
			mv.addObject("isConsole", isConsole);
			mv.addObject("userId", userInfo.getUserId());
			mv.addObject("corpId", corpId);
			mv.addObject("departmentName", departmentNameList);
			mv.addObject("name", userDetail3rd.getName());
			mv.addObject("avatar", userDetail3rd.getAvatar());
			mv.addObject("pageView", state);
			} else if (EnterpriseConst.SUITE_ID_3.equals(suiteId)) {                // 授权
				ApiUserInfoResponse userInfo = EnterpriseAPI.getuserinfo3rd(suiteAccessToken, code);                if (userInfo != null && StringUtil.isEmpty(userInfo.getUserId()) && StringUtil.isEmpty(userInfo.getUserTicket())) {
				mv.addObject("code", "5001");
				mv.addObject("msg", "成员没有加入企业微信~");
				mv.setViewName("qywx/error.jsp"); // 未加入企业微信
				return mv;
			}
				String corpId = userInfo.getCorpId(); // 授权方的企业id
				ApiUserDetailResponse userDetail3rd = EnterpriseAPI.getUserDetail3rd(suiteAccessToken, userInfo.getUserTicket());                // 验证用户是否咨询师身份
				boolean isConsole = false;
				String accessToken = EnterpriseAPI.getCorpAccessToken(suiteAccessToken, suiteId, corpId);
				ApiUserDetailResponse userDetail = EnterpriseAPI.getUserDepartment(accessToken, userInfo.getUserId());
				List<Integer> departmentList = userDetail.getDepartmentList();
				List<String> departmentNameList = userDetail.getDepartmentNameList();                if (departmentList.contains(EnterpriseConst.SUITE_ID_3_DEPARTMENT_CONSOLE)) {
					isConsole = true;
				}
				log.info("返回用户======================" + userDetail);
				log.info("返回部门======================" + departmentNameList);
				mv.addObject("error", false);
				mv.addObject("isConsole", isConsole);
				mv.addObject("userId", userInfo.getUserId());
				mv.addObject("corpId", corpId);
				mv.addObject("departmentName", departmentNameList);
				mv.addObject("name", userDetail3rd.getName());
				mv.addObject("avatar", userDetail3rd.getAvatar());
				mv.addObject("pageView", state);
			} else {
				mv.addObject("error", true);
			}
			mv.setViewName("/static/QYWXAuthorizeCallBack.html"); // 跳转等待页面，然后再跳回之前页面
			return mv;
		} catch (BusinessException e) {
			mv.addObject("code", "5000");
			mv.addObject("msg", "出错了，点击返回首页");
			mv.setViewName("qywx/error.jsp"); // 跳转等待页面，然后再跳回之前页面
			return mv;
		}*/
	}
	/**
	 * 企业微信后台回调地址
	 *
	 * @param //request
	 * @return
	 */
		  /*  @RequestMapping(value = "/admin/redirect")    public ModelAndView adminRedirectDetail(String auth_code, HttpServletRequest request, HttpServletResponse response) {
		        response.setHeader("Access-Control-Allow-Origin", "*");
		        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		        if (StringUtil.isEmpty(auth_code)) {
		        	//throw new BusinessException(Message.M4003);
		        }
		        ModelAndView mv = new ModelAndView();
		        log.info("参数==================================");
		        log.info("code:" + auth_code);
		        try {
		            String providerAccessToken = EnterpriseAPI.getProviderToken(EnterpriseConst.SCORPID, EnterpriseConst.PROVIDERSECRET);
		            ApiUserDetailResponse userDetail = EnterpriseAPI.getLoginInfo(providerAccessToken, auth_code);
		            String url = "http://wxadmin.feifanxinli.com/admin/wechat_user/login?" + "wechatUserId=" + userDetail.getUserId() + "&userName="
		                    + userDetail.getName() + "&headlImg=" + userDetail.getAvatar();
		            mv.addObject("error", false);
		            mv.addObject("url", url);
		            mv.setViewName("qywx/admin/wuxigongdian.jsp"); // 跳转等待页面，然后再跳回之前页面
		            return mv;
		        } catch (Exception e) {
		            mv.addObject("error", true);
		            mv.setViewName("qywx/admin/wuxigongdian.jsp"); // 跳转等待页面，然后再跳回之前页面
		            return mv;
		        }
		    }*/

	public String sendSmsCode(String mobile) {

		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile);

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", mobile, codeObj);
			return codeObj.toString();
		}
		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
		log.debug("手机号生成验证码成功:{},{}", mobile, code);
		redisTemplate.opsForValue().set(
				CacheConstants.DEFAULT_CODE_KEY + LoginTypeEnum.SMS.getType() + StringPool.AT + mobile
				, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS);
		return code;
	}
}