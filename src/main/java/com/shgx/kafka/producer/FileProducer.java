package com.shgx.kafka.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shgx.kafka.dao.SchemaData;
import com.shgx.kafka.services.FileWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by gshan on 2018/8/27
 */
@Service
@Slf4j
public class FileProducer extends KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private FileWatcher fileWatcher;

    private static Gson gson = new GsonBuilder().create();

    @Override
    public  void produceFromFile (ArrayList<LineIterator> fileLineIterators){
        for (LineIterator lineIterator : fileLineIterators) {
            int cnt = 0;
            while(lineIterator.hasNext()) {
                String line = lineIterator.nextLine();
                // The first line is header
                if (++cnt == 1) {
                    continue;
                }
                SchemaData message = fileWatcher.parseFile(line);
                log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
                kafkaTemplate.send("shgx", gson.toJson(message));
            }
        }

    }

    @Override
    public void produceFromService(SchemaData[] schemaDataArray) {}

}
