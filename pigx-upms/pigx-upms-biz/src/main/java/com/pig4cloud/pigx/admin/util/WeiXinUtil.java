package com.pig4cloud.pigx.admin.util;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 企业微信工具
 * auth: power
 * date: 2020.04.27
 */
public class WeiXinUtil {

	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	//微信的请求url
	//获取access_token的接口地址（GET） 限200（次/天）  
	public final static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={corpsecret}";
	//获取jsapi_ticket的接口地址（GET） 限200（次/天）  
	public final static String jsapi_ticket_url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESSTOKEN";


	/**
	 * 1.发起https请求并获取结果
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化  
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象  
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）  
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET" .equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时  
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码  
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串  
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源  
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 2.发送https请求之获取临时素材
	 *
	 * @param requestUrl
	 * @param savePath   文件的保存路径
	 * @return
	 * @throws Exception
	 */
	public static File getFile(String requestUrl, String savePath) throws Exception {
		//String path=System.getProperty("user.dir")+"/img//1.png";


		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = {new MyX509TrustManager()};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod("GET");

		httpUrlConn.connect();


		String content_disposition = httpUrlConn.getHeaderField("content-disposition");
		//微信服务器生成的文件名称
		String fileName ="";
		String[] content_arr = content_disposition.split(";");
		if(content_arr.length  == 2){
			String tmp = content_arr[1];
			int index = tmp.indexOf("\"");
			fileName =tmp.substring(index+1, tmp.length()-1);
		}

		System.err.println("fileName:"+fileName);

		//新文件名
		String newname = IdUtil.getShortUuid()+"."+fileName.split("\\.")[1];

		savePath = savePath + newname;

		System.out.println("savePath" + savePath);


		//下载文件到f文件
		File file = new File(savePath);


		// 获取微信返回的输入流
		InputStream in = httpUrlConn.getInputStream();

		//输出流，将微信返回的输入流内容写到文件中
		FileOutputStream out = new FileOutputStream(file);

		int length = 100 * 1024;
		byte[] byteBuffer = new byte[length]; //存储文件内容

		int byteread = 0;
		int bytesum = 0;

		while ((byteread = in.read(byteBuffer)) != -1) {
			bytesum += byteread; //字节数 文件大小
			out.write(byteBuffer, 0, byteread);

		}
		System.out.println("bytesum: " + bytesum);

		in.close();
		// 释放资源
		out.close();
		in = null;
		out = null;

		httpUrlConn.disconnect();


		return file;
	}


	/**
	 * 封装成file再下载
	 * @param requestUrl
	 * @param savePath
	 * @param response
	 * @throws Exception
	 */
	public static void getFile(String requestUrl, String savePath, HttpServletResponse response) throws Exception {

		ServletOutputStream out = null;

		FileInputStream ips = null;

		File file = getFile(requestUrl, savePath);


		response.setHeader("downflag", "down");

		response.setContentType("multipart/form-data");

		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());


		InputStream input = null;
		OutputStream output = null;

