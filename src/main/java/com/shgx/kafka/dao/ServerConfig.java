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
public class ServerConfig {
    private static final Logger logger = Logger.getLogger(ServerConfig.class);

    private static final String ETLSERVER_PROPERTIES_FILE = "server.properties";
    private static final String ETL_USER = "export.user";
    private static final String ETL_HOST = "export.host";
    private static final String ETL_PORT = "export.port";
    private static final String ETL_KEYFILE = "export.keyfile";

    protected ApplicationOptions applicationOptions;

    @Autowired
    public ServerConfig (ApplicationOptions applicationOptions) {
        this.applicationOptions = applicationOptions;
        this.loadConfig(ETLSERVER_PROPERTIES_FILE);
    }

    private String etlUser;
    private String etlHost;
    private int etlPort;
    private String keyFile;
    private String targetFile;
    private String targetDir;
    private String doneDir;
    private String doneFile;
    private String processedFile;
    private String jumpboxHost;
    private int jumpboxPort;
    private String jumpboxKeyfile;

    public String getEtlUser() {
        return etlUser;
    }

    public void setEtlUser(String etlUser) {
        this.etlUser = etlUser;
    }

    public String getEtlHost() {
        return etlHost;
    }

    public void setEtlHost(String etlHost) {
        this.etlHost = etlHost;
    }

    public int getEtlPort() {
        return etlPort;
    }

    public void setEtlPort(int etlPort) {
        this.etlPort = etlPort;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getDoneDir() {
        return doneDir;
    }

    public void setDoneDir(String doneDir) {
        this.doneDir = doneDir;
    }

    public String getDoneFile() {
        return doneFile;
    }

    public void setDoneFile(String doneFile) {
        this.doneFile = doneFile;
    }

    public String getProcessedFile() {
        return processedFile;
    }

    public void setProcessedFile(String processedFile) {
        this.processedFile = processedFile;
    }

    public String getJumpboxHost() {
        return jumpboxHost;
    }

    public void setJumpboxHost(String jumpboxHost) {
        this.jumpboxHost = jumpboxHost;
    }

    public int getJumpboxPort() {
        return jumpboxPort;
    }

    public void setJumpboxPort(int jumpboxPort) {
        this.jumpboxPort = jumpboxPort;
    }

    public String getJumpboxKeyfile() {
        return jumpboxKeyfile;
    }

    public void setJumpboxKeyfile(String jumpboxKeyfile) {
        this.jumpboxKeyfile = jumpboxKeyfile;
    }

    public void loadConfig (String configFile){
        try {
            applicationOptions.init(configFile);
        } catch (IOException e) {
            log.error("Failed to read properties file. " + e);
        }
        this.setEtlUser(applicationOptions.getByNameString(ETL_USER));
        this.setEtlHost(applicationOptions.getByNameString(ETL_HOST));
        this.setEtlPort(Integer.parseInt(applicationOptions.getByNameString(ETL_PORT)));
        this.setKeyFile(applicationOptions.getByNameString(ETL_KEYFILE));
    }
}
