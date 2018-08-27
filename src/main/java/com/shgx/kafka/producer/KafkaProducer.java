package com.shgx.kafka.producer;

import com.shgx.kafka.dao.SchemaData;
import org.apache.commons.io.LineIterator;

import java.util.ArrayList;

/**
 * Created by gshan on 2018/8/27
 */
public abstract class KafkaProducer {
    public abstract void produceFromFile (ArrayList<LineIterator> fileLineIterators);

    public abstract void produceFromService (SchemaData[] schemaDataArray);
}
