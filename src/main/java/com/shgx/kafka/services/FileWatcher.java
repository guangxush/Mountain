package com.shgx.kafka.services;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.shgx.kafka.dao.SchemaData;
import com.shgx.kafka.dao.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guangxush on 2018/8/27
 */
@Service
@Slf4j
public class FileWatcher {

    public ArrayList<String> datesToProcess = new ArrayList<>();

    @Autowired
    private ServerConfig serverConfig;

    @Autowired(required=false)
    public FileWatcher(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    /**
     * get LineIterators to lazy load data and avoid load all data into memory at once
     * @return ArrayList<LineIterator>
     * */
    public ArrayList<LineIterator> getFileIterator (ChannelSftp channel) throws SftpException, IOException {
        ArrayList<LineIterator> fileLineIterators = new ArrayList<>();
        String targetFile = serverConfig.getTargetdir() + serverConfig.getTargerfile();
        InputStream inputStream = null;
        try {
            listen(channel);
            for(String date : datesToProcess) {
                inputStream = channel.get(targetFile + "." + date);
                LineIterator lineIterator = IOUtils.lineIterator(inputStream, "UTF-8");
                fileLineIterators.add(lineIterator);
            }
        } catch (SftpException ex) {
            log.error("Errors happened in SftpException ex: " + ex.getMessage());
            throw new SftpException(ex.id, ex.getMessage());
        } catch (IOException e) {
            log.error("Errors happened in IOException e: " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        return fileLineIterators;
    }


    /**
     * update done files to processed ones
     * processedFilePattern = user.weibo.processed.yyyymmdd
     * */
    public Boolean updateFileStatus (ChannelSftp channel) throws SftpException {
        int datesCnt = datesToProcess.size();
        int updateCnt = 0;
        try {
            for(String date : datesToProcess) {
                String doneFile = serverConfig.getDonedir() + serverConfig.getDonefile() + "." + date;
                String processedFile = serverConfig.getDonedir() + serverConfig.getProcessedfile() + "." + date;
                channel.rename(doneFile, processedFile);
                updateCnt++;
            }
            datesToProcess.clear();
        } catch (SftpException e) {
            log.error("Errors happened in SftpException: " + e.getMessage());
            throw new SftpException(e.id, e.getMessage());
        }
        return updateCnt > 0 && updateCnt == datesCnt;
    }


    /**
     * look for done files in the target server and parse dates to process
     * @param channel
     * doneFilePattern = user.weibo.done.yyyymmdd
     * */
    public void listen (ChannelSftp channel) throws SftpException {
        datesToProcess.clear();
        Pattern doneFilePattern = Pattern.compile(serverConfig.getDonefile() + "\\.([0-9]{8})");

        Vector doneFileList = channel.ls(serverConfig.getDonedir());
        for (Object entry : doneFileList) {
            ChannelSftp.LsEntry e = (ChannelSftp.LsEntry) entry;
            Matcher doneFileMatcher = doneFilePattern.matcher(e.getFilename());
            if (doneFileMatcher.matches()) {
                String date = doneFileMatcher.group(1);
                datesToProcess.add(date);
            }
        }
    }

    /**
     * parse the data file and load data into SchemaData
     * @param line
     * @return SchemaData
     *  userID | Title | Message | Likes | Comments | SendTime | Delete
     * */
    public SchemaData parseFile(String line) {
        SchemaData schemaData = new SchemaData();
        String[] lineArr = line.split("\\|");
        schemaData.setUserId(Long.valueOf(lineArr[0]).longValue());
        schemaData.setTitle(lineArr[1]);
        schemaData.setMessage(lineArr[2]);
        schemaData.setLikes(Integer.valueOf(lineArr[3]).intValue());
        schemaData.setComments(lineArr[4]);
        schemaData.setSendTime(lineArr[5]);
        schemaData.setDelete(Boolean.valueOf(lineArr[6]).booleanValue());
        return schemaData;
    }
}
