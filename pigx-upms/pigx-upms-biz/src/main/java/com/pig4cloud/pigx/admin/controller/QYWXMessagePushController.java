package com.pig4cloud.pigx.admin.controller;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SysWorkflowTemplateCorpMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.EnterpriseConst;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/messagePush")
@Api(value = "messagePush", tags = "企业微信开放了消息发送接口，企业可以使用这些接口让自定义应用与企业微信后台或用户间进行双向通信。")
public class QYWXMessagePushController {


	private final SysQywxApplicationService sysQywxApplicationService;
	private final QywxWorkitemService qywxWorkitemService;
	private final SysUserService sysUserService;
	private final OtdepartmentService otdepartmentService;
	private final QywxWorkitemApprovalService qywxWorkitemApprovalService;
	private final QywxWorkitemCcinfoService qywxWorkitemCCInfoService;
	private final SysWorkflowTemplateCorpMapper sysWorkflowTemplateCorpMapper;
	private final  EtstaffRegisterService etstaffRegisterService;

	/*
	 *企业微信开放了消息发送接口，企业可以使用这些接口让自定义应用与企业微信后台或用户间进行双向通信
	 * 授权公众号的回调地址 处理消息等用户操作时，请务必使用appid进行匹配
	 *
	 * @param appid
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AesException
	 * @throws DocumentException
	 */
	@RequestMapping("/return/callback")
	public void acceptMessageAndEvent(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		sysQywxApplicationService.getSuiteApplication();
		String scorpid = EnterpriseConst.SCORPID;
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------corpid:" + scorpid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;// 微信推送给第三方开放平台的消息一定是加过密的，无消息加密无法解密消息
		if (StringUtil.isEmpty(echostr)) {            // 消息处理
			StringBuilder sb = new StringBuilder();
			BufferedReader in = request.getReader();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			String xml = sb.toString();
			log.info("接收到的xml信息----------" + xml);
			checkWeixinAllNetworkCheck(request, response, scorpid, xml);
		} else {
			// 校验，此处的receiveid为企业的corpid
			WXBizMsgCrypt wxcorp = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, scorpid);
			sEchoStr = wxcorp.VerifyURL(msgSignature, timestamp, nonce, echostr);
			log.info("-----------------------URL验证成功，返回解析后的的echostr:" + sEchoStr);
			// 输出响应的内容。
			response.getWriter().write(sEchoStr);
		}
	}

	@RequestMapping("/return/callbackB")
	public void acceptMessageAndEventB(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, AesException {
		sysQywxApplicationService.getSuiteApplication();
		String scorpid = EnterpriseConst.SCORPID;
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String msgSignature = request.getParameter("msg_signature");
		String echostr = request.getParameter("echostr");
		log.info("-----------------------corpid:" + scorpid);
		log.info("-----------------------nonce:" + nonce);
		log.info("-----------------------timestamp:" + timestamp);
		log.info("-----------------------msg_signature:" + msgSignature); // 签名串
		log.info("-----------------------echostr:" + echostr);// 随机串
		String sEchoStr; // url验证时需要返回的明文
		if (StringUtil.isEmpty(msgSignature)) return;// 微信推送给第三方开放平台的消息一定是加过密的，无消息加密无法解密消息
		if (StringUtil.isEmpty(echostr)) {            // 消息处理
			StringBuilder sb = new StringBuilder();
			BufferedReader in = request.getReader();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			String xml = sb.toString();
			log.info("接收到的xml信息----------" + xml);
			checkWeixinAllNetworkCheckB(request, response, scorpid, xml);
		} else {
			// 校验，此处的receiveid为企业的corpid
			WXBizMsgCrypt wxcorp = new WXBizMsgCrypt(EnterpriseConst.STOKEN_B, EnterpriseConst.SENCODINGAESKEY_B, scorpid);
			sEchoStr = wxcorp.VerifyURL(msgSignature, timestamp, nonce, echostr);
			log.info("-----------------------URL验证成功，返回解析后的的echostr:" + sEchoStr);
			// 输出响应的内容。
			response.getWriter().write(sEchoStr);
		}
	}

	public void checkWeixinAllNetworkCheck(HttpServletRequest request, HttpServletResponse response, String scorpid, String xml) throws DocumentException, IOException {
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();
		String toUserName = rootElt.elementText("ToUserName"); // 企业微信的CorpID，当为第三方套件回调事件时，CorpID的内容为suiteid
		String agentID = rootElt.elementText("AgentID"); // 接收的应用id，可在应用的设置页面获取
		String encrypt = rootElt.elementText("Encrypt"); // 消息结构体加密后的字符串

		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, scorpid);
			// 解析出url上的参数值如下：
			String sVerifyNonce = request.getParameter("nonce");
			String sVerifyTimeStamp = request.getParameter("timestamp");
			String sVerifyMsgSig = request.getParameter("msg_signature");
			// 检验消息的真实性，并且获取解密后的明文.
			String sEncryptXml = wxcpt.DecryptMsg(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, xml);
			log.info("解密后的xml信息----------" + sEncryptXml);
			parsingMsg(request, response, sEncryptXml);
			// 验证URL失败，错误原因请查看异常
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void checkWeixinAllNetworkCheckB(HttpServletRequest request, HttpServletResponse response, String scorpid, String xml) throws DocumentException, IOException {
		Document doc = DocumentHelper.parseText(xml);
		Element rootElt = doc.getRootElement();
		String toUserName = rootElt.elementText("ToUserName"); // 企业微信的CorpID，当为第三方套件回调事件时，CorpID的内容为suiteid
		String agentID = rootElt.elementText("AgentID"); // 接收的应用id，可在应用的设置页面获取
		String encrypt = rootElt.elementText("Encrypt"); // 消息结构体加密后的字符串

		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN_B, EnterpriseConst.SENCODINGAESKEY_B, scorpid);
			// 解析出url上的参数值如下：
			String sVerifyNonce = request.getParameter("nonce");
			String sVerifyTimeStamp = request.getParameter("timestamp");
			String sVerifyMsgSig = request.getParameter("msg_signature");
			// 检验消息的真实性，并且获取解密后的明文.
			String sEncryptXml = wxcpt.DecryptMsg(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, xml);
			log.info("解密后的xml信息----------" + sEncryptXml);
			parsingMsg(request, response, sEncryptXml);
			// 验证URL失败，错误原因请查看异常
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//对解密后的xml信息进行处理
	public void parsingMsg(HttpServletRequest request, HttpServletResponse response, String sEncryptXml) throws DocumentException, IOException {
		Document doc = DocumentHelper.parseText(sEncryptXml);
		Element rootElt = doc.getRootElement();
		String msgType = rootElt.elementText("MsgType");
		String toUserName = rootElt.elementText("ToUserName");
		String fromUserName = rootElt.elementText("FromUserName");

		log.info("---消息类型msgType：" + msgType + "-----------------企业微信CorpID:" + toUserName + "-----------------成员UserID:" + fromUserName);
		if ("event".equals(msgType)) {
			String event = rootElt.elementText("Event");
			log.info("开始解析事件消息--------,事件类型：" + event);
			replyEventMessage(request, response, event, toUserName, fromUserName, sEncryptXml);
		} else if ("text".equals(msgType)) {
			log.info("开始解析文本消息--------");
			String content = rootElt.elementText("Content");
			processTextMessage(request, response, content, toUserName, fromUserName);
		}
	}

	public void replyEventMessage(HttpServletRequest request, HttpServletResponse response, String event, String toUserName, String fromUserName, String sEncryptXml) throws DocumentException, IOException {
		switch (event) {
			case "sys_approval_change":
				log.info("开始解析审批状态通知消息--------");
				log.info("--------------------企业的cropid " + toUserName);
				Document doc = DocumentHelper.parseText(sEncryptXml);
				Element rootElt = doc.getRootElement();
				String agentid = rootElt.elementText("AgentID");
				log.info("--------------------企业应用的id agentid:" + agentid);
				//解析审批流程信息
				Element ApprovalInfoElt = rootElt.element("ApprovalInfo");
				String thirdNo = null;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("SpNo"))) {
					thirdNo = ApprovalInfoElt.elementText("SpNo");
					log.info("--------------------审批单编号 thirdNo:" + thirdNo);
				}
				String openSpName = null;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("SpName"))) {
					openSpName = ApprovalInfoElt.elementText("SpName");
					log.info("--------------------审批模板名称 openSpName:" + openSpName);
				}
				String openTemplateId = null;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("TemplateId"))) {
					openTemplateId = ApprovalInfoElt.elementText("TemplateId");
					log.info("--------------------审批模板id openTemplateId:" + openTemplateId);
				}
				int openSpStatus = 0;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("SpStatus"))) {
					openSpStatus = Integer.parseInt(ApprovalInfoElt.elementText("SpStatus"));
					log.info("--------------------申请单当前审批状态 1-审批中；2-已通过；3-已驳回；4-已取消 openSpStatus:" + openSpStatus);
				}
				String applyTime = null;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("ApplyTime"))) {
					int time = Integer.parseInt(ApprovalInfoElt.elementText("ApplyTime"));
					applyTime = DateUtils.stampToDate(time);
					log.info("--------------------提交申请时间 applyTime:" + applyTime);
				}
				Integer currentapprovalId = null;
				String spt = "";
				String spr = "";
				//解析申请人信息
				Element applyerElt = ApprovalInfoElt.element("Applyer");
				String applyUserName = null;
				int applyUserId = 0;
				if (StringUtil.isNotEmpty(applyerElt.elementText("UserId"))) {
					applyUserId = Integer.parseInt(applyerElt.elementText("UserId"));
					log.info("--------------------提交者userid applyUserId:" + applyUserId);
					UserVO user = sysUserService.selectUserVoById(applyUserId);
					applyUserName = user.getUsername();
					log.info("--------------------提交者姓名 applyUserName:" + applyUserName);
				}
				int applyDeptId = 0;
				String applyUserParty = null;
				if (StringUtil.isNotEmpty(applyerElt.elementText("Party"))) {
					applyDeptId = Integer.parseInt(applyerElt.elementText("Party"));
					log.info("--------------------提交者所在部门id deptId:" + applyDeptId);
					Otdepartment otdepartment = otdepartmentService.getById(applyDeptId);
					applyUserParty = otdepartment.getTitle();
					log.info("--------------------提交者所在部门 applyUserParty:" + applyUserParty);
				}

				int statuChangeEvent = 0;
				if (StringUtil.isNotEmpty(ApprovalInfoElt.elementText("StatuChangeEvent"))) {
					statuChangeEvent = Integer.parseInt(ApprovalInfoElt.elementText("StatuChangeEvent"));
					log.info("--------------------审批申请状态变化类型：1-提单；2-同意；3-驳回；4-转审；5-催办；6-撤销；8-通过后撤销；10-添加备注 statuChangeEvent:" + statuChangeEvent);
				}
				//查询
				QywxWorkitem qywxWorkitem = qywxWorkitemService.getById(thirdNo);
				if (qywxWorkitem == null) {
					qywxWorkitem = new QywxWorkitem();
					//thirdNo = UUID.randomUUID().toString().replace("-", "");
				}

				//获取流程类型
				QueryWrapper<SysWorkflowTemplateCorp> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("open_template_id",openTemplateId);
				SysWorkflowTemplateCorp sysWorkflowTemplateCorp = sysWorkflowTemplateCorpMapper.selectOne(queryWrapper);
				String type = sysWorkflowTemplateCorp.getType();
				log.info("--------------------审批类型 type:" + type);

				qywxWorkitem.setThirdno(thirdNo);
				qywxWorkitem.setOpenspname(openSpName);
				qywxWorkitem.setOpentemplateid(openTemplateId);
				qywxWorkitem.setOpenspstatus(openSpStatus);
				qywxWorkitem.setApplytime(applyTime);
				qywxWorkitem.setApplyusername(applyUserName);
				qywxWorkitem.setApplyuserid(applyUserId);
				qywxWorkitem.setApplydeptid(applyDeptId);
				qywxWorkitem.setApplyuserparty(applyUserParty);
				qywxWorkitem.setStatuchangeevent(statuChangeEvent);
				qywxWorkitem.setQywxcorpid(toUserName);
				qywxWorkitem.setType(type);
				//保存或者更新
				qywxWorkitemService.saveOrUpdate(qywxWorkitem);


				//解析审批人信息
				int approvalid = 0;
				List<Element> list = ApprovalInfoElt.elements("SpRecord");
				if (list != null || list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Element nodeElt = list.get(i);
						int nodeStatus = 0;
						if (StringUtil.isNotEmpty(nodeElt.elementText("SpStatus"))) {
							nodeStatus = Integer.parseInt(nodeElt.elementText("SpStatus"));
							log.info("--------------------节点审批操作状态 1-审批中；2-已同意；3-已驳回；4-已转审 nodeStatus:" + nodeStatus);
						}
						int nodeAttr = 0;
						if (StringUtil.isNotEmpty(nodeElt.elementText("ApproverAttr"))) {
							nodeAttr = Integer.parseInt(nodeElt.elementText("ApproverAttr"));
							log.info("--------------------审批节点属性 1-或签；2-会签 nodeAttr:" + nodeAttr);
						}

						QywxWorkitemApproval approval = new QywxWorkitemApproval();
						approval.setThirdno(thirdNo);
						approval.setOpenspname(openSpName);
						approval.setOpentemplateid(openTemplateId);
						approval.setOpenspstatus(openSpStatus);
						approval.setApplytime(applyTime);
						approval.setApplyusername(applyUserName);
						approval.setApplyuserid(applyUserId);
						approval.setApplydeptid(applyDeptId);
						approval.setApplyuserparty(applyUserParty);
						approval.setStatuchangeevent(statuChangeEvent);
						approval.setQywxcorpid(toUserName);
						approval.setType(type);
						approval.setNodestatus(nodeStatus);
						approval.setNodeattr(nodeAttr);

						List<Element> elist = nodeElt.elements("Details");

						if (elist != null || elist.size() > 0) {
							for (int j = 0; j < elist.size(); j++) {
								Element itemElt = elist.get(j);

								//解析分支审批人信息
								Element applyerItemElt = itemElt.element("Approver");
								String itemName = null;

								int itemUserId = 0;
								if (StringUtil.isNotEmpty(applyerItemElt.elementText("UserId"))) {
									itemUserId = Integer.parseInt(applyerItemElt.elementText("UserId"));
									log.info("--------------------分支审批人userid itemUserId:" + itemUserId);
									UserVO user1 = sysUserService.selectUserVoById(itemUserId);
									itemName = user1.getUsername();
									log.info("--------------------分支审批人姓名 itemName:" + itemName);
								}
								String itemImage = null;
								if (StringUtil.isNotEmpty(itemElt.elementText("ItemImage"))) {
									itemImage = itemElt.elementText("ItemImage");
									log.info("--------------------分支审批人头像 itemImage:" + itemImage);
								}
								int itemStatus = 0;
								if (StringUtil.isNotEmpty(itemElt.elementText("SpStatus"))) {
									itemStatus = Integer.parseInt(itemElt.elementText("SpStatus"));
									log.info("--------------------分支审批审批操作状态：1-审批中；2-已同意；3-已驳回；4-已转审 itemStatus:" + itemStatus);
								}
								String itemSpeech = null;
								if (StringUtil.isNotEmpty(itemElt.elementText("Speech"))) {
									itemSpeech = itemElt.elementText("Speech");
									log.info("--------------------分支审批人审批意见 itemSpeech:" + itemSpeech);
								}
								String itemOpTime = null;
								if (StringUtil.isNotEmpty(itemElt.elementText("SpTime"))) {
									int time1 = Integer.parseInt(itemElt.elementText("SpTime"));
									spt = time1+",";
									spr = String.valueOf(itemUserId)+",";
									itemOpTime = DateUtils.stampToDate(time1);
									log.info("--------------------分支审批人操作时间 0为尚未操作 itemOpTime:" + itemOpTime);
								}
								String mediaId = null;
								if (StringUtil.isNotEmpty(itemElt.elementText("MediaId"))) {
									mediaId = itemElt.elementText("MediaId");
									log.info("--------------------分支审批人审批意见附件media_id mediaId:" + mediaId);
								}
								approval.setItemname(itemName);
								approval.setItemuserid(itemUserId);
								approval.setItemstatus(itemStatus);
								approval.setItemspeech(itemSpeech);
								approval.setItemoptime(itemOpTime);
								//保存
								qywxWorkitemApprovalService.save(approval);
								approvalid = approval.getId();

							}
						}
					}
				}
				String[] arrayspt = null;
				String[] arrayspr = null;
				if(!StringUtils.isEmpty(spt)){
					spt = spt.substring(0,spt.length() - 1);
					arrayspt = spt.split(",");
					if(StringUtils.isEmpty(arrayspt)){
						arrayspt = new String[]{spt};
					}
				}
				if(!StringUtils.isEmpty(spr)){
					spr = spr.substring(0,spr.length() - 1);
					arrayspr = spr.split(",");
					if(StringUtils.isEmpty(arrayspr)){
						arrayspr = new String[]{spr};
					}
				}
				log.info("--------------------所有审批人操作时间" + spt);
				log.info("--------------------所有审批人" + spr);
				List sprList = new ArrayList(arrayspr.length);
				Map mapspr = null;
				for(int gg=0;gg<arrayspr.length;gg++){
					mapspr = new HashMap();
					mapspr.put("spt",arrayspt[gg]);
					mapspr.put("spr",arrayspr[gg]);
					sprList.add(gg,mapspr);
				}

				Collections.sort(sprList, new Comparator<Map>() {
					public int compare(Map u1, Map u2) {
						return (u2.get("spt").toString()).compareTo(u1.get("spt").toString()); //降序
					}
				});
				Map currentMap = null;
				if(sprList.size()>0){
					currentMap = (Map)sprList.get(0);
					currentapprovalId = currentMap.get("spr").toString()!=null ? Integer.parseInt(currentMap.get("spr").toString()):0;
				}
				log.info("--------------------当前审批人currentapprovalId：" + currentapprovalId);
				//审批人
				//处理业务逻辑
				approvedWorkitem(openSpName, openSpStatus, thirdNo,currentapprovalId);
				//解析备注信息
				List<Element> commentlist = ApprovalInfoElt.elements("Comments");
				if (commentlist != null || commentlist.size() > 0) {
					for (int i = 0; i < commentlist.size(); i++) {
						Element nodeElt = commentlist.get(i);
						//解析备注信息
						Element itemElt = nodeElt.element("CommentUserInfo");
						String commentname = null;

						int commentuserid = 0;
						if (StringUtil.isNotEmpty(itemElt.elementText("UserId"))) {
							commentuserid = Integer.parseInt(itemElt.elementText("UserId"));
							log.info("--------------------备注人userid commentuserid:" + commentuserid);
							UserVO user3 = sysUserService.selectUserVoById(commentuserid);
							commentname = user3.getUsername();
							log.info("--------------------备注人姓名 commentname:" + commentname);
						}
						String commenttime = null;
						if (StringUtil.isNotEmpty(nodeElt.elementText("CommentTime"))) {
							int time2 = Integer.parseInt(nodeElt.elementText("CommentTime"));
							commenttime = DateUtils.stampToDate(time2);
							log.info("--------------------备注提交时间 0为尚未操作 commenttime:" + commenttime);
						}
						String commentcontent = null;
						if (StringUtil.isNotEmpty(nodeElt.elementText("CommentContent"))) {
							commentcontent = nodeElt.elementText("CommentContent");
							log.info("--------------------备注文本内容 commentcontent:" + commentcontent);
						}
						int commentid = 0;
						if (StringUtil.isNotEmpty(nodeElt.elementText("CommentId"))) {
							commentid = Integer.parseInt(nodeElt.elementText("CommentId"));
							log.info("--------------------备注id commentid:" + commentid);
						}

						QywxWorkitemApproval entiy = qywxWorkitemApprovalService.getById(approvalid);
						entiy.setCommentuserid(commentuserid);
						entiy.setCommentname(commentname);
						entiy.setCreateTime(commenttime);
						entiy.setCommentcontent(commentcontent);
						entiy.setCommentid(commentid);

						qywxWorkitemApprovalService.updateById(entiy);
					}
				}

				//解析抄送人信息
				List<Element> notifyList = ApprovalInfoElt.elements("Notifyer");
				if (notifyList != null || notifyList.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Element notifyElt = list.get(i);
						String itemName = null;

						int itemUserId = 0;
						if (StringUtil.isNotEmpty(notifyElt.elementText("UserId"))) {
							itemUserId = Integer.parseInt(notifyElt.elementText("UserId"));
							log.info("--------------------分支审批人userid itemUserId:" + itemUserId);
							UserVO user2 = sysUserService.selectUserVoById(itemUserId);
							itemName = user2.getUsername();
							log.info("--------------------分支审批人姓名 itemName:" + itemName);
						}

						QywxWorkitemCcinfo ccInfo = new QywxWorkitemCcinfo();
						ccInfo.setThirdno(thirdNo);
						ccInfo.setOpenspname(openSpName);
						ccInfo.setOpentemplateid(openTemplateId);
						ccInfo.setOpenspstatus(openSpStatus);
						ccInfo.setApplytime(applyTime);
						ccInfo.setApplyusername(applyUserName);
						ccInfo.setApplyuserid(applyUserId);
						ccInfo.setApplydeptid(applyDeptId);
						ccInfo.setApplyuserparty(applyUserParty);
						ccInfo.setStatuchangeevent(statuChangeEvent);
						ccInfo.setQywxcorpid(toUserName);
						ccInfo.setType(type);
						ccInfo.setItemname(itemName);
						ccInfo.setItemuserid(itemUserId);
						//保存
						qywxWorkitemCCInfoService.save(ccInfo);
					}
				}

				break;
			case "enter_agent":
				String content = "登陆成功";
				log.info("---全网发布接入检测------step.4-------事件回复消息  content=" + content + " toUserName=" + toUserName + "   fromUserName=" + fromUserName);
				replyTextMessage(request, response, content, toUserName, fromUserName);
				break;
			default:
				break;
		}

