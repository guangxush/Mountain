package com.shgx.kafka.services;

import com.shgx.kafka.dao.SchemaData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by gshan on 2018/8/27
 */
@Service
@Component
public class FileWatcher {

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
