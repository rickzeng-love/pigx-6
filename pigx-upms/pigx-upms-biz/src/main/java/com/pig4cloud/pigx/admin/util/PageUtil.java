package com.pig4cloud.pigx.admin.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

//直接传一个page对象

/**
 *PageHelper.startPage(PageHolder.get().getPageNo(),PageHolder.get().getPageSize());
 */
public class PageUtil {

    public static IPage getIpage(List list){
        //用PageInfo对结果进行包装
        PageInfo page = new PageInfo(list);
        IPage p = new Page();
        p.setCurrent(page.getPageNum());
        p.setPages(page.getPages());
        p.setRecords(page.getList());
        p.setSize(p.getSize());
        p.setTotal(page.getTotal());
        return p;
    }

}
