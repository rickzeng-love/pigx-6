package com.pig4cloud.pigx.admin.controller;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pigx.admin.entity.BusClick;
import com.pig4cloud.pigx.admin.util.ExcelUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author gaoxiao
 * @description
 * @date 2020/3/27 11:24
 **/
@RestController
@RequestMapping("/excel")
@Api(value = "excel导入导出", tags = "excel导入导出", description = "excel导入导出")
public class ExcelControllerDemo  {

	///@Autowired
	///ExcelService excelService;

	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportExcel()  throws IOException {
		//HttpServletResponse response = getHttpResponse();
		HttpServletResponse response = null;

		//List<BusClick> resultList = excelService.getBusClick();
		List<BusClick> resultList = null;

		long t1 = System.currentTimeMillis();
		ExcelUtils.writeExcel(response, resultList, BusClick.class);
		long t2 = System.currentTimeMillis();
		System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	}

	@RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	public void readExcel(MultipartFile file){

		long t1 = System.currentTimeMillis();
		List<BusClick> list = ExcelUtils.readExcel("", BusClick.class, file);
		long t2 = System.currentTimeMillis();
		System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
		list.forEach(
				b -> System.out.println(JSON.toJSONString(b))
		);
	}
}