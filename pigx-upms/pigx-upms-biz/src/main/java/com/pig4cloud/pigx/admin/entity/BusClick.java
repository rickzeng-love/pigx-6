package com.pig4cloud.pigx.admin.entity;

import com.pig4cloud.pigx.admin.entity.ExcelColumn;
import lombok.Data;

/**
 * @author gaoxiao
 * @description
 * @date 2020/3/27 9:39
 **/
@Data
public class BusClick {

	@ExcelColumn(value = "cityCode", col = 1)
	private String cityCode;

	@ExcelColumn(value = "markId", col = 2)
	private String markId;

	@ExcelColumn(value = "toaluv", col = 3)
	private String toaluv;

	@ExcelColumn(value = "date", col = 4)
	private String date;

	@ExcelColumn(value = "clientVer", col = 5)
	private String clientVer;
}
//省略getter and setter 方法