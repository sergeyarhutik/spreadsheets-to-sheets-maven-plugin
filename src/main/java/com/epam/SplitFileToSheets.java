package com.epam;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;


public class SplitFileToSheets {
    static void splitSpreadsheetIntoSheets(Spreadsheet spreadsheet) {
        String originFile = spreadsheet.getPath() + spreadsheet.hashCode() + spreadsheet.getFormat();
        XSSFWorkbook workbook = getWorkbook(originFile);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            workbook.setActiveSheet(i);
            String newFilePath = spreadsheet.getPath() + workbook.getSheetName(i) + spreadsheet.getFormat();
            try (FileOutputStream outFile = new FileOutputStream(new File(newFilePath))) {
                workbook.write(outFile);
            } catch (IOException e) {
                System.exit(0);
            }
            XSSFWorkbook tempWB = getWorkbook(newFilePath);
            removeUnnecessarySheets(tempWB, spreadsheet, tempWB.getSheetName(tempWB.getActiveSheetIndex()));
            deleteOriginFile(originFile);
        }
    }

    /**
     * Method takes String with path to origin file
     * and returns Object workbook.
     */
    private static XSSFWorkbook getWorkbook(String documentPath) {
        File file = new File(documentPath);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
            return workbook;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create workbook", e.getCause());
        }
    }

    /**
     * Method takes Object workbook, requires spreadsheet
     * and requires sheetName. Then it removes unnecessary
     * sheets and saves xlsx file.
     */
    private static void removeUnnecessarySheets(XSSFWorkbook workbook, Spreadsheet spreadsheet, String sheetName) {
        for (int i = workbook.getNumberOfSheets() - 1; i >= 0; i--) {
            XSSFSheet tmpSheet = workbook.getSheetAt(i);
            if (!tmpSheet.getSheetName().equals(sheetName)) {
                workbook.removeSheetAt(i);
            }
        }
        workbook.setSheetName(0, sheetName);
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(new File(spreadsheet.getPath() +
                    sheetName + spreadsheet.getFormat()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to open file", e.getCause());
        }
        try {
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to save file", e.getCause());
        }
    }

    private static void deleteOriginFile(String originFile) {
        new File(originFile).delete();
    }
}
