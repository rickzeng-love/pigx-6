package com.pig4cloud.pigx.common.core.util.domain;

import java.io.Serializable;

/**
 * @author Administrator
 * 分页参数
 */
public class PageNoSize implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageNo = 0;
	private Integer pageSize = 10;

	public PageNoSize() {
	}

	public PageNoSize(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
