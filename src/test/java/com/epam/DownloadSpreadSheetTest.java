package com.epam;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class DownloadSpreadSheetTest {
    private String linka;
    private String patha;

    private static String SHEET_FORMAT = ".xlsx";
    private static String EXPORT_FORMAT = "/export?format=xlsx";

    @Test
    void execute() {
        linka = "https://111";
        patha = "target/qwe/";
        Map fileProperties = new HashMap();
        fileProperties.put(linka, patha);
        File theDir = new File((String) patha);
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
            }
        }
        String StringLink = linka + EXPORT_FORMAT;
        String PathLink = patha + SHEET_FORMAT;
        Assert.assertEquals(StringLink, "https://111/export?format=xlsx");
        Assert.assertEquals(PathLink, "target/qwe/.xlsx");
    }
}