package com.pig4cloud.pigx.admin.api.vo;
import lombok.Data;

/**
 * @Description: 值对象
 * @Author: gaoxiao
 * @Date: Created in  2020/03/28
 */
@Data
public class ExcelModelVo {
    // 格式校验成功的数据大小
    private long succSize;
    // 格式校验失败的数据大小
    private long failSize;
    // 导入数据库失败的数据大小
    private long failToDBSize;
    // 导入数据库成功的数据大小；
    private long succToDBSize;


    public ExcelModelVo(long succSize, long failSize, long failToDBSize) {
        this.succSize = succSize;
        this.failSize = failSize;
        this.failToDBSize = failToDBSize;
        // 校验成功的数据大小 - 导入数据库失败的数据大小 =成功导入的数据大小
        this.succToDBSize = succSize - failToDBSize;
    }
}
