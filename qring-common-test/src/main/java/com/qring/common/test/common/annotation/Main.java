package com.qring.common.test.common.annotation;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.*;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/12/14 16:01
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(Objects.equals(null, null));
    }

    public static void in1() {
        ExcelReader reader = ExcelUtil.getReader("E:\\download\\告警生成配置列表.xlsx");
        reader.setSheet("模拟量");
        System.out.println(reader.read(2));
        reader.setSheet("数字量11111");
        System.out.println(reader.read(2));
    }

    public static void in() {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\DELL\\Downloads\\response.xlsx");

        List<List<Object>> paths = reader.read();
        List<String> list = new ArrayList<>();
        Deque<Object> stack = new ArrayDeque<>();
        List<String> s = new ArrayList<>();
        int index = 0;
        for (List<Object> objectList : paths) {
            if (index >= objectList.size()) {
                index = objectList.size() - 1;
            }
            while (index < objectList.size() && "".equals(objectList.get(index))) {
                index++;
            }
            while (index + 1 <= stack.size()) {
                stack.pop();
            }
            stack.push(objectList.get(index));
            s.add(stack.toString());
        }
        System.out.println(s.size());
        System.out.println(s);
    }
}
