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

package com.pig4cloud.pigx.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtShiftWorkKey;
import com.pig4cloud.pigx.admin.entity.Atstatus;
import com.pig4cloud.pigx.admin.mapper.AtshiftWorkMapper;
import com.pig4cloud.pigx.admin.service.AtstatusService;
import com.pig4cloud.pigx.admin.util.ExportExcelUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtshiftWork;
import com.pig4cloud.pigx.admin.service.AtshiftWorkService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 员工排班
 *
 * @author GXS
 * @date 2020-05-27 16:53:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atshiftwork" )
@Api(value = "atshiftwork", tags = "员工排班管理")
public class AtshiftWorkController {

    private final  AtshiftWorkService atshiftWorkService;
    private final AtshiftWorkMapper atshiftWorkMapper;
    private final AtstatusService atstatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atshiftWork 员工排班
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtshiftWorkPage(Page page, AtshiftWork atshiftWork) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftWork.setCorpcode(corpCode);
        return R.ok(atshiftWorkService.page(page, Wrappers.query(atshiftWork)));
    }

	/**
	 * 查询所有
	 * @param atshiftWork
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllAtshiftWork" )
	public R getAllAtshiftWork(AtshiftWork atshiftWork){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftWork.setCorpcode(corpCode);
		return R.ok(atshiftWorkService.list(Wrappers.query(atshiftWork)));
	}

	/**
	 * 查询员工排班的员工班次信息列表
	 * @param page
	 * @param atshiftWork
	 * @return
	 */
	@ApiOperation(value = "查询员工排班的员工班次信息列表", notes = "查询员工排班的员工班次信息列表")
	@PostMapping("/getAtshiftWorkList" )
	public R getAtshiftWorkList(Page page,@RequestBody(required=false)  AtshiftWork atshiftWork){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftWork)){
			atshiftWork = new AtshiftWork();
		}
		atshiftWork.setCorpcode(corpCode);
		return R.ok(atshiftWorkService.getAtshiftWorkList(page,atshiftWork));
	}


	/**
	 * 查询员工排班的员工班次信息列表
	 * @param page
	 * @param atshiftWork
	 * @return
	 */
	@ApiOperation(value = "手动排班选择员工", notes = "手动排班选择员工")
	@PostMapping("/getAvwStatusForAtshiftWork" )
	public R getAvwStatusForAtshiftWork(Page page,@RequestBody(required=false)  AtshiftWork atshiftWork){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		if(StringUtils.isEmpty(atshiftWork)){
			atshiftWork = new AtshiftWork();
		}
		atshiftWork.setCorpcode(corpCode);
		return R.ok(atshiftWorkService.getAtshiftWorkList(page,atshiftWork));
	}

