package com.shgx.kafka.dao;

import com.shgx.kafka.util.ApplicationOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by gshan on 2018/8/27
 */
@Component
@Slf4j
public class FileConfig extends ServerConfig{
    private static final Logger logger = Logger.getLogger(FileConfig.class);

    private static final String ETLSERVER_PROPERTIES_FILE = "etlserver.properties";
    private static final String UPI_TARGETDIR = "export.targetdir";
    private static final String UPI_TARGETFILE = "export.targetfile";
    private static final String UPI_DONE_DIR = "export.donedir";
    private static final String UPI_DONE_FILE = "export.donefile";
    private static final String UPI_PROCESSED_FILE = "export.processedfile";


    @Autowired
    public FileConfig(ApplicationOptions applicationOptions) {
        super(applicationOptions);
        this.applicationOptions = applicationOptions;
        this.loadConfig(ETLSERVER_PROPERTIES_FILE);
    }

    @Override
    public void loadConfig (String configFile){
        try {
            applicationOptions.init(configFile);
        } catch (IOException e) {
            log.error("Failed to read properties file. " + e);
        }
        this.setTargetFile(applicationOptions.getByNameString(UPI_TARGETFILE));
        this.setTargetDir(applicationOptions.getByNameString(UPI_TARGETDIR));
        this.setDoneDir(applicationOptions.getByNameString(UPI_DONE_DIR));
        this.setDoneFile(applicationOptions.getByNameString(UPI_DONE_FILE));
        this.setProcessedFile(applicationOptions.getByNameString(UPI_PROCESSED_FILE));
    }
}
