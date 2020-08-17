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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.admin.entity.EtcdPaystditem;
import com.pig4cloud.pigx.admin.entity.SystpaystditemCommon;
import com.pig4cloud.pigx.admin.service.EtcdPaystditemService;
import com.pig4cloud.pigx.admin.service.SystpaystditemCommonService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 薪资项类型
 *
 * @author XP
 * @date 2020-06-09 16:04:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/etcdpaystditem")
@Api(value = "etcdpaystditem", tags = "薪资项类型管理")
public class EtcdPaystditemController {

	private final EtcdPaystditemService etcdPaystditemService;
	private final SystpaystditemCommonService systpaystditemCommonService;

	/**
	 * 列表查询
	 * @return
	 */
	@ApiOperation(value = "薪资项列表查询", notes = "薪资项列表查询")
	@PostMapping("/getEtcdPaystditemList")
	public R getEtcdPaystditemList() {
		EtcdPaystditem etcdPaystditem = null;
		SystpaystditemCommon systpaystditemCommon = null;
		List<SystpaystditemCommon> sysList = null;
		List<EtcdPaystditem> list = etcdPaystditemService.list();
		List result = new ArrayList(list.size());
		Map map  = null;
		EtcdPaystditem etcdPaystditem1 = null;
		String typetitle = "";
		if (list.size() > 0){
			for (int i = 0; i < list.size(); i++){
				etcdPaystditem1 = list.get(i);
				int itemid = etcdPaystditem1.getId();
				typetitle = etcdPaystditem1.getTitle();
				systpaystditemCommon = new SystpaystditemCommon();
				systpaystditemCommon.setType(itemid);
				PigxUser pigxUser = SecurityUtils.getUser();
				String corpcode = pigxUser.getCorpcode();
				systpaystditemCommon.setCorpcode(corpcode);
				sysList = systpaystditemCommonService.list(Wrappers.query(systpaystditemCommon));
				map = new HashMap();
				map.put("type",itemid);
				map.put("titletype",typetitle);
				map.put("item",sysList);
				result.add(i,map);

			}
		}

		return R.ok(result);
	}

	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param etcdPaystditem 薪资项类型
	 * @return
	 */
/*	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getEtcdPaystditemPage(Page page, EtcdPaystditem etcdPaystditem) {
		return R.ok(etcdPaystditemService.page(page, Wrappers.query(etcdPaystditem)));
	}*/


	/**
	 * 通过id查询薪资项类型
	 *
	 * @param id id
	 * @return R
	 */
/*	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(etcdPaystditemService.getById(id));
	}*/

	/**
	 * 新增薪资项类型
	 *
	 * @param etcdPaystditem 薪资项类型
	 * @return R
	 */
	/*@ApiOperation(value = "新增薪资项类型", notes = "新增薪资项类型")
	@SysLog("新增薪资项类型")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_etcdpaystditem_add')")
	public R save(@RequestBody EtcdPaystditem etcdPaystditem) {
		return R.ok(etcdPaystditemService.save(etcdPaystditem));
	}*/

	/**
	 * 修改薪资项类型
	 *
	 * @param etcdPaystditem 薪资项类型
	 * @return R
	 */
/*	@ApiOperation(value = "修改薪资项类型", notes = "修改薪资项类型")
	@SysLog("修改薪资项类型")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_etcdpaystditem_edit')")
	public R updateById(@RequestBody EtcdPaystditem etcdPaystditem) {
		return R.ok(etcdPaystditemService.updateById(etcdPaystditem));
	}*/

	/**
	 * 通过id删除薪资项类型
	 *
	 * @param id id
	 * @return R
	 */
/*	@ApiOperation(value = "通过id删除薪资项类型", notes = "通过id删除薪资项类型")
	@SysLog("通过id删除薪资项类型")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_etcdpaystditem_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(etcdPaystditemService.removeById(id));
	}*/

}
