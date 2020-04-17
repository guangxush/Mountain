package com.shgx.kafka.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;
import java.util.ServiceConfigurationError;

/**
 * Created by guangxush on 2018/8/27
 */
@Component
@Scope("prototype")
public class ApplicationOptions {

    private Properties properties;

    /**
     * init the properties file
     * @param inputFile
     * @throws IOException
     */
    public void init(String inputFile) throws IOException {
        //InputStream in = this.getClass().getResourceAsStream("/"+inputFile);//Server environment
        File file = new File("src/main/resources/config/"+inputFile);
        Properties props = new Properties();
        FileInputStream fileInputStream = new FileInputStream(file);
        props.load(fileInputStream);
        this.properties = props;
    }

    /**
     * get the param from properties
     * @param key
     * @return
     */
    public String getByNameString (String key) {
        if (!properties.containsKey(key)) {
            throw new ServiceConfigurationError("Configuration property not found: " + key);
        }
        return properties.getProperty(key);
    }

}
