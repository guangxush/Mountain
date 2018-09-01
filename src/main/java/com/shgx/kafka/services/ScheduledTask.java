package com.shgx.kafka.services;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.shgx.kafka.dao.FileConfig;
import com.shgx.kafka.dao.ServerConfig;
import com.shgx.kafka.producer.FileProducer;
import com.shgx.kafka.util.SSHWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gshan on 2018/9/1
 */
@Service
@Configuration
@Slf4j
public class ScheduledTask {
    private ChannelSftp channel;

    @Autowired
    //@Qualifier("ServerConfig")
    private ServerConfig serverConfig;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private SSHWrapper sshWrapper;

    @Autowired
    private FileWatcher fileWatcher;

    @Autowired
    private FileProducer fileProducer;

    /**
     * 1 minutes scheduled
     * */
    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        log.info("Scheduled on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            //send weibo data to kafka
            channel = sshWrapper.connect(serverConfig);
            fileWatcher = new FileWatcher(fileConfig);
            ArrayList<LineIterator> fileLineIterators = fileWatcher.getFileIterator(channel);
            if (fileLineIterators != null && fileLineIterators.size() > 0) {
                fileProducer.produceFromFile(fileLineIterators);
            }
            if (fileWatcher.updateFileStatus(channel)) {
                System.out.println("weibo data sent successfully to kafka!");
            }
        } catch (IOException ioe) {
            log.error("IOException happened", ioe);
        } catch (SftpException sftpe) {
            log.error("SftpException happened", sftpe);
        } catch (JSchException jsche) {
            log.error("JSchException happened", jsche);
        } finally {
            channel.exit();
        }
    }
}
