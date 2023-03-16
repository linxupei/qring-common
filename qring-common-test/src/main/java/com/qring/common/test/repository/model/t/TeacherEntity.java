package com.qring.common.test.repository.model.t;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JueYue
 * 2013-08-31 22:52:17
 * @version V1.0
 * @Title: Entity
 * @Description: 课程老师
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity implements java.io.Serializable {
    /**
     * id
     */
    //@Excel(name = "主讲老师", orderNum = "2",isImportField = "true_major,true_absent")
    private String id;
    /**
     * name
     */
    @Excel(name = "主讲老师_major,代课老师_absent", orderNum = "1", needMerge = true, isImportField = "true_major,true_absent")
    private String name;
}
