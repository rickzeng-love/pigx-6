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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.EtemployeeMapper;
import com.pig4cloud.pigx.admin.mapper.OtcdPositionMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.DateUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 职务
 *
 * @author gaoxiao
 * @date 2020-04-07 13:22:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/otcdposition" )
@Api(value = "otcdposition", tags = "职务管理")
public class OtcdPositionController {

    private final  OtcdPositionService otcdPositionService;
    private  final OtcdPositionMapper otcdPositionMapper;
	private  final EtemployeeService etemployeeService;
	private final EtemployeeMapper etemployeeMapper;
	private final OtcdPositionAllService otcdPositionAllService;
	private final EtstaffAllService etstaffAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param otcdPosition 职务
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getOtcdPositionPage(Page page, OtcdPosition otcdPosition) {
        return R.ok(otcdPositionService.page(page, Wrappers.query(otcdPosition)));
    }

	/**
	 * 职务分页查询
	 * @param page 分页对象
	 * @param otcdPosition 职务
	 * @return
	 */
	@ApiOperation(value = "职务分页查询", notes = "职务分页查询")
	@PostMapping("/getOtcdPositionPageBySql" )
	public R getOtcdPositionPageBySql(Page page, OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);
		return R.ok(otcdPositionMapper.listOtcdPosition(page,otcdPosition));
	}

	/**
	 * 合并职务
	 * @return R
	 */
	@ApiOperation(value = "合并职务", notes = "合并职务")
	@PostMapping("/combineOtcdPosition" )
	@Transactional
	public R combineOtcdPosition(@RequestBody Map map){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		map.put("corpcode",corpcode);
		Integer id =Integer.parseInt(map.get("id").toString());
		Integer newId = Integer.parseInt(map.get("newId").toString());
		if(id==newId){
			return R.failed("新职务不能是自己");
		}

		//判断是否有人属于此职务
		QueryWrapper<Etemployee> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("position",id).eq("corpcode",corpcode);
		List<Etemployee> etemployees = etemployeeService.list(queryWrapper);
		List<EtstaffAll> etstaffAlls=new ArrayList<>();
		if((!etemployees.isEmpty())&&etemployees.size()>0){// 职务下存在人员
			for (int i=0;i<etemployees.size();i++) {
				etemployees.get(i).setPosition(newId);
				EtstaffAll etstaffAll=new EtstaffAll();
				BeanUtils.copyProperties(etemployees.get(i),etstaffAll);
				etstaffAlls.add(etstaffAll);
			}
		}
		etstaffAllService.updateBatchById(etstaffAlls);
		etemployeeService.updateBatchById(etemployees);

		//被合并职务
		OtcdPosition oldOtcdPosition = otcdPositionService.getById(id);
		//合并到的职务
		OtcdPosition newOtcdPosition = otcdPositionService.getById(newId);
		OtcdPositionAll otcdPositionAll=new OtcdPositionAll();

		BeanUtils.copyProperties(newOtcdPosition,otcdPositionAll);
		otcdPositionAll.setCompidOld(oldOtcdPosition.getCompid());
		otcdPositionAll.setTitleOld(oldOtcdPosition.getTitle());
		otcdPositionAll.setDescriptionOld(oldOtcdPosition.getDescription());
		otcdPositionAll.setCompetenciesOld(oldOtcdPosition.getCompetencies());
		otcdPositionAll.setIsdisabled(0);
		otcdPositionAllService.save(otcdPositionAll);

		otcdPositionService.removeById(id);

		return R.ok("数据已修改！");
	}


	/**
	 * 职务在职员工
	 *
	 * @param otcdPosition 职务
	 * @return
	 */
	@ApiOperation(value = "职务在职员工", notes = "职务在职员工")
	@PostMapping("/getEtempoyeeByPosition" )
	public R getEtempoyeeByPosition(OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);
		return R.ok(otcdPositionMapper.listEtempoyeeByPosition(otcdPosition));
	}
	/**
	 * 查询所有
	 * @param otcdPosition 职级
	 * @return
	 */
	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/getAllOtcdPosition" )
	public R getAllOtcdPosition(OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);
		//otcdPosition.setIsdisabled(0);
		return R.ok(otcdPositionService.list(Wrappers.query(otcdPosition)));
	}

    /**
     * 通过id查询职务
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(otcdPositionService.getById(id));
    }

    /**
     * 新增职务
     * @param otcdPosition 职务
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdposition_add')" )
     */
    @ApiOperation(value = "新增职务", notes = "新增职务")
    @SysLog("新增职务" )
    @PostMapping("/save")
    public R save(@RequestBody OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);

		QueryWrapper<OtcdPosition> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdPosition.getTitle());
		List jobtypelist = otcdPositionService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职务名称已存在！");
		}
		QueryWrapper<OtcdPosition> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdPosition.getCode());
		List jobtypelist2 = otcdPositionService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职务编码已存在！");
		}
		//变动类型 1：新增，2：修改，3：删除
		OtcdPositionAll otcdPositionAll = new OtcdPositionAll();
		BeanUtils.copyProperties(otcdPosition,otcdPositionAll);
		otcdPositionAll.setType(1);
		otcdPositionAllService.save(otcdPositionAll);
        return R.ok(otcdPositionService.save(otcdPosition));
    }

    /**
     * 修改职务
     * @param otcdPosition 职务
     * @return R
	 *     @PreAuthorize("@pms.hasPermission('admin_otcdposition_edit')" )
     */
    @ApiOperation(value = "修改职务", notes = "修改职务")
    @SysLog("修改职务" )
    @PutMapping("/updateById")
    public R updateById(@RequestBody OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);

		QueryWrapper<OtcdPosition> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("title",otcdPosition.getTitle());
		queryWrapper.ne("id",otcdPosition.getId());
		List jobtypelist = otcdPositionService.list(queryWrapper);
		if(jobtypelist.size()>0){
			R.ok("职务名称已存在！");
		}
		QueryWrapper<OtcdPosition> queryWrapper2 = new QueryWrapper<>();
		queryWrapper2.eq("corpcode",corpcode);
		queryWrapper2.eq("code",otcdPosition.getCode());
		queryWrapper2.ne("id",otcdPosition.getId());
		List jobtypelist2 = otcdPositionService.list(queryWrapper2);
		if(jobtypelist2.size()>0){
			R.ok("职务编码已存在！");
		}

		OtcdPositionAll otcdPositionAll = new OtcdPositionAll();
		BeanUtils.copyProperties(otcdPosition,otcdPositionAll);
		//原来的职务信息
		OtcdPosition otcdPosition1 = otcdPositionService.getById(otcdPosition.getId());
		if(otcdPosition1!=null){
			otcdPositionAll.setCompetenciesOld(otcdPosition1.getCompetencies());
			otcdPositionAll.setCompidOld(otcdPosition1.getCompid());
			otcdPositionAll.setDescriptionOld(otcdPosition1.getDescription());
			otcdPositionAll.setTitleOld(otcdPosition1.getTitle());
		}
		otcdPositionAll.setType(2);
		otcdPositionAllService.save(otcdPositionAll);
		UpdateWrapper<OtcdPosition> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdPosition.getId());
        return R.ok(otcdPositionService.update(otcdPosition,updateWrapper));
    }

 /*   *//**
     * 通过id删除职务
     * @param // id id
     * @return R
     *//*
    @ApiOperation(value = "通过id删除职务", notes = "通过id删除职务")
    @SysLog("通过id删除职务" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_otcdposition_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(otcdPositionService.removeById(id));
    }
*/
	/*
	 * PC端职务管理在职、离职统计
	 * @param otcdPosition 职务
	 * @return
	 */
	@ApiOperation(value = "职务管理在职、离职统计", notes = "职务管理在职、离职统计")
	@PostMapping("/getZaiZhiAndLizhiPersonByPosition" )
	public R getZaiZhiAndLizhiPersonByPosition(OtcdPosition otcdPosition) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		otcdPosition.setCorpcode(corpcode);

		long zaizhicount = 0;//在职员工
		long lizhizhicount = 0;//离职
		Map empZaiZhi = otcdPositionMapper.listZaiZhiPersonByPosition(otcdPosition);
		if(empZaiZhi!=null){
			zaizhicount = (Long) empZaiZhi.get("person");
		}
		Map empLiZhi = otcdPositionMapper.listLizhiPersonByPosition(otcdPosition);
		if(empLiZhi!=null){
			lizhizhicount = (Long) empLiZhi.get("person");
		}

		Map resultMap1 = new HashMap();
		Map resultMap2 = new HashMap();
		resultMap1.put("title","在职员工");
		resultMap1.put("people",zaizhicount);
		resultMap2.put("title","离职员工");
		resultMap2.put("people",lizhizhicount);
		List resultList = new ArrayList(2);
		resultList.add(0,resultMap1);
		resultList.add(1,resultMap2);
		return R.ok(resultList);
	}

	/**
	 * 失效职务
	 * @return R
	 */
	@ApiOperation(value = "失效职务", notes = "失效职务")
	@PostMapping("/invalidById" )
	public R invalidById(@RequestBody OtcdPosition otcdPosition){
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Integer id = otcdPosition.getId();

		QueryWrapper<Etemployee> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		queryWrapper.eq("position",id);
		List emplist = etemployeeService.list(queryWrapper);
		if(emplist.size()>0){
			return R.ok(null,"此职务被使用，请先修改员工职务！");
		}
		otcdPosition.setIsdisabled(1);
		UpdateWrapper<OtcdPosition> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("corpcode",corpcode);
		updateWrapper.eq("id",otcdPosition.getId());
		otcdPositionService.update(otcdPosition,updateWrapper);


		return R.ok("数据已修改！");
	}

}