		try {
			input = FileUtils.openInputStream(file);
			output = response.getOutputStream();
			IOUtils.copy(input, output);
		} catch (Exception e) {
			e.printStackTrace();
		}


//		if(!file.exists()) {
//			//如果文件不存在就跳出
//			return;
//		}
//
//		ips = new FileInputStream(file);
//
//		response.setHeader("downflag", "down");
//
//		response.setContentType("multipart/form-data");
//
//		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
//
//		out = response.getOutputStream();
//
//		//读取文件流
//		int len = 0;
//		byte[] buffer = new byte[1024 * 10];
//		while ((len = ips.read(buffer)) != -1){
//			out.write(buffer,0,len);
//		}
//		out.flush();
//
//		out.close();
//		ips.close();

	}




	/**
	 * 2.发送https请求之获取临时素材 获取流
	 *
	 * @param requestUrl
	 * @return
	 * @throws Exception
	 */
	public static void getFile(String requestUrl, HttpServletResponse response) throws Exception {

		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod("GET");

		httpUrlConn.connect();


		String content_disposition = httpUrlConn.getHeaderField("content-disposition");
		//微信服务器生成的文件名称
		String fileName ="";
		String[] content_arr = content_disposition.split(";");
		if(content_arr.length  == 2){
			String tmp = content_arr[1];
			int index = tmp.indexOf("\"");
			fileName =tmp.substring(index+1, tmp.length()-1);
		}

		System.err.println("fileName:"+fileName);

		//新文件名
		String newname = IdUtil.getShortUuid()+"."+fileName.split("\\.")[1];

		// 获取微信返回的输入流
		InputStream in = httpUrlConn.getInputStream();

		response.setHeader("downflag", "down");

		response.setContentType("multipart/form-data");

		response.setHeader("Content-Disposition", "attachment;filename=" + newname);
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			IOUtils.copy(in, output);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			httpUrlConn.disconnect();

		}

	}


	/**
	 * @param requestUrl 微信上传临时素材的接口url
	 * @param file       要上传的文件
	 * @return String  上传成功后，微信服务器返回的消息
	 * @desc ：2.微信上传素材的请求方法
	 */
	public static String httpRequest(String requestUrl, File file) {
		StringBuffer buffer = new StringBuffer();

		try {
			//1.建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

			//1.1输入输出设置
			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setUseCaches(false); // post方式不能使用缓存
			//1.2设置请求头信息
			httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConn.setRequestProperty("Charset", "UTF-8");
			//1.3设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			httpUrlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);




			// 请求正文信息
			// 第一部分：
			//2.将文件头输出到微信服务器
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
					+ "\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
			// 将表头写入输出流中：输出表头
			outputStream.write(head);

			//3.将文件正文部分输出到微信服务器
			// 把文件以流文件的方式 写入到微信服务器中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				outputStream.write(bufferOut, 0, bytes);
			}
			in.close();
			//4.将结尾部分输出到微信服务器
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			outputStream.write(foot);
			outputStream.flush();
			outputStream.close();


			//5.将微信服务器返回的输入流转换成字符串  
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源  
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();


		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 2.发起http请求获取返回结果
	 *
	 * @param requestUrl 请求地址
	 * @return
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串  
			InputStream inputStream = httpUrlConn.getInputStream();
			//InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);

			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源  
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
		}
		return buffer.toString();
	}


	/**
	 * 3.获取access_token
	 *
	 * @param appid     凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("{corpId}", appid).replace("{corpsecret}", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功  
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败  
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 4. 获取JsapiTicket
	 *
	 * @param accessToken
	 * @return
	 */
	public static String getJsapiTicket(String accessToken) {


		String requestUrl = jsapi_ticket_url.replace("ACCESSTOKEN", accessToken);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

		String jsapi_ticket = "";
		// 如果请求成功  
		if (null != jsonObject) {
			try {
				jsapi_ticket = jsonObject.getString("ticket");

			} catch (JSONException e) {

				// 获取token失败  
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return jsapi_ticket;
	}

	/**
	 * 3.获取企业微信的JSSDK配置信息
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getWxConfig(HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
		//1.准备好参与签名的字段

		String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
		//System.out.println("nonceStr:"+nonceStr);
		String accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.agentSecret).getToken();
		String jsapi_ticket = getJsapiTicket(accessToken);// 必填，生成签名的H5应用调用企业微信JS接口的临时票据
		//System.out.println("jsapi_ticket:"+jsapi_ticket);
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
		//System.out.println("timestamp:"+timestamp);
		String url = request.getRequestURL().toString();
		//System.out.println("url:"+url);

		//2.字典序           ，注意这里参数名必须全部小写，且必须有序
		String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

		//3.sha1签名
		String signature = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(sign.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			//System.out.println("signature:"+signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("appId", WeiXinParamesUtil.corpId);
		ret.put("timestamp", timestamp);
		ret.put("nonceStr", nonceStr);
		ret.put("signature", signature);
		return ret;
	}


	/**
	 * 方法名：byteToHex</br>
	 * 详述：字符串加密辅助方法 </br>
	 * 开发人员：souvc  </br>
	 * 创建时间：2016-1-5  </br>
	 *
	 * @param hash
	 * @return 说明返回值含义
	 * @throws
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;

	}


	private static String getExt(String contentType) {
		if ("image/jpeg" .indexOf(contentType) != -1) {
			return ".jpg";
		} else if ("image/png" .indexOf(contentType) != -1) {
			return ".png";
		} else if ("image/gif" .indexOf(contentType) != -1) {
			return ".gif";
		}

		return null;
	}
}