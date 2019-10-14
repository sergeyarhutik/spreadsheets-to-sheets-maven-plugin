package com.epam;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;

import static com.epam.DownloadSpreadSheet.getLinkForDownload;

public class SplitFileToSheets {
    public static void splitSpreadsheetIntoSheets(FileProperties fileProperties, int filesNumber) throws IOException {
        URL url = getLinkForDownload(fileProperties);
        String fileName = Paths.get(url.getPath()).getFileName().toString();
        XSSFWorkbook workbook = getWorkbook(fileProperties.getPath() + fileName);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String newFilePath = fileProperties.getPath() + workbook.getSheetName(i) + fileProperties.getFormat();
            workbook.setActiveSheet(i);
            try(FileOutputStream outFile = new FileOutputStream(new File(newFilePath))) {
                workbook.write(outFile);
            }
            XSSFWorkbook tempWB = getWorkbook(newFilePath);
            removeUnnecessarySheets(tempWB, fileProperties, tempWB.getSheetName(tempWB.getActiveSheetIndex()));
            URL url1 = getLinkForDownload(fileProperties);
            String fileName1 = Paths.get(url1.getPath()).getFileName().toString();
            File file = new File(fileProperties.getPath() + fileName1);
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
