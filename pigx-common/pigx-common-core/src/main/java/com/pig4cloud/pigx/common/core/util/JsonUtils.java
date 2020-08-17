package com.pig4cloud.pigx.common.core.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.List;
import java.util.Map;

public class JsonUtils {
	private static final SerializeConfig config = new SerializeConfig();
	private static final SerializerFeature[] features;

	static {
		config.put(Long.class, new LongSerializer());
		features = new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullStringAsEmpty};
	}

	public JsonUtils() {
	}

	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config, new SerializerFeature[0]);
	}

	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	public static Object[] toArray(String text) {
		return toArray(text, (Class)null);
	}

	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	public static Map stringToMap(String s) {
		return JSONObject.parseObject(s);
	}

	public static String mapToString(Map m) {
		return JSONObject.toJSONString(m);
	}

	public static JSONObject pareseFastJson(String jsonString) {
		try {
			return JSONObject.parseObject(jsonString, new Feature[]{Feature.IgnoreNotMatch});
		} catch (Exception var2) {
			return null;
		}
	}
}

