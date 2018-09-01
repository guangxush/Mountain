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
public class ServerConfig {

    private static final String SERVERS_FILES = "server.properties";
    private static final String TAREGT_SERVER_USER = "target.server.user";
    private static final String TAREGT_SERVER_HOST = "target.server.host";
    private static final String TAREGT_SERVER_PORT = "target.server.port";
    private static final String TAREGT_SERVER_KEYFILE = "target.server.keyfile";
    private static final String JUMPBOX_SERVER_USER = "jumpbox.server.user";
    private static final String JUMPBOX_SERVER_HOST = "jumpbox.server.host";
    private static final String JUMPBOX_SERVER_PORT = "jumpbox.server.port";
    private static final String JUMPBOX_SERVER_KEYFILE = "jumpbox.server.keyfile";

    protected ApplicationOptions applicationOptions;

    @Autowired
    public ServerConfig (ApplicationOptions applicationOptions) {
        this.applicationOptions = applicationOptions;
        this.loadConfig(SERVERS_FILES);
    }

    private String targetUser;
    private int targetPort;
    private String targetHost;
    private String targetKeyfile;
    private String jumpboxUser;
    private int jumpboxPort;
    private String jumpboxHost;
    private String jumpboxKeyfile;

    private String targerfile;
    private String targetdir;
    private String donefile;
    private String donedir;
    private String processedfile;

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getTargetKeyfile() {
        return targetKeyfile;
    }

    public void setTargetKeyfile(String targetKeyfile) {
        this.targetKeyfile = targetKeyfile;
    }

    public String getJumpboxUser() {
        return jumpboxUser;
    }

    public void setJumpboxUser(String jumpboxUser) {
        this.jumpboxUser = jumpboxUser;
    }

    public int getJumpboxPort() {
        return jumpboxPort;
    }

    public void setJumpboxPort(int jumpboxPort) {
        this.jumpboxPort = jumpboxPort;
    }

    public String getJumpboxHost() {
        return jumpboxHost;
    }

    public void setJumpboxHost(String jumpboxHost) {
        this.jumpboxHost = jumpboxHost;
    }

    public String getJumpboxKeyfile() {
        return jumpboxKeyfile;
    }

    public void setJumpboxKeyfile(String jumpboxKeyfile) {
        this.jumpboxKeyfile = jumpboxKeyfile;
    }

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

    public void loadConfig (String configFile){
        try {
            applicationOptions.init(configFile);
        } catch (IOException e) {
            log.error("Failed to read properties file. " + e);
        }
        this.setTargetUser(applicationOptions.getByNameString(TAREGT_SERVER_USER));
        this.setTargetHost(applicationOptions.getByNameString(TAREGT_SERVER_HOST));
        this.setTargetPort(Integer.parseInt(applicationOptions.getByNameString(TAREGT_SERVER_PORT)));
        this.setTargetKeyfile(applicationOptions.getByNameString(TAREGT_SERVER_KEYFILE));
        this.setJumpboxUser(applicationOptions.getByNameString(JUMPBOX_SERVER_USER));
        this.setJumpboxHost(applicationOptions.getByNameString(JUMPBOX_SERVER_HOST));
        this.setJumpboxPort(Integer.parseInt(applicationOptions.getByNameString(JUMPBOX_SERVER_PORT)));
        this.setJumpboxKeyfile(applicationOptions.getByNameString(JUMPBOX_SERVER_KEYFILE));
    }
}
