package com.epam;

import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Data
public class SplitFileToSheets {
    public static void makeALotOfXlsx(String filesfolder, String fileName, int fileNumber) throws IOException {
        XSSFWorkbook workbook = getWorkbook(filesfolder + fileName);
        for (int j = 0; j < workbook.getNumberOfSheets(); j++) {
            String newFilePath = filesfolder + fileNumber + workbook.getSheetName(j) + ".xlsx";
            workbook.setActiveSheet(j);
            FileOutputStream outFile = new FileOutputStream(new File(newFilePath));
            workbook.write(outFile);
            outFile.close();
            XSSFWorkbook tempWB = getWorkbook(newFilePath);
            removeAllSheetsExceptOne(tempWB, tempWB.getSheetName(tempWB.getActiveSheetIndex()), tempWB.getSheetName(tempWB.getActiveSheetIndex()), filesfolder, fileNumber);
        }
    }

    private static XSSFWorkbook getWorkbook (String documentPath) throws IOException {
        File file = new File(documentPath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        return workbook;
    }

    private static void removeAllSheetsExceptOne (XSSFWorkbook workbook, String sheetName, String newSheetName, String filesfolder, int fileNumber) throws
            IOException {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            XSSFSheet tmpSheet = workbook.getSheetAt(i);
            if (tmpSheet.getSheetName() != sheetName) {
                workbook.removeSheetAt(i);
            }
        }
        workbook.setSheetName(0, newSheetName);
        FileOutputStream outFile = new FileOutputStream(new File(filesfolder + fileNumber + workbook.getSheetName(workbook.getActiveSheetIndex())) + ".xlsx");
        workbook.write(outFile);
        outFile.close();
    }
}
