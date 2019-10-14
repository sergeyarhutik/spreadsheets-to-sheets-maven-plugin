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

    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            for (int i = 0; i < fileProperties.length; i++) {
                createFilePath(fileProperties[i]);
                downloadResource(fileProperties[i], i);
                SplitFileToSheets.splitSpreadsheetIntoSheets(fileProperties[i], i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadResource(FileProperties fileProperties, int i) throws IOException {
        URL url = new URL(fileProperties.getLink() + EXPORT_FORMAT);
        try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
             FileOutputStream fos = new FileOutputStream(fileProperties.getPath() + i + fileProperties.getFormat())) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    private static void createFilePath(FileProperties fileProperties) {
        File dir = new File(fileProperties.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}