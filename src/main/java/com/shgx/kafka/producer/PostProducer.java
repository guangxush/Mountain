package com.shgx.kafka.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shgx.kafka.dao.SchemaData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by guangxush on 2018/8/27
 */
@Service
@Slf4j
@PropertySource("classpath:config/kafka.properties")
public class PostProducer extends KafkaProducer{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    @Value("${kafka.topic:shgx}")
    private String topic;

    /**
     * produce the data from web services
     * @param schemaDataArray
     */
    @Override
    public void produceFromService(SchemaData[] schemaDataArray) {
        for (SchemaData message : schemaDataArray) {
            log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
            kafkaTemplate.send(topic, gson.toJson(message));
        }
    }

    @Override
    public void produceFromFile(ArrayList<LineIterator> fileLineIterators) {}
}
