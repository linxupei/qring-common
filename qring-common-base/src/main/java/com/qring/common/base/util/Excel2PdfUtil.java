package com.qring.common.base.util;

import cn.hutool.core.lang.Assert;
import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author Qring
 * @Description excel转pdf工具
 * @Date 2023/3/17 11:26
 * @Version 1.0
 */
public class Excel2PdfUtil {

    private final static String LICENSE = "lib/aspose/license.xml";

    /**
     * 加载配置文件
     *
     * @return 是否加载成功
     */
    private static boolean getLicense() {
        boolean result = false;
        try (InputStream in = Excel2PdfUtil.class.getClassLoader().getResourceAsStream(LICENSE)) {
            License license = new License();
            license.setLicense(in);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void excel2Pdf(InputStream inputStream, OutputStream outputStream) {
        Assert.isTrue(getLicense(), "文件转换失败");
        PdfSaveOptions opts = new PdfSaveOptions();
        opts.setOnePagePerSheet(true);
        try {
            Workbook workbook = new Workbook(inputStream);
            workbook.save(outputStream, opts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        getLicense();
    }
}
