package com.qring.common.test.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/20 13:51
 * @Version 1.0
 */
public class EasyPoiTest {

    @Test
    public void test2() throws Exception {

        Map<String, Object> value = new HashMap<String, Object>();
        List<Map<String, Object>> colList = new ArrayList<Map<String, Object>>();
        //先处理表头
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "小明挑战");
        map.put("zq", "正确");
        map.put("cw", "错误");
        map.put("tj", "统计");
        map.put("zqmk", "t.zq_xm");
        map.put("cwmk", "t.cw_xm");
        map.put("tjmk", "t.tj_xm");
        colList.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "小红挑战");
        map.put("zq", "正确");
        map.put("cw", "错误");
        map.put("tj", "统计");
        map.put("zqmk", "n:t.zq_xh");
        map.put("cwmk", "n:t.cw_xh");
        map.put("tjmk", "n:t.tj_xh");
        colList.add(map);

        value.put("colList", colList);

        List<Map<String, Object>> valList = new ArrayList<Map<String, Object>>();
        map = new HashMap<String, Object>();
        map.put("one", "运动");
        map.put("two", "跑步");
        map.put("zq_xm", 1);
        map.put("cw_xm", 2);
        map.put("tj_xm", 3);
        map.put("zq_xh", 4);
        map.put("cw_xh", 2);
        map.put("tj_xh", 6);
        valList.add(map);
        map = new HashMap<String, Object>();
        map.put("one", "运动");
        map.put("two", "跳高");
        map.put("zq_xm", 1);
        map.put("cw_xm", 2);
        map.put("tj_xm", 3);
        map.put("zq_xh", 4);
        map.put("cw_xh", 2);
        map.put("tj_xh", 6);
        valList.add(map);
        map = new HashMap<String, Object>();
        map.put("one", "文化");
        map.put("two", "数学");
        map.put("zq_xm", 1);
        map.put("cw_xm", 2);
        map.put("tj_xm", 3);
        map.put("zq_xh", 4);
        map.put("cw_xh", 2);
        map.put("tj_xh", 6);
        valList.add(map);

        value.put("valList", valList);
        TemplateExportParams params = new TemplateExportParams(
                "template/for_Col.xlsx");
        params.setColForEach(true);
        Workbook book = ExcelExportUtil.exportExcel(params, value);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\28608\\Desktop\\新建文件夹\\ExcelExportTemplateColFeTest_two.xlsx");
        book.write(fos);
        fos.close();
    }

    @Data
    static class A {
        Integer a;
        Integer b;
    }

    public static void main(String[] args) {
        ConcurrentHashMap<A, Boolean> resMap = new ConcurrentHashMap<>();
        HashMap<String, String > map = new HashMap<>();
        A a = new A();
        resMap.put(a, true);
        resMap.put(a, true);
        System.out.println(a.hashCode());
        a.setA(1);
        System.out.println(a.hashCode());
        resMap.put(a, false);
        System.out.println(resMap.size());
    }

    @Test
    public void test1() throws IOException {
        List<ExcelExportEntity> entity1 = new ArrayList<>();
        entity1.add(buildExcelEntity("序号", "order", 0));
        entity1.add(buildExcelEntity("性别", "sex", 1));
        entity1.add(buildExcelEntityByGroup("姓名", "students.name", "学生1", 2));
        entity1.add(buildExcelEntityByGroup("性别", "students.sex", "学生1", 2));
        entity1.add(buildExcelEntity("班级", "class", 3));
        ExcelExportEntity entity2 = new ExcelExportEntity("学生信息");

        entity2.setList(Arrays.asList(buildExcelEntityByGroup("科目", "students.project", "科目一", 4),
                buildExcelEntityByGroup("分数", "students.score", "科目一", 4),
                buildExcelEntityByGroup("科目", "students.project", "科目二", 4),
                buildExcelEntityByGroup("分数", "students.score", "科目二", 4))
                );
        entity1.add(entity2);


        List<Map<String, Object>> list = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            Map<String, Object> params = new HashMap<>();
            params.put("order", "" + i);
            params.put("sex", "sex" + i);
            params.put("class", "class-" + i);
            params.put("students.sex", "man-" + i);
            params.put("students.name", "name-" + i);
            list.add(params);
        });
        ExportParams exportParams = new ExportParams("测试", "日期：2020-11-18", "sheet名字");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity1, list);
        Sheet sheet = workbook.getSheetAt(0);//取此Excel文件的第一个Sheet
        //四个参数依次是：起始行，终止行，起始列，终止列，index是从0开始
        CellRangeAddress craOne = new CellRangeAddress(3, 4, 2, 3);
        sheet.addMergedRegion(craOne);

        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\DELL\\Desktop\\ExcelExportForMap.xls")) {
            workbook.write(fos);
        }
    }