/*		String content = event + "from_callback";
		log.info("---全网发布接入检测------step.4-------事件回复消息  content=" + content + " toUserName=" + toUserName + "   fromUserName=" + fromUserName);
		replyTextMessage(request, response, content, toUserName, fromUserName);*/
	}


	/**
	 * 文本消息
	 *
	 * @param request
	 * @param response
	 * @param content
	 * @param toUserName
	 * @param fromUserName
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void processTextMessage(HttpServletRequest request, HttpServletResponse response, String content, String toUserName, String fromUserName)
			throws IOException, DocumentException {
		String reContent = content + "from_callback";
		log.info("---全网发布接入检测------step.4-------文本回复消息  content=" + content + "   toUserName=" + toUserName + "   fromUserName=" + fromUserName);
		replyTextMessage(request, response, content, toUserName, fromUserName);
	}

	/**
	 * 回复微信服务器"文本消息"
	 *
	 * @param request
	 * @param response
	 * @param content
	 * @param toUserName
	 * @param fromUserName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void replyTextMessage(HttpServletRequest request, HttpServletResponse response, String content, String toUserName, String fromUserName)
			throws DocumentException, IOException {
		Long createTime = Calendar.getInstance().getTimeInMillis() / 1000;
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
		sb.append("<CreateTime>" + createTime + "</CreateTime>");
		sb.append("<MsgType><![CDATA[text]]></MsgType>");
		sb.append("<Content><![CDATA[" + content + "]]></Content>");
		sb.append("</xml>");
		String replyMsg = sb.toString();

		String returnvaleue = "";
		try {
			// 此处的receiveid 随便定义均可通过加密算法，联系企业微信未得到合理解释，暂时按照文档，此处参数使用企业对应的corpid
			log.info("===================>>>企业coidId:" + toUserName);
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(EnterpriseConst.STOKEN, EnterpriseConst.SENCODINGAESKEY, toUserName);
			returnvaleue = wxcpt.EncryptMsg(replyMsg, createTime.toString(), "easemob");
		} catch (AesException e) {
			e.printStackTrace();
		}
		response.getWriter().write(returnvaleue);
	}

	//处理审批通过的
	private void approvedWorkitem(String openSpName, int openSpStatus, String thirdNo, Integer currentapprovalId) {
		//1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付
		if (openSpStatus != 1) {
			switch (openSpName){
				case "录用":
					log.info("录用===================>>>thirdNo:" + thirdNo+"======>"+"openSpStatus:"+openSpStatus+"======>openSpName:"+openSpName+"======>currentapprovalId:"+currentapprovalId);
					etstaffRegisterService.submitLuYongBySpno(thirdNo,openSpStatus,currentapprovalId);
					break;
				case "转正":
					etstaffRegisterService.submitZhuanZhengBySpno(thirdNo,openSpStatus,currentapprovalId);
					break;
				case "离职":
					etstaffRegisterService.submitLiZhiBySpno(thirdNo,openSpStatus,currentapprovalId);
					break;
				case "调薪":

					break;
				case "调岗":
					etstaffRegisterService.submitDiaoDongBySpno(thirdNo,openSpStatus,currentapprovalId);
					break;
				case "请假":

					break;
				case "外出":

					break;
				case "出差":

					break;
				case "补卡":

					break;
				case "加班":

					break;
				case "调班":

					break;
			}
		}
	}



}