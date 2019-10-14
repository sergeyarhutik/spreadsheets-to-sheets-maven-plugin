package com.epam;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class SplitFileToSheets {
    public static void splitSpreadsheetIntoSheets(FileProperties fileProperties, int filesNumber) throws IOException {

        XSSFWorkbook workbook = getWorkbook(fileProperties.getPath() + filesNumber + fileProperties.getFormat());
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String newFilePath = fileProperties.getPath() + workbook.getSheetName(i) + fileProperties.getFormat();
            workbook.setActiveSheet(i);
            try(FileOutputStream outFile = new FileOutputStream(new File(newFilePath))) {
                workbook.write(outFile);
            }
            XSSFWorkbook tempWB = getWorkbook(newFilePath);
            removeUnnecessarySheets(tempWB, fileProperties, tempWB.getSheetName(tempWB.getActiveSheetIndex()));
            File file = new File(fileProperties.getPath() + filesNumber + fileProperties.getFormat());
            file.delete();
        }
    }

    private static XSSFWorkbook getWorkbook(String documentPath) throws IOException {
        File file = new File(documentPath);
        try(FileInputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
            return workbook;
        }
    }

    private static void removeUnnecessarySheets(XSSFWorkbook workbook, FileProperties fileProperties, String sheetName) throws
            IOException {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            XSSFSheet tmpSheet = workbook.getSheetAt(i);
            if (tmpSheet.getSheetName() != sheetName) {
                workbook.removeSheetAt(i);
            }
        }
        workbook.setSheetName(0, sheetName);
        FileOutputStream outFile = new FileOutputStream(new File(fileProperties.getPath() +
                sheetName + fileProperties.getFormat()));
        workbook.write(outFile);
        outFile.close();
    }
}
