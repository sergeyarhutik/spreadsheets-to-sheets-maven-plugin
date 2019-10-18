package com.epam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @version 1.0-SNAPSHOT
 * Maven Plugin to download <b>google spreadsheets</b> and
 * convert each sheet into a separate <b>xlsx-file</b>.
 * <b>phase</b> sets when the plugin should work,
 * @Mojo name <b>getsheets</b> used in POM as a goal.
 * https://maven.apache.org/developers/mojo-api-specification.html
 * Example:
 *                      <phase>compile</phase>
 *                         <goals>
 *                             <goal>getsheets</goal>
 *                         </goals>
 */
@Mojo(name = "getsheets", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class DownloadSpreadSheet extends AbstractMojo {

    /** @Parameret used to configure this parameter from the POM */
    @Parameter
    private Spreadsheet[] spreadsheet;

    /**
     * An execute() method, which triggers the Mojo's
     * build-process behavior.
     *  1. Creating the directory specified in the POM
     * for storing the temporary full spreadsheet.
     *  2. Temporary full spreadsheet file saving
     * into created directory.
     *  3. Split spreadsheet file into sheets. Each
     *  sheet saving into separate xlsx file.
     */
    public void execute() {
        Arrays.stream(spreadsheet).forEach(element -> {
            createFilePath(element);
            downloadResource(element);
            SplitFileToSheets.splitSpreadsheetIntoSheets(element);
        });
    }

    /**
     * Method, which takes each full spreadsheet
     * and saves it into xlsx file.
     */
    private void downloadResource(Spreadsheet spreadsheet) {
        try (InputStream in = new URL(spreadsheet.getLink()).openStream()) {
            Files.copy(in, Paths.get(spreadsheet.getPath() + spreadsheet.hashCode() + spreadsheet.getFormat()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Unable to download spreadsheet from GoogleDrive", e.getCause());
        }
    }

    /**
     * Method checks existing of necessary directory
     * and creates it if not.
     */
    private void createFilePath(Spreadsheet spreadsheet) {
        File dir = new File(spreadsheet.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}


