package com.moritzmessner.http_server;

import com.moritzmessner.http_server.config.Configuration;
import com.moritzmessner.http_server.config.ConfigurationManager;

public class HTTPServer {

    public static void main(String[] args) {

        System.out.println("Server starting...");
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/config.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfig();

        System.out.print("Port: " + conf.getPort() + "\nWeb Root: " + conf.getWebroot() + "\n");
    }
}
