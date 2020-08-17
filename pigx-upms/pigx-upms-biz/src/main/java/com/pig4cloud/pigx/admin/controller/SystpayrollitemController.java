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
//import com.pig4cloud.pigx.admin.api.dto.TreeFormula;
import com.pig4cloud.pigx.admin.api.dto.TreeOrg;
import com.pig4cloud.pigx.admin.api.vo.TreeUtil;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.mapper.SystpayrollitemMapper;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 薪资项
 *
 * @author gaoxiao
 * @date 2020-06-26 14:44:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/systpayrollitem" )
@Api(value = "systpayrollitem", tags = "薪资项管理")
public class SystpayrollitemController {

    private final  SystpayrollitemService systpayrollitemService;
    private final SystpayrollitemMapper systpayrollitemMapper;
    private final SystpaystditemService systpaystditemService;
    private final SystpaystditemCommonService systpaystditemCommonService;
    private final SystpayrollitemCommonService systpayrollitemCommonService;
	private final SystpayrollgroupService systpayrollgroupService;
	private final  SystpayrollitemAllService systpayrollitemAllService;
	private final  SystpaystditemAllService systpaystditemAllService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param systpayrollitem 薪资项
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSystpayrollitemPage(Page page, Systpayrollitem systpayrollitem) {
        return R.ok(systpayrollitemService.page(page, Wrappers.query(systpayrollitem)));
    }