//    /**
//     * 通过id查询员工排班
//     * @param term id
//     * @return R
//     */
//    @ApiOperation(value = "通过id查询", notes = "通过id查询")
//    @GetMapping("/{term}" )
//    public R getById(@PathVariable("term" ) LocalDateTime term) {
//        return R.ok(atshiftWorkService.getById(term));
//    }

	/**
	 * 通过id查询员工排班
	 * @param term id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{term}/{EID}" )
	public R getById(@PathVariable("term" ) String term,@PathVariable("EID" ) Integer EID) {
		AtShiftWorkKey atShiftWorkKey=new AtShiftWorkKey(term,EID);
		return R.ok(atshiftWorkService.getById(atShiftWorkKey));
	}

	/**
	 * 导出员工排班记录列表
	 * @param response
	 * @param request
	 */
	@ApiOperation(value = "导出员工排班记录列表", notes = "导出员工排班记录列表")
	@RequestMapping(value = "/exportAtshiftWorkList",method = RequestMethod.GET)
	public void exportAtshiftWorkList(HttpServletResponse response, HttpServletRequest request){
		//获取查询条件
		String name=request.getParameter("name");
		LocalDateTime term=LocalDateTime.parse(request.getParameter("term"));
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		//得到所有要导出的数据
		List<AtshiftWork> dataList=atshiftWorkMapper.ExportAtshiftWork(name,corpcode,term);
		//定义导出的excel名字
		String excelName = "员工排班列表";

		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		//fieldMap.put("id","编号");
		//fieldMap.put("Term","月份");
		fieldMap.put("Name","姓名");
		fieldMap.put("Badge","工号");
		fieldMap.put("S1Name","第1天");
		fieldMap.put("S2Name","第2天");
		fieldMap.put("S3Name","第3天");
		fieldMap.put("S4Name","第4天");
		fieldMap.put("S5Name","第5天");
		fieldMap.put("S6Name","第6天");
		fieldMap.put("S7Name","第7天");
		fieldMap.put("S8Name","第8天");
		fieldMap.put("S9Name","第9天");
		fieldMap.put("S10Name","第10天");
		fieldMap.put("S11Name","第11天");
		fieldMap.put("S12Name","第12天");
		fieldMap.put("S13Name","第13天");
		fieldMap.put("S14Name","第14天");
		fieldMap.put("S15Name","第15天");
		fieldMap.put("S16Name","第16天");
		fieldMap.put("S17Name","第17天");
		fieldMap.put("S18Name","第18天");
		fieldMap.put("S19Name","第19天");
		fieldMap.put("S20Name","第20天");
		fieldMap.put("S21Name","第21天");
		fieldMap.put("S22Name","第22天");
		fieldMap.put("S23Name","第23天");
		fieldMap.put("S24Name","第24天");
		fieldMap.put("S25Name","第25天");
		fieldMap.put("S26Name","第26天");
		fieldMap.put("S27Name","第27天");
		fieldMap.put("S28Name","第28天");
		fieldMap.put("S29Name","第29天");
		fieldMap.put("S30Name","第30天");
		fieldMap.put("S31Name","第31天");

		//导出相关信息
		new ExportExcelUtils().export(excelName,dataList,fieldMap,response);
	}

	/**
	 * 按月份统计班次的每日排班统计
	 * @param term
	 * @return
	 */
	@ApiOperation(value = "按月份统计班次的每日排班统计", notes = "按月份统计班次的每日排班统计")
	@GetMapping("/getAtShiftTypeTotal/{term}" )
	public R getAtShiftTypeTotal(@PathVariable("term") String term){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//不需要合计信息，使用以下代码
		//return R.ok(atshiftWorkMapper.ListAtshiftWorkTotal(corpcode,term));

		//需要合计信息，使用以下代码
		List<Map> listMap=atshiftWorkMapper.ListAtshiftWorkTotal(corpcode,term);
		Integer S1Count=0,S2Count=0,S3Count=0,S4Count=0,S5Count=0,S6Count=0,S7Count=0;
		Integer S8Count=0,S9Count=0,S10Count=0,S11Count=0,S12Count=0,S13Count=0,S14Count=0;
		Integer S15Count=0,S16Count=0,S17Count=0,S18Count=0,S19Count=0,S20Count=0,S21Count=0;
		Integer S22Count=0,S23Count=0,S24Count=0,S25Count=0,S26Count=0,S27Count=0,S28Count=0;
		Integer S29Count=0,S30Count=0,S31Count=0;

		if(listMap.size()>0)
		{
			for(Map m:listMap)
			{
				S1Count=S1Count+Integer.parseInt((String)m.get("S1"));
				S2Count=S2Count+Integer.parseInt((String)m.get("S2"));
				S3Count=S3Count+Integer.parseInt((String)m.get("S3"));
				S4Count=S4Count+Integer.parseInt((String)m.get("S4"));
				S5Count=S5Count+Integer.parseInt((String)m.get("S5"));
				S6Count=S6Count+Integer.parseInt((String)m.get("S6"));
				S7Count=S7Count+Integer.parseInt((String)m.get("S7"));
				S8Count=S8Count+Integer.parseInt((String)m.get("S8"));
				S9Count=S9Count+Integer.parseInt((String)m.get("S9"));
				S10Count=S10Count+Integer.parseInt((String)m.get("S10"));

				S11Count=S11Count+Integer.parseInt((String)m.get("S11"));
				S12Count=S12Count+Integer.parseInt((String)m.get("S12"));
				S13Count=S13Count+Integer.parseInt((String)m.get("S13"));
				S14Count=S14Count+Integer.parseInt((String)m.get("S14"));
				S15Count=S15Count+Integer.parseInt((String)m.get("S15"));
				S16Count=S16Count+Integer.parseInt((String)m.get("S16"));
				S17Count=S17Count+Integer.parseInt((String)m.get("S17"));
				S18Count=S18Count+Integer.parseInt((String)m.get("S18"));
				S19Count=S19Count+Integer.parseInt((String)m.get("S19"));
				S20Count=S20Count+Integer.parseInt((String)m.get("S20"));

				S21Count=S21Count+Integer.parseInt((String)m.get("S21"));
				S22Count=S22Count+Integer.parseInt((String)m.get("S22"));
				S23Count=S23Count+Integer.parseInt((String)m.get("S23"));
				S24Count=S24Count+Integer.parseInt((String)m.get("S24"));
				S25Count=S25Count+Integer.parseInt((String)m.get("S25"));
				S26Count=S26Count+Integer.parseInt((String)m.get("S26"));
				S27Count=S27Count+Integer.parseInt((String)m.get("S27"));
				S28Count=S28Count+Integer.parseInt((String)m.get("S28"));
				S29Count=S29Count+Integer.parseInt((String)m.get("S29"));
				S30Count=S30Count+Integer.parseInt((String)m.get("S30"));
				S31Count=S31Count+Integer.parseInt((String)m.get("S31"));
			}
		}
		Map<String,String> mapTotal=new HashMap<String,String>();
		mapTotal.put("shift","合计");
		mapTotal.put("Title","合计");
		mapTotal.put("S1",S1Count.toString());
		mapTotal.put("S2",S2Count.toString());
		mapTotal.put("S3",S3Count.toString());
		mapTotal.put("S4",S4Count.toString());
		mapTotal.put("S5",S5Count.toString());
		mapTotal.put("S6",S6Count.toString());
		mapTotal.put("S7",S7Count.toString());
		mapTotal.put("S8",S8Count.toString());
		mapTotal.put("S9",S9Count.toString());
		mapTotal.put("S10",S10Count.toString());

		mapTotal.put("S11",S11Count.toString());
		mapTotal.put("S12",S12Count.toString());
		mapTotal.put("S13",S13Count.toString());
		mapTotal.put("S14",S14Count.toString());
		mapTotal.put("S15",S15Count.toString());
		mapTotal.put("S16",S16Count.toString());
		mapTotal.put("S17",S17Count.toString());
		mapTotal.put("S18",S18Count.toString());
		mapTotal.put("S19",S19Count.toString());
		mapTotal.put("S20",S20Count.toString());

		mapTotal.put("S21",S21Count.toString());
		mapTotal.put("S22",S22Count.toString());
		mapTotal.put("S23",S23Count.toString());
		mapTotal.put("S24",S24Count.toString());
		mapTotal.put("S25",S25Count.toString());
		mapTotal.put("S26",S26Count.toString());
		mapTotal.put("S27",S27Count.toString());
		mapTotal.put("S28",S28Count.toString());
		mapTotal.put("S29",S29Count.toString());
		mapTotal.put("S30",S30Count.toString());
		mapTotal.put("S31",S31Count.toString());
		listMap.add(mapTotal);

		return R.ok(listMap);
	}

	/**
	 * 导出按月份统计班次的每日排班统计
	 * @param response
	 * @param request
	 */
	@ApiOperation(value = "导出员工排班记录列表", notes = "导出员工排班记录列表")
	@RequestMapping(value = "/exportAtShiftTypeTotal",method = RequestMethod.GET)
	public void exportAtShiftTypeTotal(HttpServletResponse response, HttpServletRequest request){
		//获取查询条件
		String term=request.getParameter("term");
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		//得到所有要导出的数据
		List<Map> dataList=atshiftWorkMapper.ListAtshiftWorkTotal(corpcode,term);
		Integer S1Count=0,S2Count=0,S3Count=0,S4Count=0,S5Count=0,S6Count=0,S7Count=0;
		Integer S8Count=0,S9Count=0,S10Count=0,S11Count=0,S12Count=0,S13Count=0,S14Count=0;
		Integer S15Count=0,S16Count=0,S17Count=0,S18Count=0,S19Count=0,S20Count=0,S21Count=0;
		Integer S22Count=0,S23Count=0,S24Count=0,S25Count=0,S26Count=0,S27Count=0,S28Count=0;
		Integer S29Count=0,S30Count=0,S31Count=0;

		//获取合计信息
		if(dataList.size()>0)
		{
			for(Map m:dataList)
			{
				S1Count=S1Count+Integer.parseInt((String)m.get("S1"));
				S2Count=S2Count+Integer.parseInt((String)m.get("S2"));
				S3Count=S3Count+Integer.parseInt((String)m.get("S3"));
				S4Count=S4Count+Integer.parseInt((String)m.get("S4"));
				S5Count=S5Count+Integer.parseInt((String)m.get("S5"));
				S6Count=S6Count+Integer.parseInt((String)m.get("S6"));
				S7Count=S7Count+Integer.parseInt((String)m.get("S7"));
				S8Count=S8Count+Integer.parseInt((String)m.get("S8"));
				S9Count=S9Count+Integer.parseInt((String)m.get("S9"));
				S10Count=S10Count+Integer.parseInt((String)m.get("S10"));

				S11Count=S11Count+Integer.parseInt((String)m.get("S11"));
				S12Count=S12Count+Integer.parseInt((String)m.get("S12"));
				S13Count=S13Count+Integer.parseInt((String)m.get("S13"));
				S14Count=S14Count+Integer.parseInt((String)m.get("S14"));
				S15Count=S15Count+Integer.parseInt((String)m.get("S15"));
				S16Count=S16Count+Integer.parseInt((String)m.get("S16"));
				S17Count=S17Count+Integer.parseInt((String)m.get("S17"));
				S18Count=S18Count+Integer.parseInt((String)m.get("S18"));
				S19Count=S19Count+Integer.parseInt((String)m.get("S19"));
				S20Count=S20Count+Integer.parseInt((String)m.get("S20"));

				S21Count=S21Count+Integer.parseInt((String)m.get("S21"));
				S22Count=S22Count+Integer.parseInt((String)m.get("S22"));
				S23Count=S23Count+Integer.parseInt((String)m.get("S23"));
				S24Count=S24Count+Integer.parseInt((String)m.get("S24"));
				S25Count=S25Count+Integer.parseInt((String)m.get("S25"));
				S26Count=S26Count+Integer.parseInt((String)m.get("S26"));
				S27Count=S27Count+Integer.parseInt((String)m.get("S27"));
				S28Count=S28Count+Integer.parseInt((String)m.get("S28"));
				S29Count=S29Count+Integer.parseInt((String)m.get("S29"));
				S30Count=S30Count+Integer.parseInt((String)m.get("S30"));
				S31Count=S31Count+Integer.parseInt((String)m.get("S31"));
			}
		}
		Map<String,String> mapTotal=new HashMap<String,String>();
		mapTotal.put("shift","合计");
		mapTotal.put("Title","合计");
		mapTotal.put("S1",S1Count.toString());
		mapTotal.put("S2",S2Count.toString());
		mapTotal.put("S3",S3Count.toString());
		mapTotal.put("S4",S4Count.toString());
		mapTotal.put("S5",S5Count.toString());
		mapTotal.put("S6",S6Count.toString());
		mapTotal.put("S7",S7Count.toString());
		mapTotal.put("S8",S8Count.toString());
		mapTotal.put("S9",S9Count.toString());
		mapTotal.put("S10",S10Count.toString());

		mapTotal.put("S11",S11Count.toString());
		mapTotal.put("S12",S12Count.toString());
		mapTotal.put("S13",S13Count.toString());
		mapTotal.put("S14",S14Count.toString());
		mapTotal.put("S15",S15Count.toString());
		mapTotal.put("S16",S16Count.toString());
		mapTotal.put("S17",S17Count.toString());
		mapTotal.put("S18",S18Count.toString());
		mapTotal.put("S19",S19Count.toString());
		mapTotal.put("S20",S20Count.toString());

		mapTotal.put("S21",S21Count.toString());
		mapTotal.put("S22",S22Count.toString());
		mapTotal.put("S23",S23Count.toString());
		mapTotal.put("S24",S24Count.toString());
		mapTotal.put("S25",S25Count.toString());
		mapTotal.put("S26",S26Count.toString());
		mapTotal.put("S27",S27Count.toString());
		mapTotal.put("S28",S28Count.toString());
		mapTotal.put("S29",S29Count.toString());
		mapTotal.put("S30",S30Count.toString());
		mapTotal.put("S31",S31Count.toString());
		dataList.add(mapTotal);

		//定义导出的excel名字
		String excelName = "员工排班列表";

		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		fieldMap.put("Title","班次人数");
		fieldMap.put("S1","1");
		fieldMap.put("S2","2");
		fieldMap.put("S3","3");
		fieldMap.put("S4","4");
		fieldMap.put("S5","5");
		fieldMap.put("S6","6");
		fieldMap.put("S7","7");
		fieldMap.put("S8","8");
		fieldMap.put("S9","9");
		fieldMap.put("S10","10");
		fieldMap.put("S11","11");
		fieldMap.put("S12","12");
		fieldMap.put("S13","13");
		fieldMap.put("S14","14");
		fieldMap.put("S15","15");
		fieldMap.put("S16","16");
		fieldMap.put("S17","17");
		fieldMap.put("S18","18");
		fieldMap.put("S19","19");
		fieldMap.put("S20","20");
		fieldMap.put("S21","21");
		fieldMap.put("S22","22");
		fieldMap.put("S23","23");
		fieldMap.put("S24","24");
		fieldMap.put("S25","25");
		fieldMap.put("S26","26");
		fieldMap.put("S27","27");
		fieldMap.put("S28","28");
		fieldMap.put("S29","29");
		fieldMap.put("S30","30");
		fieldMap.put("S31","31");

		//导出相关信息
		new ExportExcelUtils().export(excelName,dataList,fieldMap,response);
	}

    /**
     * 新增员工排班
     * @param atshiftWork 员工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftwork_add')" )
     * @return R
     */
    @ApiOperation(value = "新增员工排班", notes = "新增员工排班")
    @SysLog("新增员工排班" )
    @PostMapping
    public R save(@RequestBody AtshiftWork atshiftWork) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		atshiftWork.setCorpcode(corpCode);
        return R.ok(atshiftWorkService.save(atshiftWork));
    }

