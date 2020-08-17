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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.Otjob;

import java.util.List;
import java.util.Map;

/**
 * 岗位信息
 *
 * @author gaoxiao
 * @date 2020-04-07 16:29:28
 */
public interface OtjobService extends IService<Otjob> {
	/**
	 * 1.存在特殊字符，请逐个双击后查询
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobBySpecialChar(Otjob Otjob);
	/**
	 * 2.岗位代码已存在
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByExistsJobID(Otjob Otjob);
	/**
	 * 3.上级岗位必须存在且有效，或者生效日期必须晚于上级岗位的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByExistsEnableJobID(Otjob Otjob);
	/**
	 * 4.所属公司必须存在且有效,生效日期晚于所属公司的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByExistsEnableCompID(Otjob Otjob);
	/**
	 * 5.所属部门必须存在且有效,生效日期晚于所属部门的创建日期！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByExistsEnableDepID(Otjob Otjob);
	/**
	 * 6.上级岗位必须是所属公司下面的岗位！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtJobmentByAdminJobID(Otjob Otjob);
	/**
	 * 7.岗位所属部门必须是所属公司下面的部门!
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtJobByDepartJobID(Otjob Otjob);
	/**
	 * 8.失效的岗位不能做变更!
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtJobByIsDisabled(Otjob Otjob);
	/**
	 * 9.信息没有发生变化!
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtJobByNoChange(Otjob Otjob);
	/**
	 * 10.岗位失效时请先处理其下属岗位!
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtJobByDueJobID(Otjob Otjob);
	/**
	 * 11.岗位失效时请先处理岗位上在职员工!
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByDueEmployee(Otjob Otjob);
	/**
	 * 12.岗位失效时请先处理兼职对信息！
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobByDisableJobID(Otjob Otjob);
	/**
	 * 根据corpcode获取岗位列表带人数
	 *
	 * @return List<Map>
	 */
	public List<Map> getOtjobsByCorpcodeWithPeople(Otjob Otjob);

}
