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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.AtshiftDetails;
import com.pig4cloud.pigx.admin.entity.AvwShiftDay;
import com.pig4cloud.pigx.admin.mapper.AtplanRangeMapper;
import com.pig4cloud.pigx.admin.util.PageUtilForList;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.AtplanRange;
import com.pig4cloud.pigx.admin.service.AtplanRangeService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 日结果汇总
 *
 * @author gaoxiao
 * @date 2020-06-22 11:40:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/atplanrange" )
@Api(value = "atplanrange", tags = "日结果汇总管理")
public class AtplanRangeController {

    private final  AtplanRangeService atplanRangeService;
    private final AtplanRangeMapper atplanRangeMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param atplanRange 日结果汇总
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAtplanRangePage(Page page, AtplanRange atplanRange) {
        return R.ok(atplanRangeService.page(page, Wrappers.query(atplanRange)));
    }


  /*  *//**
     * 通过id查询日结果汇总
     * @param term id
     * @return R
     *//*
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{term}" )
    public R getById(@PathVariable("term" ) LocalDateTime term) {
        return R.ok(atplanRangeService.getById(term));
    }
*/
    /**
     * 新增日结果汇总
     * @param atplanRange 日结果汇总
     * @return R
     */
    @ApiOperation(value = "新增日结果汇总", notes = "新增日结果汇总")
    @SysLog("新增日结果汇总" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_atplanrange_add')" )
    public R save(@RequestBody AtplanRange atplanRange) {
        return R.ok(atplanRangeService.save(atplanRange));
    }

/*    *//**
     * 修改日结果汇总
     * @param atplanRange 日结果汇总
     * @return R
     *//*
    @ApiOperation(value = "修改日结果汇总", notes = "修改日结果汇总")
    @SysLog("修改日结果汇总" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_atplanrange_edit')" )
    public R updateById(@RequestBody AtplanRange atplanRange) {
        return R.ok(atplanRangeService.updateById(atplanRange));
    }*/

  /*  *//**
     * 通过id删除日结果汇总
     * @param term id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除日结果汇总", notes = "通过id删除日结果汇总")
    @SysLog("通过id删除日结果汇总" )
    @DeleteMapping("/{term}" )
    @PreAuthorize("@pms.hasPermission('admin_atplanrange_del')" )
    public R removeById(@PathVariable LocalDateTime term) {
        return R.ok(atplanRangeService.removeById(term));
    }
*/

	/**
	 * 分页查询
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getAtplanRangeByEid" )
	public R getAtplanRangeByEid() {
		AtplanRange atplanRange = new AtplanRange();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		atplanRange.setEid(eid);
		atplanRange.setCorpcode(pigxUser.getCorpcode());
		List<AvwShiftDay> list = atplanRangeMapper.listAtShiftDayByEid(atplanRange);


		AvwShiftDay avwShiftDay = null;
		Double EOMN = null;
		Double LIMN = null;
		Double OT1 = null;
		Double OT2 = null;
		Double OT3 = null;
		Double SicL = null;
		Double PerL = null;
		Double AnnL = null;
		Double ComL = null;
		Double MarL = null;
		Double PrPL = null;
		Double MatL = null;
		Double PatL = null;
		String begindate = null;
		String enddate = null;
		//正常
		double zc = 0.0;
		//请假
		double qj = 0.0;
		//缺卡
		int qk = 0;
		//迟到
		int cd = 0;
		//早退
		int zt = 0;
		double jb  = 0.0;
		String flag ="";  //正常
		List<AvwShiftDay> resultList = new ArrayList<>(list.size());
		for(int i=0;i<list.size();i++){
			avwShiftDay = list.get(i);
			//早退
			EOMN = avwShiftDay.getEomn();
			//迟到
			LIMN = avwShiftDay.getLimn();
			//加班
			OT1 = avwShiftDay.getOt1();
			OT2 = avwShiftDay.getOt2();
			OT3 = avwShiftDay.getOt3();
			//请假
			SicL = avwShiftDay.getSicl();
			PerL = avwShiftDay.getPerl();
			AnnL = avwShiftDay.getAnnl();
			ComL = avwShiftDay.getComl();
			MarL = avwShiftDay.getMarl();
			PrPL = avwShiftDay.getPrpl();
			MatL = avwShiftDay.getMatl();
			PatL = avwShiftDay.getPatl();
			//缺勤
			begindate = avwShiftDay.getCardbegintime1();
			enddate = avwShiftDay.getCardendtime1();
			// 1：迟到 2早退 3：加班 4：请假 5：缺卡(上午) 6：缺卡(下午) 0 正常
			 if(!StringUtils.isEmpty(LIMN)){
			 	flag = flag+",1";
			 	cd = cd + 1;
			 }else if(!StringUtils.isEmpty(EOMN)){
				flag = flag+",2";
				zt = zt + 1;
			} else  if(!StringUtils.isEmpty(OT1) || !StringUtils.isEmpty(OT2) || !StringUtils.isEmpty(OT3)){
				flag = flag+",3";
				if(!StringUtils.isEmpty(OT1)){
					jb = jb +OT1;
				}
				 if(!StringUtils.isEmpty(OT2)){
					 jb = jb +OT2;
				 }
				 if(!StringUtils.isEmpty(OT3)){
					 jb = jb +OT3;
				 }
			} else if(!StringUtils.isEmpty(SicL) || !StringUtils.isEmpty(PerL) || !StringUtils.isEmpty(AnnL) || !StringUtils.isEmpty(ComL) || !StringUtils.isEmpty(MarL) || !StringUtils.isEmpty(PrPL) || !StringUtils.isEmpty(MatL) || !StringUtils.isEmpty(PatL)){
				flag = flag+",4";
				 if(!StringUtils.isEmpty(SicL) ){
				 	qj = qj + SicL;
				 }
				 if(!StringUtils.isEmpty(PerL)){
					 qj = qj + PerL;
				 }
				 if(!StringUtils.isEmpty(AnnL)){
					 qj = qj + AnnL;
				 }
				 if(!StringUtils.isEmpty(ComL)){
					 qj = qj + ComL;
				 }
				 if(!StringUtils.isEmpty(MarL)){
					 qj = qj + MarL;
				 }
				 if(!StringUtils.isEmpty(PrPL)){
					 qj = qj + PrPL;
				 }
				 if(!StringUtils.isEmpty(MatL)){
					 qj = qj + MatL;
				 }
				 if(!StringUtils.isEmpty(PatL)){
					 qj = qj + PatL;
				 }
			} else if(!StringUtils.isEmpty(begindate)){
				flag = flag+",5";
				qk = qk + 1;
			} else if(!StringUtils.isEmpty(enddate)){
				flag = flag+",6";
				 qk = qk + 1;
			}else{
			 	zc = zc + 1;
			 	if("".equals(flag)){
			 		flag = "0";
				}else{
					flag = "0,"+flag;
				}

			 }
			avwShiftDay.setFlag(flag);
			resultList.add(i,avwShiftDay);
		}
		Map statistics = new HashMap();
		statistics.put("zc",zc);
		statistics.put("qj",qj);
		statistics.put("qk",qk);
		statistics.put("cd",cd);
		statistics.put("zt",zt);
		statistics.put("jb",jb);
		Map resultMap =  new HashMap();
		resultMap.put("statistics",statistics);
		resultMap.put("shiftDayList",resultList);
		return R.ok(resultMap);
	}

	/**
	 *手机端管理员考勤状态分页查询
	 * @return
	 */
	@ApiOperation(value = "手机端管理员考勤状态分页查询", notes = "手机端管理员考勤状态分页查询")
	@PostMapping("/getAtShiftDayByEidForStatistics" )
	public R getAtShiftDayByEidForStatistics(Page page) {
		AtplanRange atplanRange = new AtplanRange();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		atplanRange.setCorpcode(pigxUser.getCorpcode());
		//早退
		Map map = new HashMap();
		map.put("corpcode",pigxUser.getCorpcode());
		map.put("eomn","true");
		IPage<Map> listpageEOMN = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map);
		//迟到
		Map map2 = new HashMap();
		map2.put("corpcode",pigxUser.getCorpcode());
		map2.put("limn","true");
		IPage<Map> listpageLIMN = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map2);
		//缺卡
		Map map3 = new HashMap();
		map3.put("corpcode",pigxUser.getCorpcode());
		map3.put("cardbegintime1","true");
		IPage<Map> listpageQK = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map3);

		Map resultMap = new HashMap();
		resultMap.put("listpageEOMN",listpageEOMN);
		resultMap.put("listpageLIMN",listpageLIMN);
		resultMap.put("listpageQK",listpageQK);
		return R.ok(resultMap);
	}

	/**
	 *手机端管理员考勤迟到分页查询
	 * @return
	 */
	@ApiOperation(value = "手机端管理员考勤迟到分页查询", notes = "手机端管理员考勤迟到分页查询")
	@PostMapping("/getAtShiftDayForStatisticsCD" )
	public R getAtShiftDayForStatisticsCD(Page page) {
		AtplanRange atplanRange = new AtplanRange();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		atplanRange.setCorpcode(pigxUser.getCorpcode());

		//迟到
		Map map2 = new HashMap();
		map2.put("corpcode",pigxUser.getCorpcode());
		map2.put("limn","true");
		IPage<Map> listpageLIMN = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map2);


		return R.ok(listpageLIMN);
	}
	/**
	 *手机端管理员考勤早退分页查询
	 * @return
	 */
	@ApiOperation(value = "手机端管理员考勤早退分页查询", notes = "手机端管理员考勤早退分页查询")
	@PostMapping("/getAtShiftDayForStatisticsZT" )
	public R getAtShiftDayForStatisticsZT(Page page) {
		AtplanRange atplanRange = new AtplanRange();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		atplanRange.setCorpcode(pigxUser.getCorpcode());
		//早退
		Map map = new HashMap();
		map.put("corpcode",pigxUser.getCorpcode());
		map.put("eomn","true");
		IPage<Map> listpageEOMN = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map);

		return R.ok(listpageEOMN);
	}

	/**
	 *手机端管理员考勤状态缺卡分页查询
	 * @return
	 */
	@ApiOperation(value = "手机端管理员考勤状态缺卡分页查询", notes = "手机端管理员考勤状态缺卡分页查询")
	@PostMapping("/getAtShiftDayForStatisticsQK" )
	public R getAtShiftDayForStatisticsQK(Page page) {
		AtplanRange atplanRange = new AtplanRange();
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer eid = pigxUser.getEid();
		atplanRange.setCorpcode(pigxUser.getCorpcode());

		//缺卡
		Map map3 = new HashMap();
		map3.put("corpcode",pigxUser.getCorpcode());
		map3.put("cardbegintime1","true");
		IPage<Map> listpageQK = atplanRangeMapper.listAtShiftDayByEidForStatistics(page,map3);

		return R.ok(listpageQK);
	}

	/**
	 * 分页查询
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/getAtplanRangeStatisticsMore" )
	public R getAtplanRangeStatisticsMore(Page page,@RequestBody Map pmap) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Map map = new HashMap();
		pmap.put("corpcode",pigxUser.getCorpcode());
		List<AvwShiftDay> list = atplanRangeMapper.listAtShiftDayForStatistics2(pmap);


		AvwShiftDay avwShiftDay = null;
		Double EOMN = null;
		Double LIMN = null;
		Double OT1 = null;
		Double OT2 = null;
		Double OT3 = null;
		Double SicL = null;
		Double PerL = null;
		Double AnnL = null;
		Double ComL = null;
		Double MarL = null;
		Double PrPL = null;
		Double MatL = null;
		Double PatL = null;
		String begindate = null;
		String enddate = null;
		//正常
		double zc = 0.0;
		int zc1 = 0;
		//请假
		double qj = 0.0;
		int qj1 = 0;
		//缺卡
		int qk = 0;
		//迟到
		int cd = 0;
		//早退
		int zt = 0;
		double jb  = 0.0;
		int yc = 0;
		String flag = "0";  //正常
		String flag2 = "";
		List<AvwShiftDay> resultList = new ArrayList(list.size());
		for(int i=0;i<list.size();i++){
			avwShiftDay = list.get(i);
			//早退
			EOMN = avwShiftDay.getEomn();
			//迟到
			LIMN = avwShiftDay.getLimn();
			//加班
			OT1 = avwShiftDay.getOt1();
			OT2 = avwShiftDay.getOt2();
			OT3 = avwShiftDay.getOt3();
			//请假
			SicL = avwShiftDay.getSicl();
			PerL = avwShiftDay.getPerl();
			AnnL = avwShiftDay.getAnnl();
			ComL = avwShiftDay.getComl();
			MarL = avwShiftDay.getMarl();
			PrPL = avwShiftDay.getPrpl();
			MatL = avwShiftDay.getMatl();
			PatL = avwShiftDay.getPatl();
			//缺勤
			begindate = avwShiftDay.getCardbegintime1();
			enddate = avwShiftDay.getCardendtime1();
			//flag 1：迟到 2早退 3：加班 4：请假 5：缺卡(上午) 6：缺卡(下午) 0 正常
			//flag2 0 正常 2 异常 3 请假
			if(!StringUtils.isEmpty(LIMN) || !StringUtils.isEmpty(EOMN) ){
				if(!StringUtils.isEmpty(LIMN) ){
					flag2 = flag2+",1";
				}
				if(!!StringUtils.isEmpty(EOMN) ){
					flag2 = flag2+",2";
				}
				flag = "2";
				yc = yc + 1;
			}else  if(!StringUtils.isEmpty(OT1) || !StringUtils.isEmpty(OT2) || !StringUtils.isEmpty(OT3)){
				flag2 = flag2+",3";

			}  else if(!StringUtils.isEmpty(SicL) || !StringUtils.isEmpty(PerL) || !StringUtils.isEmpty(AnnL) || !StringUtils.isEmpty(ComL) || !StringUtils.isEmpty(MarL) || !StringUtils.isEmpty(PrPL) || !StringUtils.isEmpty(MatL) || !StringUtils.isEmpty(PatL)){
				flag2 = flag2+",4";
				flag = "3";
				qj1 = qj1 + 1;


			} else if(!StringUtils.isEmpty(begindate) || !StringUtils.isEmpty(enddate)) {
				if(!StringUtils.isEmpty(begindate)) {
					flag2 = flag2+",5";
				}
				if(!StringUtils.isEmpty(enddate)) {
					flag2 = flag2+",6";
				}
				flag = "2";
				qk = qk + 1;
				yc = yc + 1;
			}else{
				zc = zc + 1;
				zc1 = zc1 +1;
				flag = "0";
				if("".equals(flag2)){
					flag2 = "0";
				}else{
					flag2 = "0,"+flag2;
				}
			}
			avwShiftDay.setFlag(flag);
			avwShiftDay.setFlag2(flag2);
			resultList.add(i,avwShiftDay);
		}
		List<AvwShiftDay> list1 = new ArrayList(zc1);
		List<AvwShiftDay> list2 = new ArrayList(yc);
		List<AvwShiftDay> list3 = new ArrayList(qj1);
		int g1 = 0;
		int g2 =0;
		int g3 = 0;
		for(int i=0;i<list.size();i++) {
			avwShiftDay = list.get(i);
			if("0".equals(avwShiftDay.getFlag())){
				list1.add(g1,avwShiftDay);
				g1++;
			}
			if("2".equals(avwShiftDay.getFlag())){
				list2.add(g2,avwShiftDay);
				g2++;
			}
			if("3".equals(avwShiftDay.getFlag())){
				list3.add(g3,avwShiftDay);
				g3++;
			}
		}
		List resultList1 = null;
		if("0".equals(pmap.get("type").toString())){
			resultList1 = PageUtilForList.startPage(list1,(int) page.getCurrent(),(int)page.getSize());
		}
		if("1".equals(pmap.get("type").toString())){
			resultList1 = PageUtilForList.startPage(list2,(int) page.getCurrent(),(int)page.getSize());
		}
		if("2".equals(pmap.get("type").toString())){
			resultList1 = PageUtilForList.startPage(list3,(int) page.getCurrent(),(int)page.getSize());
		}


		return R.ok(resultList1);
	}

}
