package com.shgx.kafka.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.ServiceConfigurationError;

/**
 * Created by gshan on 2018/8/27
 */
@Component
@Scope("prototype")
public class ApplicationOptions {
    private Properties properties;

    public void init(String inputFile) throws IOException {
        String filePath = inputFile;
        Properties props = new Properties();
        props.load(new FileReader(filePath));
        this.properties = props;
    }

    public String getByNameString (String key) {
        if (!properties.containsKey(key)) {
            throw new ServiceConfigurationError("Configuration property not found: " + key);
        }
        return properties.getProperty(key);
    }
}
