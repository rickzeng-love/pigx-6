package com.pig4cloud.pigx.common.core.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 统一返回值
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Rfe extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
 
    public Rfe() {}


    public static Rfe error() {
        return error("未知异常，请联系管理员！");
    }
    public static Rfe error(String msg) {
		Rfe r = new Rfe();
        r.put("f", false);
        r.put("msg", msg);
        return r;
    }

    public static Rfe ok() {
        return ok("操作成功！");
    }
    public static Rfe ok(String msg) {
		Rfe r = new Rfe();
        r.put("f",true);
        r.put("msg", msg);
        return r;
    }

    public static Rfe errorObj(Object obj) {
		Rfe r = error();
        r.put("obj",obj);
        return r;
    }

    public static Rfe okObj(Object obj) {
		Rfe r = ok();
        r.put("obj",obj);
        return r;
    }

    public static Rfe flag(Boolean f) {
         return flag(f,null);
    }

    public static Rfe flag(Boolean f, String msg) {
        if(f){
            return Rfe.ok(msg);
        }else{
            if(StringUtils.isBlank(msg)){
                return Rfe.error();
            }
            return Rfe.error(msg);
        }
    }

//    自己处理错误信息
    public static Rfe selfopt() {
		Rfe r = Rfe.ok();
        r.put("selfopt", "1");
        return r;
    }

    public Rfe put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @JsonIgnore
    public boolean isSuccess(){
        Object f = this.get("f");
        if(null!=f && f instanceof Boolean){
            return (Boolean)f;
        }
        return false;
    }

    @JsonIgnore
    public String getMsg(){
        Object f = this.get("msg");
        return null==f?"":f.toString();
    }

    @JsonIgnore
    public Object getObj() {
        return getObjByKey("obj");
    }

    @JsonIgnore
    public Object getObjByKey(String key){
        processError();
        return this.get("obj");
    }

    @JsonIgnore
    public <T> T getObj(Class<T> clazz){
        return getObjByKey("obj",clazz);
    }

    @JsonIgnore
    public <T> T getObjByKey(String key,Class<T> clazz){
        processError();
        Object obj = this.get(key);
        return JSON.parseObject(JSON.toJSONString(obj),clazz);
    }

    @JsonIgnore
    public <T> List<T> getObjToList(Class<T> clazz) {
        return getObjByKeyToList("obj",clazz);
    }
    @JsonIgnore
    public <T> List<T> getObjByKeyToList(String key, Class<T> clazz)  {
        processError();
        Object obj = this.get(key);
        return JSON.parseArray(JSON.toJSONString(obj),clazz);
    }



    private void processError() {
        Object f = this.get("f");
        Object msg = this.get("f");
        if(null!=f && f instanceof Boolean){
            if(!(Boolean)f){
                String m = null==msg?"":msg.toString();
                throw new RuntimeException(m);
            }
        }
    }



}