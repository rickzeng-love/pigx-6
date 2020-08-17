package com.pig4cloud.pigx.admin.util;

import java.text.MessageFormat;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.bridge.IMessage;

/** 这里继承RuntimeException异常 **/
public class BusinessException extends RuntimeException {
	/*private static final long serialVersionUID = 2332608236621015980L;
	*//** 错误码 **//*
	private IMessage errorCode;
	private String type = "B-";
	private Object[] msgArgs;
	*//** 用于存放后端返回的数据 **//*
	private Object data;

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(IMessage errorCode) {
		this.errorCode = errorCode;
	}

	public BusinessException(IMessage errorCode, Object data) {
		this.data = data;
		this.errorCode = errorCode;
	}

	public BusinessException(IMessage errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BusinessException(IMessage errorCode, Object[] msgArgs) {
		this.errorCode = errorCode;
		this.msgArgs = msgArgs;
	}

	public BusinessException(IMessage errorCode, Object[] msgArgs, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.msgArgs = msgArgs;
	}

	public Object[] getMsgArgs() {
		return this.msgArgs;
	}

	public void setMsgArgs(Object[] msgArgs) {
		this.msgArgs = msgArgs;
	}

	public String getMsg() {
		String msg = "";
		if(this.errorCode == null) {
			msg = this.getMessage();
			return msg;
		} else {
			try {
				//这里只要知道可以通过错误码获得相关错误信息
				msg = I18nUtils.getMessage(this.errorCode, this.getMsgArgs());
			} catch (Exception var3) {
				msg = MessageFormat.format("错误代码: {0}, 错误参数: {1}, 国际化消息读取失败!", new Object[]{Integer.valueOf(this.errorCode.getCode()), StringUtils.join(this.getMsgArgs(), "|")});
			}

			return msg;
		}
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IMessage getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(IMessage errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}*/
}
