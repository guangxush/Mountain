package com.shgx.kafka.util;

import com.shgx.kafka.dao.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created by gshan on 2018/9/1
 */
@Component
@Slf4j
public class SSHWrapper {


    private static final String STRICT_HOST_KEY_CHECKING_KEY = "StrictHostKeyChecking";
    private static final String STRICT_HOST_KEY_CHECKING_VALUE = "no";
    private static final String CHANNEL_TYPE = "sftp";

    /**
     * ssh to server
     * @param serverConfig
     * */
    @Retryable(value = JSchException.class,maxAttempts = 5, backoff = @Backoff(delay = 60000))
    public ChannelSftp connect(ServerConfig serverConfig) throws JSchException {
        String jumpboxUser = serverConfig.getJumpboxUser();
        String jumpboxHost = serverConfig.getJumpboxHost();
        String jumpboxKeyFile = serverConfig.getJumpboxKeyfile();
        int jumpboxPort = serverConfig.getJumpboxPort();
        String targerUser = serverConfig.getTargetUser();
        String targetHost = serverConfig.getTargetHost();
        int targetPort = serverConfig.getTargetPort();
        String targetKeyFile = serverConfig.getTargetKeyfile();

        JSch jsch = new JSch();
        jsch.addIdentity(jumpboxKeyFile);
        jsch.setConfig(STRICT_HOST_KEY_CHECKING_KEY, STRICT_HOST_KEY_CHECKING_VALUE);

        // first ssh connection from site to jumpserver
        Session jumpboxSession = jsch.getSession(jumpboxUser, jumpboxHost, jumpboxPort);
        jumpboxSession.connect();
        log.info("Connected to " + jumpboxUser + "@" + jumpboxHost + ":" + jumpboxPort);

        // portforward
        int assignedPort = jumpboxSession.setPortForwardingL(0, targetHost, targetPort);
        log.info("portforwarding: " + "localhost:" + assignedPort+" -> " + targetHost + ":" + 22);

        // second ssh connection via ssh tunnel
        Session targetSession = jsch.getSession(targerUser, "127.0.0.1", assignedPort);
        targetSession.setHostKeyAlias(targetHost);
        targetSession.connect();
        log.info("Connected to " + targerUser + "@" + targetHost + ":" + targetPort);

        ChannelSftp channel = (ChannelSftp) targetSession.openChannel(CHANNEL_TYPE);
        channel.connect();
        return channel;
    }
}
