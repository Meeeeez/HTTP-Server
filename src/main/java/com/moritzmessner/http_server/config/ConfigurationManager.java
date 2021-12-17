package com.moritzmessner.http_server.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.moritzmessner.http_server.util.Json;

import javax.print.DocFlavor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager configManager;
    private static Configuration currentConfig;
    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (configManager == null) configManager = new ConfigurationManager();
        return configManager;
    }

    public void loadConfigFile(String path)  {
        FileReader fileReader;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer stringBuffer;
        stringBuffer = new StringBuffer();
        int i;
        try {
            while((i = fileReader.read()) != -1 ) {
                stringBuffer.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf;
        try {
            conf = Json.parse(stringBuffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("An Error occurred while parsing", e);
        }
        try {
            currentConfig = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("An Error occured while parsing, Internal", e);
        }
    }

    public Configuration getCurrentConfig() {
        if(currentConfig == null) {
            throw new HttpConfigurationException("No Configuration Set.");
        }
        return currentConfig;
    }
}
