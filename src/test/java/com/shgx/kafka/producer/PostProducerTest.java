package com.shgx.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created by gshan on 2018/9/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostProducerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testPostProducer()throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "1643750751010");
        map.put("title", "kafka amazing1");
        map.put("message", "kafka development is very interesting");
        map.put("likes",2);
        map.put("comments", "very exciting!;very interesting!");
        map.put("sendTime", "2018-8-30 10:49:43");
        map.put("delete", "false");
        String json = JSONObject.toJSONString(map);
        MvcResult result = mockMvc.perform(post("/kafka/producer")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(json)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