/*
     * 修改员工排班
     * @param atshiftWork 员工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftwork_edit')" )
     * @return R
     */

    @ApiOperation(value = "修改员工排班", notes = "修改员工排班")
    @SysLog("修改员工排班" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody AtshiftWork atshiftWork) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		UpdateWrapper<AtshiftWork> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpCode);
		updateWrapper.eq("term",atshiftWork.getTerm());
		updateWrapper.eq("eid",atshiftWork.getEid());
        return R.ok(atshiftWorkService.update(atshiftWork,updateWrapper));
    }


    //保存功能代码 未编写完....... 20200528
	/**
	 * 修改员工排班(批量)
	 * @param map
	 * @param results
	 * @return
	 */
	@ApiOperation(value = "修改员工排班(批量)", notes = "修改员工排班(批量)")
	@SysLog("修改员工排班" )
	@PutMapping("/updateById2")
	@Transactional
    public R updateById2(@RequestBody Map map, BindingResult results){
    	List<AtshiftWork> atshiftWorkList= JSONArray.parseArray((String)map.get("group"),AtshiftWork.class);
		AtshiftWork atworkNew=null;
		AtshiftWork atworkOld=null;
		AtShiftWorkKey akey=null;
		Atstatus astatus=null;
		Integer agentMode=0;
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpCode = pigxUser.getCorpcode();
		List<Map> listMap=null;

    	if(atshiftWorkList.size()>0)
		{
			for(int i=0;i<atshiftWorkList.size();i++) {
				//获取当前前端界面传输的数据
				atworkNew = atshiftWorkList.get(i);
				//获取员工排班在数据库中最新的数据
				akey=new AtShiftWorkKey(atworkNew.getTerm(),atworkNew.getEid());
				atworkOld = atshiftWorkService.getById(akey);
				//agentMode=atworkOld.get

				//获取员工对应的考勤账套
				astatus=atstatusService.getById(atworkNew.getEid());
				if(astatus!=null)
				{
					agentMode=astatus.getAid();
				}

				//判断手工排班登记表中是否已存在员工记录，不允许添加
				listMap=atshiftWorkMapper.ListShiftHandleAddEmpCheckSub(atworkOld.getEid(),atworkOld.getTerm(),corpCode);
				if(listMap.size()>0){
					//1300200
					return R.ok(null,"此人员已经添加至手动排班窗体中,不允许添加!");
				}

				//判断当前是否牌考勤分析中，分析中不允许，不允许添加
				listMap=atshiftWorkMapper.ListAttendLockStatusCheck(agentMode);
				if(listMap.size()>0){
					//1300062
					return R.ok(null,"考勤分析中,为避免数据不一致，请稍后再做处理！");
				}

				//数据写入到手工排班登记表 atShiftSupply_Work

				//写入成功后，获取手工排班登记表ID，执行存储过程：aSP_ShiftHandleTurnRunEasy({Term},{[EID]},{UserID}

			}
		}
		return R.ok();
	}


//    /**
//     * 通过id删除员工排班
//     * @param term id
//     * @return R
//     */
//    @ApiOperation(value = "通过id删除员工排班", notes = "通过id删除员工排班")
//    @SysLog("通过id删除员工排班" )
//    @DeleteMapping("/{term}" )
//    @PreAuthorize("@pms.hasPermission('admin_atshiftwork_del')" )
//    public R removeById(@PathVariable LocalDateTime term) {
//    	return R.ok(atshiftWorkService.removeById(term));
//    }
/*

	*/
/**
	 * 通过Term-EID删除员工排班 @PreAuthorize("@pms.hasPermission('admin_atshiftwork_del')" )
	 * @param term
	 * @param EID
	 * @return
	 *//*

	@ApiOperation(value = "通过id删除员工排班", notes = "通过id删除员工排班")
	@SysLog("通过id删除员工排班" )
	@DeleteMapping("/{term}/{EID}" )
	public R removeById(@PathVariable("term" ) String term,@PathVariable("EID" ) Integer EID) {
		AtShiftWorkKey atShiftWorkKey=new AtShiftWorkKey(term,EID);
		return R.ok(atshiftWorkService.removeById(atShiftWorkKey));
	}
*/


}
