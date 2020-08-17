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

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.Etemployee;
import com.pig4cloud.pigx.admin.mapper.EtbgEducationMapper;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.EtbgEducation;
import com.pig4cloud.pigx.admin.service.EtbgEducationService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 教育经历
 *
 * @author gaoxiao
 * @date 2020-04-13 09:36:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etbgeducation" )
@Api(value = "etbgeducation", tags = "教育经历管理")
public class EtbgEducationController {

    private final  EtbgEducationService etbgEducationService;
    private final EtbgEducationMapper etbgEducationMapper;

    /**
     * 分页查询
     * @param page 分页对象
     * @param etbgEducation 教育经历
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getEtbgEducationPage(Page page, EtbgEducation etbgEducation) {
        return R.ok(etbgEducationService.page(page, Wrappers.query(etbgEducation)));
    }
	/**
	 * 查询所有
	 * @param etbgEducation
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgEducation" )
	public R getAllEtbgEducation(EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEducation.setCorpcode(corpcode);
		etbgEducation.setUserid(userid);
		return R.ok(etbgEducationService.list(Wrappers.query(etbgEducation)));
	}


    /**
     * 通过id查询教育经历
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(etbgEducationService.getById(id));
    }

    /**
     * 新增教育经历
     * @param etbgEducation 教育经历    @PreAuthorize("@pms.hasPermission('admin_etbgeducation_add')" )
     * @return R
     */
    @ApiOperation(value = "新增教育经历", notes = "新增教育经历")
    @SysLog("新增教育经历" )
    @PostMapping("/save")
    public R save(@RequestBody EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEducation.setCorpcode(corpcode);
		etbgEducation.setUserid(userid);
    	return R.ok(etbgEducationService.save(etbgEducation));
    }

    /**
     * 修改教育经历
     * @param etbgEducation 教育经历    @PreAuthorize("@pms.hasPermission('admin_etbgeducation_edit')" )
     * @return R
     */
    @ApiOperation(value = "修改教育经历", notes = "修改教育经历")
    @SysLog("修改教育经历" )
    @PostMapping("/updateById")
    public R updateById(@RequestBody EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgEducation> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgEducation.getId());
		return R.ok(etbgEducationService.update(etbgEducation,updateWrapper));
    }
	/**
	 * 修改教育经历
	 * @param etbgEducation 教育经历    @PreAuthorize("@pms.hasPermission('admin_etbgeducation_edit')" )
	 * @return R
	 */
	@ApiOperation(value = "删除教育经历", notes = "删除教育经历")
	@SysLog("删除教育经历" )
	@PostMapping("/invalidEtbgEducation")
	public R invalidEtbgEducation(@RequestBody EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		UpdateWrapper<EtbgEducation> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",etbgEducation.getId());
		etbgEducation.setIsdisabled(1);
		return R.ok(etbgEducationService.update(etbgEducation,updateWrapper));
	}
