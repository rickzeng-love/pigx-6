package com.pig4cloud.pigx.daemon.elastic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pigx.daemon.elastic.entity.StatusTraceLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务轨迹处理
 *
 * @author lishangbu
 * @date 2018/11/22
 */
@Mapper
public interface StatusTraceLogMapper extends BaseMapper<StatusTraceLog> {

}
