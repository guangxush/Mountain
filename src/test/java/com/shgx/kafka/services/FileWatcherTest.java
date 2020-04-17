package com.shgx.kafka.services;

import com.shgx.kafka.dao.SchemaData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by guangxush on 2018/8/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FileWatcherTest {

    @Autowired
    private FileWatcher fileWatcher;

    @Test
    public void testparseFile(){
        String line = "1464589148861|kafka amazing|kafka development is very interesting|2|very exciting!;very interesting!" +
                "|2018-8-30 10:49:43|false";
        SchemaData message = fileWatcher.parseFile(line);
        assertEquals(new Long("1464589148861"), message.getUserId());
        assertEquals("kafka amazing", message.getTitle());
        assertEquals("kafka development is very interesting", message.getMessage());
        assertEquals(2, message.getLikes());
        assertEquals("very exciting!;very interesting!", message.getComments());
        assertEquals("2018-8-30 10:49:43", message.getSendTime());
        assertEquals(false, message.getDelete());
    }
}
