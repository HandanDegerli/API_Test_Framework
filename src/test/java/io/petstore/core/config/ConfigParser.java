package io.petstore.core.config;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

public class ConfigParser {
    private static final String CONFIG_FILE_NAME = "./configuration.yml";
    public static String getValue(String key) {
        Logger logger = Logger.getLogger(ConfigParser.class);
        Yaml yaml = new Yaml();
        try{
            Reader yamlFile = new FileReader(CONFIG_FILE_NAME);

            Map<String, Object> yamlMaps = yaml.load(yamlFile);
            return (String) yamlMaps.get(key);
        }catch (Exception e) {
            System.out.println(e);
            logger.error("Configuration File could not mapped");
        }
        return null;
    }
}
