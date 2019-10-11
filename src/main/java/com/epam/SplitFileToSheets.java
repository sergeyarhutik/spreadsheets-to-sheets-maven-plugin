package com.epam;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class SplitFileToSheets {
    public static void splitSpreadsheetIntoSheets(String filesFolder, String fileName) throws IOException {

        XSSFWorkbook workbook = getWorkbook(filesFolder + fileName);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String newFilePath = filesFolder + workbook.getSheetName(i) + DownloadSpreadSheet.getSheetFormat();
            workbook.setActiveSheet(i);
            FileOutputStream outFile = new FileOutputStream(new File(newFilePath));
            workbook.write(outFile);
            outFile.close();
            XSSFWorkbook tempWB = getWorkbook(newFilePath);
            removeUnnecessarySheets(tempWB, tempWB.getSheetName(tempWB.getActiveSheetIndex()),
                    tempWB.getSheetName(tempWB.getActiveSheetIndex()), filesFolder);
            File file = new File(filesFolder + fileName);
            file.delete();
        }
    }

    private static XSSFWorkbook getWorkbook(String documentPath) throws IOException {
        File file = new File(documentPath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        return workbook;
    }

    private static void removeUnnecessarySheets(XSSFWorkbook workbook, String sheetName,
                                                String newSheetName, String filesfolder) throws
            IOException {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            XSSFSheet tmpSheet = workbook.getSheetAt(i);
            if (tmpSheet.getSheetName() != sheetName) {
                workbook.removeSheetAt(i);
            }
        }
        workbook.setSheetName(0, newSheetName);
        FileOutputStream outFile = new FileOutputStream(new File(filesfolder +
                workbook.getSheetName(workbook.getActiveSheetIndex())) + DownloadSpreadSheet.getSheetFormat());
        workbook.write(outFile);
        outFile.close();
    }
}