//    public static void main(String[] args) {
//
//        String[] HEAD_TITLE = new String[]{"1", "2"};
//        List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
//
//        ExcelExportEntity colEntity = new ExcelExportEntity("SKU", "sku");
//        colEntity.setNeedMerge(true);
//        colList.add(colEntity);
//        /**
//         * 将此循环改成任意循环
//         **/
//        Map<String, Map<String, String>> resultMap = new HashMap<>();
//        Map<String, String> map = new TreeMap<>();
//        map.put("A", "a");
//        resultMap.put("KKK", map);
//
//        for (Map.Entry<String, Map<String, String>> entry : resultMap.entrySet()) {
//            //一级表头
//            ExcelExportEntity group1 = new ExcelExportEntity(entry.getKey(), entry.getKey());
//            //二级对象
//            Map<String, String> tm = entry.getValue();
//            //二级表头
//            List<ExcelExportEntity> exportEntities2 = new ArrayList<>();
//            //二级
//            tm.forEach((key, value) -> {
//                ExcelExportEntity ee2 = new ExcelExportEntity(key.toString(), entry.getKey() + key);
//                ee2.setGroupName(entry.getKey());
//                //三级表头
//                List<ExcelExportEntity> exportEntities3 = new ArrayList<>();
//
//                for (String s : HEAD_TITLE) {
//                    ExcelExportEntity ev1 = new ExcelExportEntity(s, entry.getKey() + key + s);
////                    List<ExcelExportEntity> exportEntities4 = new ArrayList<>();
////                    for (int k = 0; k < 2; k++) {
////                        ExcelExportEntity ev2 = new ExcelExportEntity(s, entry.getKey() + key + s);
////                        exportEntities4.add(ev2);
////                    }
////                    ev1.setList(exportEntities4);
//                    exportEntities3.add(ev1);
//                }
//                ee2.setList(exportEntities3);
//                exportEntities2.add(ee2);
//            });
//            group1.setList(exportEntities2);
//
//            colList.add(group1);
//        }
//
//        //文件数据
//        List<Map<String, Object>> list = new ArrayList<>();
//
//        //导出
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("数据表", "数据"), colList, list);
//        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\DELL\\Desktop\\ExcelExportForMap.xls")) {
//            workbook.write(fos);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static ExcelExportEntity buildExcelEntity(String name, Object key, int orderNo) {
        ExcelExportEntity excelEntity = new ExcelExportEntity(name, key);
        excelEntity.setOrderNum(orderNo);
        return excelEntity;
    }

    /**
     * 在group列下添加子列
     *
     * @param name
     * @param key
     * @param group
     * @return
     */
    private static ExcelExportEntity buildExcelEntityByGroup(String name, Object key, String group, int orderNo) {
        ExcelExportEntity excelEntity = new ExcelExportEntity(name, key);
        excelEntity.setGroupName(group);
        excelEntity.setOrderNum(orderNo);
        return excelEntity;
    }

    private static ExcelExportEntity buildExcelEntityByGroup(String name, String group, int orderNo) {
        ExcelExportEntity excelEntity = new ExcelExportEntity(name);
        excelEntity.setGroupName(group);
        excelEntity.setNeedMerge(true);
        excelEntity.setOrderNum(orderNo);
        return excelEntity;
    }
}
