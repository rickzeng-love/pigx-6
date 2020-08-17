package com.pig4cloud.pigx.admin.service;

import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.core.util.TaskActionEntity;
import com.pig4cloud.pigx.common.core.util.domain.BscInstTaskOpt;
import com.pig4cloud.pigx.common.core.util.domain.DingyiEntity;
import com.pig4cloud.pigx.common.core.util.domain.InstEntity;
import com.pig4cloud.pigx.common.core.util.domain.TaskEntity;

import java.util.List;


public interface ProInstWorkService {

	R completeMyTask(BscInstTaskOpt task);

	R shilihua(TaskActionEntity taskEntity);

	List<DingyiEntity> dingyilist(String dingyiid, String diyikey);

	List<InstEntity> shililist(String dingyiid, String instid, String ywid);

	List<TaskEntity> tasklist(String instid);

	R updateVariables(TaskActionEntity taskEntity);


}
