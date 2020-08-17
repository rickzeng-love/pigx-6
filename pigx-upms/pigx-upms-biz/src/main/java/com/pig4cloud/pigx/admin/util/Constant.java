package com.pig4cloud.pigx.admin.util;

/**
 * @Description: 常量
 * @Author: gaoxiao
 * @Date: Created in  2020/03/28
 */
public class Constant {
    // 未通过格式校验的数据
    public static final String failListKey = "excelToDB:failListKey";
    // 已通过格式校验的数据
    public static final String successListKey = "excelToDB:successListKey";
    // 未通过格式校验的数据大小
    public static final String failSizeKey = "excelToDB:failSizeKey";
    // 已通过格式校验的数据大小
    public static final String succSizeKey = "excelToDB:succSizeKey";
    // 消息队列中未被消费的数据大小
    public static final String succSizeTempKey = "excelToDB:succSizeTempKey";
    // 导入数据库失败的数据大小
    public static final String failToDBKey = "excelToDB:failToDBKey";


    // redis中，发布者中所使用到的频道名称
    public static final String receiveSingle = "excelToDB:receiveSingle";
    public static final String receiveList = "excelToDB:receiveList";

    // redis中，消费者的方法名
    public static final String singleMethodName = "receiveSingle";
    public static final String listMethodName = "receiveList";


}
