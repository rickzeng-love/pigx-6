package com.pig4cloud.pigx.admin.client;


import com.pig4cloud.pigx.common.core.util.Rfe;
import com.pig4cloud.pigx.common.core.util.TaskActionEntity;
import com.pig4cloud.pigx.common.core.util.domain.BscInstTaskOpt;
import com.pig4cloud.pigx.common.core.util.domain.BscProTaskSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value= "pigx-oa-platform",fallback = SearchMytaskClientFallback.class)
public interface MytaskClient {

	String Fe_path = "/api/process/";


	/**
	 * 待办任务列表
	 * @param instid
	 * @return
	 */
	@PostMapping(value= Fe_path + "tasklist")
	Rfe tasklist(@RequestParam("instid") String instid);


	/**
	 * 定义列表
	 * @param dingyiid
	 * @param diyikey
	 * @return
	 */
	@PostMapping(value= Fe_path + "dingyilist")
	Rfe dingyilist(@RequestParam("dingyiid") String dingyiid,
					 @RequestParam("diyikey") String diyikey);


	/**
	 * 实例列表
	 * @param dingyiid
	 * @param instid
	 * @param ywid
	 * @return
	 */
	@PostMapping(value= Fe_path + "shililist")
	Rfe shililist(@RequestParam("dingyiid") String dingyiid,
				   @RequestParam("instid") String instid,
				   @RequestParam("ywid") String ywid);



	/**
	 * 启动流程
	 * @param key
	 * @param refid
	 * @param des
	 * @param params
	 * @return
	 */
	@PostMapping(value= Fe_path + "startProcess")
	Rfe startProcess(@RequestParam("key") String key,
							@RequestParam("refid") String refid,
							@RequestParam("des") String des,
							@RequestBody Map<String, Object> params);

	/**
	 * 启动流程 --根据流程id
	 * @param id
	 * @param refid
	 * @param des
	 * @param params
	 * @return
	 */
	@PostMapping(value= Fe_path + "startProcessbyid")
	Rfe startProcessbyid(@RequestParam("id") String id,
					 @RequestParam("refid") String refid,
					 @RequestParam("des") String des,
					 @RequestBody Map<String, Object> params);


	/**
	 * 获取我的代办
	 * 用时必须先 设置PageHolder
	 * PageHolder.setNo(Integer.parseInt(page.getCurrent() + ""),Integer.parseInt(page.getSize() + ""));
	 * @param model
	 * @return
	 */
	@PostMapping(value= Fe_path + "searchMytask")
	Rfe searchMytask(@RequestBody BscProTaskSearch model);



	/**
	 * 查询我的代办分页
	 * @param entity
	 * @return
	 */
	@PostMapping("searchMytaskPage")
	Rfe searchMytaskPage(BscProTaskSearch entity);

	/**
	 * 获取一个任务
	 * @param entity
	 * @return
	 */
	@PostMapping(value= Fe_path + "getOneTask")
	Rfe getOneTask(@RequestBody BscProTaskSearch entity);


	/**
	 * 完成我的任务
	 * @param insTtask
	 * @return
	 */
	@PostMapping(value= Fe_path + "completeMyTask")
	Rfe completeMyTask(@RequestBody BscInstTaskOpt insTtask);

	/**
	 * 结束我的代办，不再开启新的代办
	 * @param insTtask
	 */
	@PostMapping(value= Fe_path + "finishMyTask")
	Rfe finishMyTask(@RequestBody BscInstTaskOpt insTtask);

	/**
	 * 结束整个流程
	 * @param insTtask
	 */
	@PostMapping(value= Fe_path + "finishMyProinst")
	Rfe finishMyProinst(@RequestBody BscInstTaskOpt insTtask, @RequestParam("delflag") boolean delflag);


	/**
	 * 更新全局变量
	 * @param taskEntity
	 */
	@PostMapping(value= Fe_path + "updateVariables")
	Rfe updateVariables(@RequestBody TaskActionEntity taskEntity);



}
