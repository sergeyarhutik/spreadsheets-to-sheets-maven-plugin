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

    @Parameter(property = "links")
    private String[] links;

    @Parameter(property = "filesfolder", defaultValue = "target/generated-sources/")
    private String filesFolder;

    private static String SHEET_FORMAT = ".xlsx";
    private static String EXPORT_FORMAT = "/export?format=xlsx";

    public void execute() throws MojoExecutionException, MojoFailureException {

        File theDir = new File(filesFolder);
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
            }
        }

        for (int i = 0; i < links.length; i++) {
            try {
                for (int j = 0; j < links.length; j++) {
                downloadUsingNIO(links[i] + EXPORT_FORMAT, filesFolder + i + SHEET_FORMAT);
                SplitFileToSheets.splitSpreadsheetIntoSheets(filesFolder, i + SHEET_FORMAT, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadUsingNIO(String urlStr, String filesFolder) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(filesFolder);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}