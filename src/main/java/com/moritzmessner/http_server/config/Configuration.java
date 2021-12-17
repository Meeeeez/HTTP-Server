package com.moritzmessner.http_server.config;

public class Configuration {

    private int port;
    private String webRoot;

    public int getPort() {
        return port;
    }

    public String getWebroot() {
        return webRoot;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebRoot(String web_root) {
        this.webRoot = web_root;
    }
}
