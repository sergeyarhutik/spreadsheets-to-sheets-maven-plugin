package com.epam;

import lombok.Data;

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


@Data
@Mojo(name = "getfile", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class GetFile extends AbstractMojo {

    @Parameter(property = "links")
    private String[] links;

    @Parameter(property = "filesfolder", defaultValue = "target/")
    private String filesFolder;

    private static String SheetName = ".xlsx";


    private static String exportFormat = "/export?format=xlsx";

    public void execute() throws MojoExecutionException, MojoFailureException {
        for (int i = 0; i < links.length; i++) {
            try {
                downloadUsingNIO(links[i] + exportFormat, filesFolder + i + SheetName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}