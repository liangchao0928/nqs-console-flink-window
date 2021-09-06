package com.eystar.console.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtils {


    public static Properties getProperties(String path) throws IOException {
        InputStream ips = PropertiesUtils.class
                .getClassLoader()
                .getResourceAsStream(path);
        Properties props = new Properties();
        props.load(ips);
        ips.close();
        return props;
    }


}