	/**
	 * 根据薪资账套查询工资项
	 * @param systpayrollitem
	 * @return
	 */
	@ApiOperation(value = "根据薪资账套查询工资项", notes = "根据薪资账套查询工资项")
	@PostMapping("/getsystpayrollitemByGID" )
	public R getsystpayrollitemByGID( Systpayrollitem systpayrollitem) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		systpayrollitem.setCorpcode(corpcode);
		return R.ok(systpayrollitemMapper.listsystpayrollitemByGID(systpayrollitem));
	}
	/**
	 * 查询薪资项树状列表
	 * @return
	 */
	@ApiOperation(value = "薪资套选薪资项树状列表", notes = "薪资套选薪资项树状列表")
	@PostMapping("/getsystpayrollitemTree" )
	public R getsystpayrollitemTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		List list = systpayrollitemMapper.listsystpayrollitemTree(systpayrollitem);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("可配置项");
		return R.ok(TreeUtil.findChildren2(treeOrg,list));

	}

    /**
     * 通过id查询薪资项
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(systpayrollitemService.getById(id));
    }

   /* *//**
     * 新增薪资项     @PreAuthorize("@pms.hasPermission('admin_systpayrollitem_add')" )
     * @param systpayrollitem 薪资项
     * @return R
     *//*
    @ApiOperation(value = "新增薪资项", notes = "新增薪资项")
    @SysLog("新增薪资项" )
    @PostMapping("/save")
    public R save(@RequestBody Systpayrollitem systpayrollitem) {
    	// "id,stype,title"
    	String items = systpayrollitem.getItems();
    	String[] arrayitems = items.split("|");
    	String[] arrayitems2 = null;
    	String item = "";
    	String item2 = "";
    	String sid = "";
    	String stype = "";
    	Integer id = null;
    	Integer type  = null;
    	String title = "";

    	for(int i=0;i<arrayitems.length;i++){
			item = arrayitems[i];
			arrayitems2 = item.split(",");
			for(int g=0;g<arrayitems2.length;g++){
				id = Integer.parseInt(arrayitems2[0]);
				type =Integer.parseInt(arrayitems2[1]);

			}
		}
        return R.ok(systpayrollitemService.save(systpayrollitem));
    }
*/

	/**
	 * 新增薪资项     @PreAuthorize("@pms.hasPermission('admin_systpayrollitem_add')" )
	 * @param mapList 薪资项
	 * @return R
	 */
    @ApiOperation(value = "新增薪资项", notes = "新增薪资项")
    @SysLog("新增薪资项" )
    @PostMapping("/save")
	@Transactional
    public R save(@RequestBody List<Map> mapList) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
    	//前台传数组过来
    	Map map = null;
    	Object id = null;
    	Integer parentid = null;
    	Integer type  = null;
    	String title = "";
    	Integer xorder = null;
    	Integer gid = null;
    	Integer corpid = pigxUser.getCorpid();
		Systpayrollitem systpayrollitem  = null;
		Systpaystditem systpaystditem = null;
		SystpayrollitemCommon systpayrollitemCommon = null;
		SystpaystditemCommon systpaystditemCommon = null;
		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(!StringUtils.isEmpty(systpayrollgroup)){
			gid = systpayrollgroup.getId();
		}
    	for(int i=0;i<mapList.size();i++){
			map = mapList.get(i);
			parentid = Integer.parseInt(map.get("parentid").toString());
			type = Integer.parseInt(map.get("stype").toString());
			xorder = map.get("xorder")!=null ? Integer.parseInt(map.get("xorder").toString()):null;
			title = map.get("title").toString();
			id = map.get("id");
			Integer iid = Integer.parseInt(id.toString());
			//gid = Integer.parseInt(map.get("gid").toString());
			//if(!StringUtils.isEmpty(id)){
			//	continue;
			//}
			//基本薪资
			if(type==1){
				systpaystditemCommon =null;
				systpaystditemCommon = systpaystditemCommonService.getById(iid);
				systpaystditem = new Systpaystditem();
				systpaystditem.setType(1);
				systpaystditem.setTitle(title);
				systpaystditem.setCorpcode(corpcode);
				systpaystditem.setCorpid(pigxUser.getCorpid());
				systpaystditem.setGid(gid);
				systpaystditem.setIsdisabled(0);
				systpaystditem.setParentid(iid);
				systpaystditem.setXorder(systpaystditemCommon.getXorder());
				systpaystditem.setRemark(systpaystditemCommon.getRemark());
				UpdateWrapper<Systpaystditem> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("corpid",corpid);
				updateWrapper.eq("parentid",iid);
				systpaystditemService.saveOrUpdate(systpaystditem,updateWrapper);
				systpaystditem.setColname("item"+systpaystditem.getId());
				systpaystditemService.updateById(systpaystditem);
			}else if(type==7){
				//输入项
				systpayrollitemCommon = null;
				systpayrollitemCommon = systpayrollitemCommonService.getById(iid);
				systpayrollitem = new Systpayrollitem();
				systpayrollitem.setTitle(title);
				systpayrollitem.setType(7);
				systpayrollitem.setCorpcode(corpcode);
				systpayrollitem.setCorpid(pigxUser.getCorpid());
				systpayrollitem.setGid(gid);
				systpayrollitem.setIsdisabled(0);
				systpayrollitem.setParentid(iid);
				systpayrollitem.setXorder(xorder);
				systpayrollitem.setTablename("systpayrollitem");
				systpayrollitem.setIftype(1);
				systpayrollitem.setRemark(systpayrollitemCommon.getRemark());
				systpayrollitem.setXorder(systpayrollitemCommon.getXorder());
				UpdateWrapper<Systpayrollitem> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("corpcode",corpcode);
				updateWrapper.eq("parentid",iid);
				systpayrollitemService.saveOrUpdate(systpayrollitem,updateWrapper);
				systpayrollitem.setColname("item"+systpayrollitem.getId());
				systpayrollitemService.updateById(systpayrollitem);

			}else{
				//剩下的是输出项
				systpayrollitemCommon = null;
				systpayrollitemCommon = systpayrollitemCommonService.getById(iid);
				systpayrollitem = new Systpayrollitem();
				systpayrollitem.setTitle(title);
				systpayrollitem.setType(type);
				systpayrollitem.setCorpcode(corpcode);
				systpayrollitem.setCorpid(pigxUser.getCorpid());
				systpayrollitem.setGid(gid);
				systpayrollitem.setIsdisabled(0);
				systpayrollitem.setParentid(iid);
				systpayrollitem.setXorder(xorder);
				systpayrollitem.setTablename("systpayrollitem");
				systpayrollitem.setIftype(0);
				systpayrollitem.setRemark(systpayrollitemCommon.getRemark());
				systpayrollitem.setXorder(systpayrollitemCommon.getXorder());
				UpdateWrapper<Systpayrollitem> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("corpcode",corpcode);
				updateWrapper.eq("parentid",iid);
				systpayrollitemService.saveOrUpdate(systpayrollitem,updateWrapper);
				systpayrollitem.setColname("item"+systpayrollitem.getId());
				systpayrollitemService.updateById(systpayrollitem);
			}

		}
        return R.ok("保存成功");
    }

	/**
	 * 获取公式中薪资项树
	 * @return
	 */
	@ApiOperation(value = "获取公式中薪资项树", notes = "获取公式中薪资项树")
	@PostMapping("/getSystpayrollitemForFormulaTree" )
	public R getSystpayrollitemForFormulaTree() {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();

		Systpayrollitem systpayrollitem = new Systpayrollitem();
		systpayrollitem.setCorpcode(corpcode);
		List formulaList = systpayrollitemMapper.listSystpayrollitemForFormula(systpayrollitem);
		TreeOrg treeOrg  = new TreeOrg();
		treeOrg.setExpand(false);
		treeOrg.setId("0");
		treeOrg.setTitle("薪资项");
		return R.ok(TreeUtil.findChildren2(treeOrg,formulaList));

	}

 /*   *//**
     * 修改薪资项
     * @return R
     *//*
    @ApiOperation(value = "修改薪资项", notes = "修改薪资项")
    @SysLog("修改薪资项" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_systpayrollitem_edit')" )
    public R updateById(@RequestBody Systpayrollitem systpayrollitem) {
        return R.ok(systpayrollitemService.updateById(systpayrollitem));
    }

    */
	/*
     * 通过id删除薪资项
     * @param id id     @PreAuthorize("@pms.hasPermission('admin_systpayrollitem_del')" )
     * @return R
     */
    @ApiOperation(value = "通过id删除薪资项", notes = "通过id删除薪资项")
    @SysLog("通过id删除薪资项" )
    @PostMapping("reomoveById")
    public R removeById(@RequestBody Map map) {
		PigxUser pigxUser = SecurityUtils.getUser();
		String corpcode = pigxUser.getCorpcode();
		//前台传数组过来
		Integer id = Integer.parseInt(map.get("id").toString());
		Integer type  = Integer.parseInt(map.get("stype").toString());
		Integer gid = null;

		QueryWrapper<Systpayrollgroup> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("corpcode",corpcode);
		Systpayrollgroup systpayrollgroup = systpayrollgroupService.getOne(queryWrapper);
		if(!StringUtils.isEmpty(systpayrollgroup)){
			gid = systpayrollgroup.getId();
		}
		//基本薪资
		if(type==1){
			QueryWrapper<Systpaystditem> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("id",id);
			queryWrapper1.eq("corpcode",corpcode);
			queryWrapper1.eq("gid",gid);

			Systpaystditem systpaystditem1  = systpaystditemService.getOne(queryWrapper1);
			SystpaystditemAll systpaystditemAll = new SystpaystditemAll();
			BeanUtils.copyProperties(systpaystditem1,systpaystditemAll);
			systpaystditemAllService.save(systpaystditemAll);

			UpdateWrapper<Systpaystditem> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id",id);
			updateWrapper.eq("corpcode",corpcode);
			updateWrapper.eq("gid",gid);
			systpaystditemService.remove(updateWrapper);

		}else{
			QueryWrapper<Systpayrollitem> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("id",id);
			queryWrapper1.eq("corpcode",corpcode);
			queryWrapper1.eq("gid",gid);

			Systpayrollitem systpayrollitem1  = systpayrollitemService.getOne(queryWrapper1);
			SystpayrollitemAll systpayrollitemAll = new SystpayrollitemAll();
			BeanUtils.copyProperties(systpayrollitem1,systpayrollitemAll);

			systpayrollitemAllService.save(systpayrollitemAll);

			UpdateWrapper<Systpayrollitem> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id",id);
			updateWrapper.eq("corpcode",corpcode);
			updateWrapper.eq("gid",gid);
			systpayrollitemService.remove(updateWrapper);
		}


		return R.ok("删除成功！");
    }

}
