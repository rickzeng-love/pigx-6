package com.pig4cloud.pigx.common.core.util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.lang.reflect.Type;

public class LongSerializer implements ObjectSerializer {
	public LongSerializer() {
	}

	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
				out.write(48);
			} else {
				out.writeNull();
			}

		} else {
			out.writeString(object.toString());
		}
	}
}

