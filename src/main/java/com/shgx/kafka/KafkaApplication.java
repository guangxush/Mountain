package com.shgx.kafka;

import com.shgx.kafka.producer.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class, args);

        /**KafkaSender sender = context.getBean(KafkaSender.class);
        for (int i = 0; i < 10; i++) {
            sender.send();

            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
