package com.moritzmessner.http_server;

import com.diogonunes.jcolor.Attribute;

import com.moritzmessner.http_server.config.Configuration;
import com.moritzmessner.http_server.config.ConfigurationManager;
import com.moritzmessner.http_server.core.ServerListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import static com.diogonunes.jcolor.Ansi.colorize;

public class HTTPServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HTTPServer.class);

    public static void main(String[] args) {

        LOGGER.info(colorize("Server starting...", Attribute.BRIGHT_YELLOW_TEXT(), Attribute.BLUE_BACK(), Attribute.BOLD()));
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/config.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfig();

        LOGGER.info(colorize("Port: " + conf.getPort(), Attribute.BRIGHT_YELLOW_TEXT(), Attribute.BLUE_BACK(), Attribute.BOLD()));
        LOGGER.info(colorize("Web Root: " + conf.getWebroot(), Attribute.BRIGHT_YELLOW_TEXT(), Attribute.BLUE_BACK(), Attribute.BOLD()));
        try {
            ServerListener serverListener = new ServerListener(conf.getPort(), conf.getWebroot());
            serverListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
