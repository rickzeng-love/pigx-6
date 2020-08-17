package com.pig4cloud.pigx.admin.controller;

import com.pig4cloud.pigx.admin.entity.Excel;
import com.pig4cloud.pigx.admin.util.ExportExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author created by gaoxioa
 * @version v.0.1
 * @Description TODO
 * @date 2020/3/27
 * @备注
 **/
@Controller
public class ExcelController {

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/excel/exportBankCheckInfo",method = RequestMethod.GET)
	public void ExportBankCkeckInfo(HttpServletResponse response, HttpServletRequest request){
		//这里是笔者实际业务需求中需要得到时间间隔。可忽略
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		System.out.println("打印的起始日期为："+start+"，打印的结束日期为："+end);
		//得到所有要导出的数据
		List<Excel> orderlist =null;
		//定义导出的excel名字
		String excelName = "订单详情表";

		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		fieldMap.put("id","编号");
		fieldMap.put("link_man","姓名");
		fieldMap.put("amount_real","价格");
		fieldMap.put("date_add","日期");
		fieldMap.put("status_str","订单状态");
		fieldMap.put("mobie","收货电话");
		fieldMap.put("address","地址");
		fieldMap.put("detailValue","订单详情");

		//导出用户相关信息
		new ExportExcelUtils().export(excelName,orderlist,fieldMap,response);
	}






}

