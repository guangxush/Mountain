package com.shgx.kafka.producer;

import com.shgx.kafka.dao.SchemaData;
import org.apache.commons.io.LineIterator;

import java.util.ArrayList;

/**
 * Created by guangxush on 2018/8/27
 */
public abstract class KafkaProducer {
    /**
     * produce the data from file
     * @param fileLineIterators
     */
    public abstract void produceFromFile (ArrayList<LineIterator> fileLineIterators);

    /**
     * produce the data from web services
     * @param schemaDataArray
     */
    public abstract void produceFromService (SchemaData[] schemaDataArray);
}
