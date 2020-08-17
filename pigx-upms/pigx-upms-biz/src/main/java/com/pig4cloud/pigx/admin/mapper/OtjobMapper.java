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

package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.dto.JobTreeOrg;
import com.pig4cloud.pigx.admin.entity.OtcdEmpgrade;
import com.pig4cloud.pigx.admin.entity.Otcompany;
import com.pig4cloud.pigx.admin.entity.Otjob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 岗位信息
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:28
 */
@Mapper
public interface OtjobMapper extends BaseMapper<Otjob> {
	/**
	 * 1.存在特殊字符，请逐个双击后查询
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobBySpecialChar(Otjob Otjob);
	/**
	 * 2.岗位代码已存在
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByExistsJobID(Otjob Otjob);
	/**
	 * 3.上级岗位必须存在且有效，或者生效日期必须晚于上级岗位的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByExistsEnableJobID(Otjob Otjob);
	/**
	 * 4.所属公司必须存在且有效,生效日期晚于所属公司的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByExistsEnableCompID(Otjob Otjob);
	/**
	 * 5.所属部门必须存在且有效,生效日期晚于所属部门的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByExistsEnableDepID(Otjob Otjob);
	/**
	 * 6.上级岗位必须是所属公司下面的岗位！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByAdminJobID(Otjob Otjob);
	/**
	 * 7.岗位所属部门必须是所属公司下面的部门!
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByDepartJobID(Otjob Otjob);
	/**
	 * 8.失效的岗位不能做变更!
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtJobByIsDisabled(Otjob Otjob);
	/**
	 * 9.信息没有发生变化!
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtJobByNoChange(Otjob Otjob);
	/**
	 * 10.岗位失效时请先处理其下属岗位!
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtJobByDueJobID(Otjob Otjob);
	/**
	 * 11.岗位失效时请先处理岗位上在职员工!
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByDueEmployee(Otjob Otjob);
	/**
	 * 12.岗位失效时请先处理兼职对信息！
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobByDisableJobID(Otjob Otjob);
	/**
	 * 根据corpcode获取岗位列表带人数
	 *
	 * @return List<Map>
	 */
	public List<Map> listOtjobsByCorpcodeWithPeople(Otjob Otjob);
	//pc端岗位列表
	public IPage<JobTreeOrg> listJobTreePC(Page page, @Param("query") Otjob otjob);
	//pc端岗位列表
	public IPage<JobTreeOrg> listJobTreePCSecond(Page page, @Param("query") Otjob otjob);
	//pc端岗位管理-详情接口1
	public Map listOtjobDetailByjobId(Otjob otjob);

	//通过depid获取岗位列表
	public IPage<Map> listOtjobListBydepId(Page page, @Param("query")  Otjob otjob);
	//通过depid获取岗位列表
	public IPage<Map> listOtjobListBydepId2(Page page, @Param("query")  Otjob otjob);
	//pc端岗位管理-详情接口2
	public Map listOtjobDetailByjobId2(Otjob otjob);
	//pc端岗位管理-详情接口3
	public List<Map> listOtjobDetailByjobId3(Otjob otjob);

	//通过adminid获取岗位列表
	public IPage<Map> listOtjobListByadminId(Page page, @Param("query") Otjob otjob);

}


