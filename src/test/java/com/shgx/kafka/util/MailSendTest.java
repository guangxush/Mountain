package com.shgx.kafka.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by guangxush on 2018/9/1
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MailSendTest {

    @Autowired
    private MailSend mailSend;

    @Test
    public void testSendEmail () {
        mailSend.sendmail("666");
    }
}
