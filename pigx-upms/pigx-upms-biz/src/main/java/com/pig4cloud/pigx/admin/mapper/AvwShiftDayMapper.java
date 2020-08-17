/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtcardlostRegister;
import com.pig4cloud.pigx.admin.entity.AtshiftStatist;
import com.pig4cloud.pigx.admin.entity.AvwCardRecord;
import com.pig4cloud.pigx.admin.entity.AvwShiftDay;
import com.pig4cloud.pigx.common.core.util.R;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * VIEW
 *
 * @author gaoxiao
 * @date 2020-06-22 11:43:40
 */
@Mapper
public interface AvwShiftDayMapper extends BaseMapper<AvwShiftDay> {
	//刷卡导入
	public R aSPImportSQLHandLog(Map map);
	//批量考勤确认
	public R aSPAnalResuBatchConfirm(Map map);
	//考勤确认 根据人
	public R aSPAnalResuConfirm(Map map);
	//考勤取消
	public R aSPAnalResuCancel(Map map);
	//考勤分析
	public R aSPAnalysisSimple(Map map);
	//补卡一天
	public R aSPCardHandleAdd(Map map);

	//考勤初始化
	public R aSPAttendMonthInit(Map map);
	//考勤汇总
	public R aSPAttendMonthCalc(Map map);
	//考勤结算
	public R aSPAttendMonthSubmit(Map map);
	//考勤封账
	public R aSPAttendMonthClose(Map map);

	//年度考勤区间初始化
	public R aSPAttendPeriods(Map map);
	//轮班组排班
	public R aSPGroupTurnRun(Map map);
	//自动排班
	public R aSPShiftTurnRun(Map map);
	//添加到手动排班
	public R aSPShiftHandleAddEmp(Map map);
	//手动排班
	public R aSPShiftHandleTurnRun(Map map);




	//日汇总结果
	public List<Map> listAvwShiftDayPageBySql(Page page,@Param("query") AvwShiftDay avwShiftDay);
	//日统计结果
	public List<Map> listatShiftStatistByEid(AtshiftStatist atshiftStatist);
	//当日刷卡记录
	public List<Map> listaVWCardRecordByEid(AvwCardRecord avwCardRecord);
	//当日补卡登记
	public List<Map> listatCardLostRegisterByEid(AtcardlostRegister atcardlostRegister);
}
