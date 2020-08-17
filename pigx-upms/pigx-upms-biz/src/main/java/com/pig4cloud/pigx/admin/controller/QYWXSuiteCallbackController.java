package com.pig4cloud.pigx.admin.controller;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysQywxApplicationMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.ApiSuiteTokenRequest;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.admin.util.EnterpriseAPI;
import com.pig4cloud.pigx.admin.util.QYWXURL;
import com.pig4cloud.pigx.common.core.constant.CacheConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.EnterpriseConst;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/suiteCallback" )
@Api(value = "suiteCallback", tags = "推送更新token，保证第三方应用的token(suite_access_token)不过期,同步更新预授权码")
public class QYWXSuiteCallbackController {
	private final SysQywxApplicationService sysQywxApplicationService;
	private final SysPublicParamService sysPublicParamService;
	private final  CacheManager cacheManager;
	private final SysQywxApplicationMapper sysQywxApplicationMapper;
	private final SysUserService sysUserService;
	private  final OtdepartmentService otdepartmentService;
	private final SystcorpinfoService systcorpinfoService;
	private final SysQywxApplicationAuthorizerService sysQywxApplicationAuthorizerService;

	@Resource
	private RestTemplate restTemplate;

	/*
	 *指令接收 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用
	 */
	@PostMapping("/directive/receive" )
	public void acceptAuthorizeEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		log.info("企业微信服务器开始推送suite_ticket---------10分钟一次-----------");
		processAuthorizeEvent(request,response);
	}

	/*
	 *指令接收 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用
	 */
	@PostMapping("/directive/receiveB" )
	public void acceptAuthorizeEventB(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		log.info("企业微信服务器开始推送suite_ticket---------10分钟一次-----------");
		processAuthorizeEventB(request,response);
	}
	/*
	 *指令接收 企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用
	 */
	@GetMapping("/directive/receive" )
	public void acceptAuthorizeEvent2(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		log.info("企业微信服务器开始推送suite_ticket-----------------------------");
		processAuthorizeEvent2(request,response);
	}
	@GetMapping("/directive/receiveB" )
	public void acceptAuthorizeEvent2B(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		log.info("企业微信服务器开始推送suite_ticket-----------------------------");
		processAuthorizeEvent2B(request,response);
	}
	/*
	服务商处理指令回调，解析suite_ticket数据
	 */
	public void processAuthorizeEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		// 第三方事件回调
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID;
		log.info("-----------------------SUITEID:" + suitedid);
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN);
		log.info("-----------------------SCORPID:" + EnterpriseConst.SCORPID);
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, EnterpriseConst.SUITEID);        // 解析出url上的参数值如下：
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------suitedid:" + suitedid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;        // 回调

		StringBuilder sb = new StringBuilder();
		BufferedReader in = request.getReader();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		String xml = sb.toString();
		log.info("-----------------------服务商接收到的原始 Xml="+xml);
		String exml = wxcpt.DecryptMsg(msgSignature,timestamp,nonce,xml);
		log.info("-----------------------服务商接收到的xml解密后:" + exml);
		processAuthorizationEvent(request, exml);
		log.info("-----------------------解析成功，返回success");
		//output(response,"success"); // 输出响应的内容。
		response.getWriter().write("success");

	}

	public void processAuthorizeEventB(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		// 第三方事件回调
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID_B;
		log.info("-----------------------SUITEID:" + suitedid);
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY_B);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN_B);
		log.info("-----------------------SCORPID:" + EnterpriseConst.SCORPID);
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN_B, EnterpriseConst.SENCODINGAESKEY_B, EnterpriseConst.SUITEID_B);        // 解析出url上的参数值如下：
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------suitedid:" + suitedid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;        // 回调

		StringBuilder sb = new StringBuilder();
		BufferedReader in = request.getReader();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		String xml = sb.toString();
		log.info("-----------------------服务商接收到的原始 Xml="+xml);
		String exml = wxcpt.DecryptMsg(msgSignature,timestamp,nonce,xml);
		log.info("-----------------------服务商接收到的xml解密后:" + exml);
		processAuthorizationEventB(request, exml);
		log.info("-----------------------解析成功，返回success");
		//output(response,"success"); // 输出响应的内容。
		response.getWriter().write("success");

	}


	/*
	服务商处理指令回调，解析suite_ticket数据
	 */
	public void processAuthorizeEvent2(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		// 第三方事件回调
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID;
		log.info("-----------------------SUITEID:" + suitedid);
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN);
//		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, EnterpriseConst.SCORPID);
		// 解析出url上的参数值如下：
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------suitedid:" + suitedid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;        // 回调
		// 校验，此处的receiveid为企业的corpid
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN);
		log.info("-----------------------SCORPID:" + EnterpriseConst.SCORPID);
		WXBizMsgCrypt wxcorp = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, EnterpriseConst.SCORPID);
		sEchoStr = wxcorp.VerifyURL(msgSignature, timestamp, nonce, echostr);
		log.info("-----------------------URL验证成功，返回解析后的的echostr:" + sEchoStr);
		//output(response, sEchoStr); // 输出响应的内容。
		response.getWriter().write(sEchoStr);

	}
	public void processAuthorizeEvent2B(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		// 第三方事件回调
		sysQywxApplicationService.getSuiteApplication();
		String suitedid = EnterpriseConst.SUITEID_B;
		log.info("-----------------------SUITEID:" + suitedid);
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY_B);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN_B);
//		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, EnterpriseConst.SCORPID);
		// 解析出url上的参数值如下：
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------suitedid:" + suitedid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;        // 回调
		// 校验，此处的receiveid为企业的corpid
		log.info("-----------------------SENCODINGAESKEY:" + EnterpriseConst.SENCODINGAESKEY_B);
		log.info("-----------------------STOKEN:" + EnterpriseConst.STOKEN_B);
		log.info("-----------------------SCORPID:" + EnterpriseConst.SCORPID);
		WXBizMsgCrypt wxcorp = new WXBizMsgCrypt(EnterpriseConst.STOKEN_B, EnterpriseConst.SENCODINGAESKEY_B, EnterpriseConst.SCORPID);
		sEchoStr = wxcorp.VerifyURL(msgSignature, timestamp, nonce, echostr);
		log.info("-----------------------URL验证成功，返回解析后的的echostr:" + sEchoStr);
		//output(response, sEchoStr); // 输出响应的内容。
		response.getWriter().write(sEchoStr);

	}


	/**
	 *  对解密后的xml信息进行处理
	 *
	 *  @param
	 */
	public void  processAuthorizationEvent(HttpServletRequest  request,  String  echoXml)  {
		Document doc;
		try  {
			doc  =  DocumentHelper.parseText(echoXml);
			Element rootElt  =  doc.getRootElement();
			//  消息类型
			String  infoType  =  rootElt.elementText("InfoType");
			//String  suiteId  =  rootElt.elementText("SuiteId");
			sysQywxApplicationService.getSuiteApplication();
			String suiteId = EnterpriseConst.SUITEID;
			String suiteSecret = EnterpriseConst.SUITESECRET;
			SysQywxApplication entity  =  sysQywxApplicationMapper.listQYWeixinApplication(suiteId, suiteSecret);
			//  第三方应用的SuiteId
			switch  (infoType)  {
				//  授权成功，从企业微信应用市场发起授权时，企业微信后台会推送授权成功通知。
				//  从第三方服务商网站发起的应用授权流程，由于授权完成时会跳转第三方服务商管理后台，因此不会通过此接口向第三方服务商推送授权成功通知。
				case  "create_auth":
					String  authCode  =  rootElt.elementText("AuthCode");//  授权的auth_code,最长为512字节。用于获取企业的永久授权码。5分钟内有效
					//log.debug("》》》》》》》》》》授权码AuthCode："  +  authCode);        //  换取企业永久授权码

//					String authCode = request.getParameter("auth_code");
					log.info("获取永久授权方法内临时授权码:auth_code:"+authCode);
					entity.setAuthcode(authCode);
					sysQywxApplicationService.getPermanentCodeAndAccessToken(entity);

					break;
					//  变更授权，服务商接收到变更通知之后，需自行调用获取企业授权信息进行授权内容变更比对。
				case  "change_auth":
					String  changeAuthCorpid  =  rootElt.elementText("AuthCorpId");//  授权方的corpid
					//  获取并更新本地授权的企业信息
					//EnterpriseAPI.getAuthInfo(suiteId,  changeAuthCorpid);        break;      //  取消授权，当授权方（即授权企业）在企业微信管理端的授权管理中，取消了对应用的授权托管后，企业微信后台会推送取消授权通知。
				case  "cancel_auth":        //  TODO  删除企业授权信息
					String  cancelAuthCorpid  =  rootElt.elementText("AuthCorpId");//  授权方的corpid
					//sysQywxApplicationService.deleteBySuiteAndCorpId(suiteId,  cancelAuthCorpid);
					QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper = new QueryWrapper<>();
					queryWrapper.eq("auth_corpid",cancelAuthCorpid);
					queryWrapper.eq("suite_id",suiteId);
					sysQywxApplicationAuthorizerService.remove(queryWrapper);
					break;      //  企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
				case  "suite_ticket":
					String  suiteTicket  =  rootElt.elementText("SuiteTicket");
					String  timeStamp  =  rootElt.elementText("TimeStamp");  //  时间戳-秒
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳========================="  +  timeStamp);        //  存储ticket
					log.info("推送SuiteTicket协议-----------suiteTicket  =  "  +  suiteTicket);
					//EnterpriseConst  suiteConst  =  new  EnterpriseConst("suite");
					//suiteConst.setKey(suiteId);
					//String  suiteSecret  =  suiteConst.getValue();
					//获取suiteId和suiteSecret

					/*String suiteId = EnterpriseConst.SUITEID;

					String suiteSecret = EnterpriseConst.SUITESECRET;
					SysQywxApplication entity  =  sysQywxApplicationMapper.listQYWeixinApplication(suiteId, suiteSecret);*/
					if(entity==null){
						entity = new SysQywxApplication();
						entity.setSuiteId(suiteId);
						entity.setSuiteSecret(suiteSecret);
						entity.setSuiteTicket(suiteTicket);
						sysQywxApplicationService.save(entity);
					}
					entity.setSuiteTicket(suiteTicket);
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳(毫秒)========================="  +  Long.parseLong(timeStamp)  *  1000);
					Date date  =  new  Date(Long.parseLong(timeStamp)  *  1000);

					//Date  ticketDate  =  DateUtil.parseDate(DateUtil.formatStandardDatetime(date));
					//更新suiteTicket
					String ticketDate = DateUtils.formatFORMATFULL(date);
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳=========================" +ticketDate );
					entity.setTicketTime(ticketDate);
					sysQywxApplicationService.updateById(entity);
					//  获取第三方应用凭证，有效期2小时
					//ApiSuiteTokenRequest apiSuiteToken  =  new  ApiSuiteTokenRequest();
					//apiSuiteToken.setSuite_id(suiteId);
					//apiSuiteToken.setSuite_secret(suiteSecret);        //  授权事件接收会每隔10分钟检验一下ticket的有效性，从而保证了此处的ticket是长期有效的
					//apiSuiteToken.setSuite_ticket(suiteTicket);        //  验证token有效性(2小时)
					String  suiteAccessToken  =  sysQywxApplicationService.getSuiteAccessToken(entity);
					entity.setSuiteAccessToken(suiteAccessToken);
					log.info("suiteAccessToken:"+suiteAccessToken);
					//  验证预授权码有效性(10分钟)
					String preAuthCode = sysQywxApplicationService.getPreAuthCode(entity);
					log.info("preAuthCode:"+preAuthCode);
					break;      //  变更通知，根据ChangeType区分消息类型
				case  "change_contact":        //  TODO  更新令牌等
					break;
				default:
					break;
			}
		}  catch  (DocumentException  e)  {
			e.printStackTrace();
		}

	}

	public void  processAuthorizationEventB(HttpServletRequest  request,  String  echoXml)  {
		Document doc;
		try  {
			doc  =  DocumentHelper.parseText(echoXml);
			Element rootElt  =  doc.getRootElement();
			//  消息类型
			String  infoType  =  rootElt.elementText("InfoType");
			//String  suiteId  =  rootElt.elementText("SuiteId");
			sysQywxApplicationService.getSuiteApplication();
			String suiteId = EnterpriseConst.SUITEID_B;
			String suiteSecret = EnterpriseConst.SUITESECRET_B;
			SysQywxApplication entity  =  sysQywxApplicationMapper.listQYWeixinApplication(suiteId, suiteSecret);
			//  第三方应用的SuiteId
			switch  (infoType)  {
				//  授权成功，从企业微信应用市场发起授权时，企业微信后台会推送授权成功通知。
				//  从第三方服务商网站发起的应用授权流程，由于授权完成时会跳转第三方服务商管理后台，因此不会通过此接口向第三方服务商推送授权成功通知。
				case  "create_auth":
					String  authCode  =  rootElt.elementText("AuthCode");//  授权的auth_code,最长为512字节。用于获取企业的永久授权码。5分钟内有效
					//log.debug("》》》》》》》》》》授权码AuthCode："  +  authCode);        //  换取企业永久授权码

//					String authCode = request.getParameter("auth_code");
					log.info("获取永久授权方法内临时授权码:auth_code:"+authCode);
					entity.setAuthcode(authCode);
					sysQywxApplicationService.getPermanentCodeAndAccessToken(entity);

					break;
				//  变更授权，服务商接收到变更通知之后，需自行调用获取企业授权信息进行授权内容变更比对。
				case  "change_auth":
					String  changeAuthCorpid  =  rootElt.elementText("AuthCorpId");//  授权方的corpid
					//  获取并更新本地授权的企业信息
					//EnterpriseAPI.getAuthInfo(suiteId,  changeAuthCorpid);        break;      //  取消授权，当授权方（即授权企业）在企业微信管理端的授权管理中，取消了对应用的授权托管后，企业微信后台会推送取消授权通知。
				case  "cancel_auth":        //  TODO  删除企业授权信息
					String  cancelAuthCorpid  =  rootElt.elementText("AuthCorpId");//  授权方的corpid
					//sysQywxApplicationService.deleteBySuiteAndCorpId(suiteId,  cancelAuthCorpid);
					QueryWrapper<SysQywxApplicationAuthorizer> queryWrapper = new QueryWrapper<>();
					queryWrapper.eq("auth_corpid",cancelAuthCorpid);
					queryWrapper.eq("suite_id",suiteId);
					sysQywxApplicationAuthorizerService.remove(queryWrapper);
					break;      //  企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
				case  "suite_ticket":
					String  suiteTicket  =  rootElt.elementText("SuiteTicket");
					String  timeStamp  =  rootElt.elementText("TimeStamp");  //  时间戳-秒
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳========================="  +  timeStamp);        //  存储ticket
					log.info("推送SuiteTicket协议-----------suiteTicket  =  "  +  suiteTicket);
					//EnterpriseConst  suiteConst  =  new  EnterpriseConst("suite");
					//suiteConst.setKey(suiteId);
					//String  suiteSecret  =  suiteConst.getValue();
					//获取suiteId和suiteSecret

					/*String suiteId = EnterpriseConst.SUITEID;

					String suiteSecret = EnterpriseConst.SUITESECRET;
					SysQywxApplication entity  =  sysQywxApplicationMapper.listQYWeixinApplication(suiteId, suiteSecret);*/
					if(entity==null){
						entity = new SysQywxApplication();
						entity.setSuiteId(suiteId);
						entity.setSuiteSecret(suiteSecret);
						entity.setSuiteTicket(suiteTicket);
						sysQywxApplicationService.save(entity);
					}
					entity.setSuiteTicket(suiteTicket);
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳(毫秒)========================="  +  Long.parseLong(timeStamp)  *  1000);
					Date date  =  new  Date(Long.parseLong(timeStamp)  *  1000);

					//Date  ticketDate  =  DateUtil.parseDate(DateUtil.formatStandardDatetime(date));
					//更新suiteTicket
					String ticketDate = DateUtils.formatFORMATFULL(date);
					log.info("》》》》》》》》》》》》》》》》》》》》》》》》TimeStamp时间戳=========================" +ticketDate );
					entity.setTicketTime(ticketDate);
					sysQywxApplicationService.updateById(entity);
					//  获取第三方应用凭证，有效期2小时
					//ApiSuiteTokenRequest apiSuiteToken  =  new  ApiSuiteTokenRequest();
					//apiSuiteToken.setSuite_id(suiteId);
					//apiSuiteToken.setSuite_secret(suiteSecret);        //  授权事件接收会每隔10分钟检验一下ticket的有效性，从而保证了此处的ticket是长期有效的
					//apiSuiteToken.setSuite_ticket(suiteTicket);        //  验证token有效性(2小时)
					String  suiteAccessToken  =  sysQywxApplicationService.getSuiteAccessToken(entity);
					entity.setSuiteAccessToken(suiteAccessToken);
					log.info("suiteAccessToken:"+suiteAccessToken);
					//  验证预授权码有效性(10分钟)
					String preAuthCode = sysQywxApplicationService.getPreAuthCode(entity);
					log.info("preAuthCode:"+preAuthCode);
					break;      //  变更通知，根据ChangeType区分消息类型
				case  "change_contact":        //  TODO  更新令牌等
					break;
				default:
					break;
			}
		}  catch  (DocumentException  e)  {
			e.printStackTrace();
		}

	}
	/*

		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);

		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);

	 */
	/*@PostMapping("/createUser" )
	public R createUser(){
		String scorpid = "wwc649dbd1e325fb9f";
		SysUser sysUser =  new SysUser();
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		sysUser.setCorpcode(corpcode);


		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("qywx_flag",1);
		queryWrapper.eq("qywx_corpid","wwc649dbd1e325fb9f");
		List<SysUser> userList = sysUserService.list(queryWrapper);
		SysUser lsysUser = null;
		Map map = null;
		for(int i=0;i<userList.size();i++){
			lsysUser = userList.get(i);
			map = new HashMap();
			map.put("qywxcorpid",lsysUser.getQywxCorpid());
			map.put("userid",lsysUser.getUserId());
			map.put("name",lsysUser.getUsername());
			map.put("alias","test||"+lsysUser.getUsername());
			map.put("mobile",lsysUser.getPhone());
			map.put("department",lsysUser.getDeptId());

			String code = sysQywxApplicationService.createUser(map);
			if("0".equals(code)){
				SysUser sysUser2 =  new SysUser();
				sysUser2.setUserId(lsysUser.getUserId());
				sysUser2.setQywxFlag(0);
				sysUserService.updateById(sysUser2);
			}else{
				return R.ok(code);
			}
		}
		return R.ok("创建成功！");
	}*/

	@PostMapping("/createDepartment" )
	public R createDepartment(){
		String scorpid = "wwc649dbd1e325fb9f";
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();


		QueryWrapper<Systcorpinfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systcorpinfo systcorpinfo = systcorpinfoService.getOne(queryWrapper);
		String qywxcorpid= "";
		if(!StringUtils.isEmpty(systcorpinfo)){
			qywxcorpid = systcorpinfo.getQywxCorpid();

		}else{
			return R.failed("请维护企业微信corpid！");
		}

		QueryWrapper<Otdepartment> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("qywx_flag",1);
		queryWrapper2.eq("corpcode",corpcode);
		List<Otdepartment> departList = otdepartmentService.list(queryWrapper2);
		Otdepartment ldepartment = null;
		Map map = null;
		for(int i=0;i<departList.size();i++){
			ldepartment = departList.get(i);
			map = new HashMap();
			map.put("qywxcorpid",qywxcorpid);
			map.put("id",ldepartment.getDepid());
			map.put("name",ldepartment.getTitle());
			map.put("parentid",ldepartment.getAdminid());
			map.put("order",ldepartment.getXorder());

			String code = sysQywxApplicationService.createDepartment(map);
			if("0".equals(code)){
				Otdepartment otdepartment1 =  new Otdepartment();
				otdepartment1.setDepid(ldepartment.getDepid());
				otdepartment1.setQywxFlag(0);
				otdepartmentService.updateById(otdepartment1);
			}else{
				return R.ok(code);
			}
		}
		return R.ok("创建成功！");
	}



}
