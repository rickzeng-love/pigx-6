package com.pig4cloud.pigx.daemon.elastic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.daemon.elastic.entity.ExecutionLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务日志处理
 *
 * @author lishangbu
 * @date 2018/11/22
 */
@Mapper
public interface ExecutionLogMapper extends BaseMapper<ExecutionLog> {

}
