package com.pig4cloud.pigx.common.core.util.domain;



/**
 * @author Administrator
 * 分页参数holder
 */
public class PageHolder {
	public static ThreadLocal<PageNoSize> page = new ThreadLocal<PageNoSize>();

	public static void put(PageNoSize p) {
		page.set(p);
	}

	public static PageNoSize get() {
		return page.get();
	}

	public static void clear() {
		page.remove();
	}


	public static void setNo(Integer pageNo,Integer pageSize) {

		PageNoSize pns = new PageNoSize();
		pns.setPageNo(pageNo);
		pns.setPageSize(pageSize);
		PageHolder.put(pns);

	}

}
