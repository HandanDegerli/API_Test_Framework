package io.petstore.utils;

import io.petstore.core.config.ConfigParser;

//benim bu class a ihtiyacım olmıcak muhtemelen çünkü env ayrımı yapmıyorum ama nolur nolmaz diye kalsın

public class SystemPropertyHelper {

    public static String getEnvVal() {
        String valueFromYamlFile = ConfigParser.getValue("env");
        String  envVal = System.getProperty("env");

        if(envVal.length() > 0){
            return envVal;
        }

   //     System.out.println("Incorrect or NO system property" + envVal + "value specified for env ...defaulting to property provided by the configuration.yml file env : " + valueFromYamlFile);

        return valueFromYamlFile;
    }

}
