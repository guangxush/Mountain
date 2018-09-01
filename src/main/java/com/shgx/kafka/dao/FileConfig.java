package com.shgx.kafka.dao;

import com.shgx.kafka.util.ApplicationOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by gshan on 2018/8/27
 */
@Component
@Slf4j
public class FileConfig extends ServerConfig{

    private static final String SERVERS_FILES = "server.properties";
    private static final String FILE_TARGETDIR = "file.targetdir";
    private static final String FILE_TARGETFILE = "file.targetfile";
    private static final String FILE_DONEDIR = "file.donedir";
    private static final String FILE_DONEFILE = "file.donefile";
    private static final String FILE_PROCESSEDFILE = "file.processedfile";

    @Autowired
    public FileConfig (ApplicationOptions applicationOptions) {
        super(applicationOptions);
        this.applicationOptions = applicationOptions;
        this.loadConfig(SERVERS_FILES);
    }

    private String targerfile;
    private String targetdir;
    private String donefile;
    private String donedir;
    private String processedfile;

    public String getTargerfile() {
        return targerfile;
    }

    public void setTargerfile(String targerfile) {
        this.targerfile = targerfile;
    }

    public String getTargetdir() {
        return targetdir;
    }

    public void setTargetdir(String targetdir) {
        this.targetdir = targetdir;
    }

    public String getDonefile() {
        return donefile;
    }

    public void setDonefile(String donefile) {
        this.donefile = donefile;
    }

    public String getDonedir() {
        return donedir;
    }

    public void setDonedir(String donedir) {
        this.donedir = donedir;
    }

    public String getProcessedfile() {
        return processedfile;
    }

    public void setProcessedfile(String processedfile) {
        this.processedfile = processedfile;
    }

    @Override
    public void loadConfig (String configFile){
        try {
            applicationOptions.init(configFile);
        } catch (IOException e) {
            log.error("Failed to read properties file. " + e);
        }
        this.setTargetdir(applicationOptions.getByNameString(FILE_TARGETDIR));
        this.setTargerfile(applicationOptions.getByNameString(FILE_TARGETFILE));
        this.setDonedir(applicationOptions.getByNameString(FILE_DONEDIR));
        this.setDonefile(applicationOptions.getByNameString(FILE_DONEFILE));
        this.setProcessedfile(applicationOptions.getByNameString(FILE_PROCESSEDFILE));
    }
}
