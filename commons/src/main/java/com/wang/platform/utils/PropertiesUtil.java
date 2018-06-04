package com.wang.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by amosli on 16/3/31.
 */
public class PropertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
    private Properties properties = new Properties();

    public PropertiesUtil(String fileName) {
        load(fileName);
    }

    private void load(String fileName) {
        try {
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(fileName);
            properties.load(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    public String getValue(String key) {
        if (!properties.containsKey(key)) {
            return "";
        }
        return properties.getProperty(key);
    }

    public Integer getIntegerValue(String key) {
        if (!properties.containsKey(key)) {
            return 0;
        }
        return Integer.parseInt(getValue(key));
    }

    public void main(String[] args) {
        LOGGER.info(getValue("uat"));
    }

}