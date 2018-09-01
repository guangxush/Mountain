package com.shgx.kafka.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by gshan on 2018/8/28
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileConfigTest {

    @Autowired
    FileConfig fileConfig;

    /**
     * Test SpringBoot read the properties from properties file
     */
    @Test
    public void test(){
        String donefile = fileConfig.getDonefile();
        assertEquals("user.weibo.done", donefile);
    }
}
