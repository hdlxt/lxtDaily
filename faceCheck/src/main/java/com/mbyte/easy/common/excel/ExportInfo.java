package com.mbyte.easy.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.mbyte.easy.util.Constants;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExportInfo extends BaseRowModel {
    @ExcelProperty(value = "姓名" ,index = 0)
    private String name;

    @ExcelProperty(value = "状态",index = 1)
    private int status;


    public String getStatus() {
        if(Constants.CHECK_YES == status){
            return "出勤";
        }
        return "缺勤";
    }
}
