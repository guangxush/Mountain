package com.shgx.kafka.producer;

import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by gshan on 2018/9/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileProducerTest {

    @Autowired
    private FileProducer fileProducer;

    @Test
    public void testProducer() throws InterruptedException, ExecutionException, IOException {
        String msg1 = "\n1464589148850|kafka amazing|kafka development is very interesting|2|very exciting!;very interesting!|2018-8-30 10:49:43|false";
        LineIterator lineIterator = new LineIterator(new StringReader(msg1));
        ArrayList<LineIterator> lineIterators = new ArrayList<>();
        lineIterators.add(lineIterator);
        fileProducer.produceFromFile(lineIterators);
    }
}
