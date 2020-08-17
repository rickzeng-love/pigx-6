package com.pig4cloud.pigx.admin.client;


import com.pig4cloud.pigx.common.core.util.Rfe;
import com.pig4cloud.pigx.common.core.util.TaskActionEntity;
import com.pig4cloud.pigx.common.core.util.domain.BscInstTaskOpt;
import com.pig4cloud.pigx.common.core.util.domain.BscProTaskSearch;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class SearchMytaskClientFallback implements MytaskClient {

	@Override
	public Rfe tasklist(String instid) {
		return Rfe.error();
	}

	@Override
	public Rfe dingyilist(String dingyiid, String diyikey) {
		return Rfe.error();
	}

	@Override
	public Rfe shililist(String dingyiid, String instid, String ywid) {
		return Rfe.error();
	}

	@Override
	public Rfe startProcess(String key, String businessid, String des, Map<String, Object> params) {
		return Rfe.error();
	}

	@Override
	public Rfe startProcessbyid(String id, String refid, String des, Map<String, Object> params) {
		return Rfe.error();
	}

	@Override
	public Rfe searchMytask(BscProTaskSearch model) {
		return Rfe.error();
	}

	@Override
	public Rfe searchMytaskPage(BscProTaskSearch entity) {
		return Rfe.error();
	}

	@Override
	public Rfe getOneTask(BscProTaskSearch entity) {
		return Rfe.error();
	}

	@Override
	public Rfe completeMyTask(BscInstTaskOpt insTtask) {
		return Rfe.error();
	}

	@Override
	public Rfe finishMyTask(BscInstTaskOpt insTtask) {
		return Rfe.error();
	}

	@Override
	public Rfe finishMyProinst(BscInstTaskOpt insTtask, boolean delflag) {
		return Rfe.error();
	}

	@Override
	public Rfe updateVariables(TaskActionEntity taskEntity) {
		return Rfe.error();
	}
}
