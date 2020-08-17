package com.pig4cloud.pigx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.pig4cloud.pigx.admin.client.MytaskClient;
import com.pig4cloud.pigx.admin.service.ProInstWorkService;
import com.pig4cloud.pigx.common.core.util.JsonUtils;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.core.util.Rfe;
import com.pig4cloud.pigx.common.core.util.TaskActionEntity;
import com.pig4cloud.pigx.common.core.util.domain.BscInstTaskOpt;
import com.pig4cloud.pigx.common.core.util.domain.DingyiEntity;
import com.pig4cloud.pigx.common.core.util.domain.InstEntity;
import com.pig4cloud.pigx.common.core.util.domain.TaskEntity;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProInstWorkServiceImpl implements ProInstWorkService {

	@Resource
	private MytaskClient mytaskClient;



	@Override
	public R completeMyTask(BscInstTaskOpt task){
		return R.ok(mytaskClient.completeMyTask(task).get("obj"));
	}


	@Override
	public R shilihua(TaskActionEntity taskEntity) {

		//初始化参数
		Map<String,Object> mm = null;

		//前台设定了参数
		if(taskEntity.getQuanjubianliang()!=null){

			//获取参数
			Map<String, Object> map = JSON.parseObject(taskEntity.getQuanjubianliang().toString(), Map.class);

			//若为空则默认新建
			mm = map.size()>0?map: Maps.newHashMap();

		}else{

			mm = Maps.newHashMap();
		}

		System.err.println(SecurityUtils.getUser().getId()+"SecurityUtils.getUser().getId()");

		mm.put("startuser", SecurityUtils.getUser().getId());//发起人为当前登陆人   设置当前登陆人

		mm.put("corpcode", SecurityUtils.getUser().getCorpcode());//流程全局变量 放入当前登陆人所属的用户组编码

		Rfe r = null;

		if(StringUtils.isNotBlank(taskEntity.getDingyiid())){

			r = mytaskClient.startProcessbyid(taskEntity.getDingyiid(),
					taskEntity.getYwid(),
					taskEntity.getShiliname(),mm);

		}else if(StringUtils.isNotBlank(taskEntity.getDingyikey())){

			r = mytaskClient.startProcess(taskEntity.getDingyikey(),
					taskEntity.getYwid(),
					taskEntity.getShiliname(),mm);

		}else{

			return R.failed("必须指定唯一流程定义id或不重复的流程定义key");

		}

		System.err.println(r+"???");

		String instid = r.get("obj").toString();

		System.out.println("instid-------------------"+instid);
		System.out.println(JsonUtils.toJSONString(r));

		return R.ok(r);
	}

	@Override
	public List<DingyiEntity> dingyilist(String dingyiid, String diyikey) {
		return (List<DingyiEntity>)mytaskClient.dingyilist(dingyiid,diyikey).get("obj");
	}

	@Override
	public List<InstEntity> shililist(String dingyiid, String instid, String ywid) {
		return (List<InstEntity>)mytaskClient.shililist(dingyiid, instid, ywid).get("obj");
	}

	@Override
	public List<TaskEntity> tasklist(String instid) {
		return (List<TaskEntity>)mytaskClient.tasklist(instid).get("obj");
	}


	@Override
	public R updateVariables(TaskActionEntity taskEntity){
		return R.ok(mytaskClient.updateVariables(taskEntity).get("obj"));
	}


}
