package com.epam;

import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "getsheets", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class DownloadSpreadSheet extends AbstractMojo {

    @Parameter(property = "filesproperties")
    private FileProperties[] fileProperties;

    private static String EXPORT_FORMAT = "/export?format=xlsx";
    private static String SHEET_FORMAT = ".xlsx";

    public static String getSheetFormat() {
        return SHEET_FORMAT;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            for (int i = 0; i < 2; i++) {
                File theDir = new File(fileProperties[i].getPath());
                if (!theDir.exists()) {
                    try {
                        theDir.mkdir();
                    } catch (SecurityException se) {
                    }
                }
                downloadUsingNIO(fileProperties[i].getLink() + EXPORT_FORMAT,
                        fileProperties[i].getPath() + i + SHEET_FORMAT);
                SplitFileToSheets.splitSpreadsheetIntoSheets(fileProperties[i].getPath(),
                        i + SHEET_FORMAT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadUsingNIO(String urlStr, String filesFolder) throws IOException {
        URL url = new URL(urlStr);
        try(ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(filesFolder)) {
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}