/*    *//**
     * 通过id删除教育经历
     * @param id id    @PreAuthorize("@pms.hasPermission('admin_etbgeducation_del')" )
     * @return R
     *//*
    @ApiOperation(value = "通过id删除教育经历", notes = "通过id删除教育经历")
    @SysLog("通过id删除教育经历" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(etbgEducationService.removeById(id));
    }
	*/
    /*
	 *  学历分布报表
	 */
	@ApiOperation(value = "学历分布报表", notes = "学历分布报表")
	@PostMapping("/getEtbgEducationBiliALL" )
	public R getEtbgEducationBiliALL(EtbgEducation etbgEducation){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etbgEducation.setCorpcode(corpcode);

		// 学历报表
		List<Map> resultMap1 =etbgEducationService.getEtbgEducationBiliALL(etbgEducation);
		Map map = null;
		Integer edutype = 1;
		long count1 = 0;
		long count2 = 0;
		long count3 = 0;
		long count4 = 0;
		long count5 = 0;
		long count6 = 0;
		long count7 = 0;
		long count8 = 0;
		long count9 = 0;
		long count10 = 0;
		long count11 = 0;
		long count12 = 0;
		long count13 = 0;
		if(resultMap1!=null){
			for(int i=0;i<resultMap1.size();i++){
				map = resultMap1.get(i);
				edutype = (Integer)map.get("edutype");
				if(edutype==223){
					count1 =  (Long)map.get("people");
				}
				if(edutype==224){
					count2 =  (Long)map.get("people");
				}
				if(edutype==225){
					count3 =  (Long)map.get("people");
				}
				if(edutype==226){
					count4 =  (Long)map.get("people");
				}
				if(edutype==227){
					count5 =  (Long)map.get("people");
				}
				if(edutype==228){
					count6 =  (Long)map.get("people");
				}
				if(edutype==229){
					count7 =  (Long)map.get("people");
				}
				if(edutype==230){
					count8 =  (Long)map.get("people");
				}
				if(edutype==231){
					count9 =  (Long)map.get("people");
				}
				if(edutype==232){
					count10 =  (Long)map.get("people");
				}
				if(edutype==233){
					count11 =  (Long)map.get("people");
				}
				if(edutype==234){
					count12 =  (Long)map.get("people");
				}
				if(edutype==235){
					count13 =  (Long)map.get("people");
				}

			}
		}

		long allcount =0;
		allcount = count1+count2+count3+count4+count5+count6+count7+count8+count9+count10+count11+count12+count13;
		long b1 = 0;
		long b2 = 0;
		long b3 = 0;
		long b4 = 0;
		long b5 = 0;
		long b6 = 0;
		b1 = count1;  //博士后
		b2 = count2;  //博士
		b3 = count3;  //硕士
		b4 = count4;  //本科
		b5 = count5+count6+count7+count8+count9+count10;  //本科以下
		b6 = count11+count12+count13;  //其他

		double bili1 = 0.0;  //
		double bili2 =  0.0;//
		double bili3 =  0.0; //
		double bili4 =  0.0; //
		double bili5 =  0.0; //
		double bili6 =  0.0; //
		if(allcount==0){

		}else{
			bili1 = new BigDecimal((float)b1/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili2 = new BigDecimal((float)b2/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili3 = new BigDecimal((float)b3/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili4 = new BigDecimal((float)b4/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili5 = new BigDecimal((float)b5/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			bili6 = new BigDecimal((float)b6/allcount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
		}

		Map map1 = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		Map map4 = new HashMap();
		Map map5 = new HashMap();
		Map map6 = new HashMap();
		Map map7 = new HashMap();
		map1.put("edutype","博士后");
		map1.put("count",b1);
		map1.put("bili",bili1);

		map2.put("edutype","博士");
		map2.put("count",b2);
		map2.put("bili",bili2);

		map3.put("edutype","硕士");
		map3.put("count",b3);
		map3.put("bili",bili3);

		map4.put("edutype","本科");
		map4.put("count",b4);
		map4.put("bili",bili4);

		map5.put("edutype","本科以下");
		map5.put("count",b5);
		map5.put("bili",bili5);

		map6.put("edutype","其他");
		map6.put("count",b6);
		map6.put("bili",bili6);


		List resultList = new ArrayList(6);
		resultList.add(0,map1);
		resultList.add(1,map2);
		resultList.add(2,map3);
		resultList.add(3,map4);
		resultList.add(4,map5);
		resultList.add(5,map6);
		/*Map resultMap = new HashMap();
		resultMap.put("1",map1);
		resultMap.put("2",map2);
		resultMap.put("3",map3);*/
		return R.ok(resultList);


	}
	/**
	 * 查询所有
	 * @param etbgEducation
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllEtbgEducationBySql" )
	public R getAllEtbgEducationBySql(@RequestBody EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEducation.setCorpcode(corpcode);
		etbgEducation.setUserid(userid);
		return R.ok(etbgEducationService.list(Wrappers.query(etbgEducation)));
	}

	/**
	 * 查询所有
	 * @param etbgEducation
	 * @return
	 */
	@ApiOperation(value = "根据EID查询学历", notes = "根据EID查询学历")
	@PostMapping("/getEtbgEducationBySql" )
	public R getEtbgEducationBySql(@RequestBody EtbgEducation etbgEducation) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer userid = pigxUser.getId();
		etbgEducation.setCorpcode(corpcode);
		Integer eid = etbgEducation.getEid();
		if(StringUtils.isEmpty(eid)){
			return R.ok("EID不能为空！");
		}
		return R.ok(etbgEducationMapper.listEtbgEducationBySql(etbgEducation));
	}
	/**
	 * 分员工档案-教育经历
	 * @param page 分页对象
	 * @return
	 */
	@ApiOperation(value = "员工档案-教育经历", notes = "员工档案-教育经历")
	@PostMapping("/getEtbgEducationAllBySql" )
	public R getEtbgEducationAllBySql(Page page,Etemployee etemployee) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		etemployee.setCorpcode(corpcode);
		IPage resultpage = etbgEducationMapper.listEtbgEducationAllBySql(page,etemployee);
		return R.ok(resultpage);
	}

}
