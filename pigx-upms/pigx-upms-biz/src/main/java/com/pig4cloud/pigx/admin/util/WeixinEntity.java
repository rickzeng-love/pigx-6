package com.pig4cloud.pigx.admin.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class WeixinEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String template_id;

	private String access_token;

	private String open_template_id;

	private JSONObject jsonstr;


}
