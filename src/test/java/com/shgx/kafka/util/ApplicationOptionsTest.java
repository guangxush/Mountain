package com.shgx.kafka.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ServiceConfigurationError;
import static org.junit.Assert.assertEquals;

/**
 * Created by guangxush on 2018/9/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationOptionsTest {

    @Test
    public void testGetByNameString () {
        ApplicationOptions applicationOptions = new ApplicationOptions();
        try {
            applicationOptions.init("server.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("22", applicationOptions.getByNameString("target.server.port"));
        assertEquals("~/.ssh/cruise", applicationOptions.getByNameString("target.server.keyfile"));
        assertEquals("user.weibo.processed", applicationOptions.getByNameString("file.processedfile"));
    }

    @Test(expected = ServiceConfigurationError.class)
    public void testServiceConfigurationError () {
        ApplicationOptions applicationOptions = new ApplicationOptions();
        try {
            applicationOptions.init("kafka.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        applicationOptions.getByNameString("no.name");
    }
}
