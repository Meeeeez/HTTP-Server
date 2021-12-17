package com.moritzmessner.http_server;

import com.moritzmessner.http_server.config.Configuration;
import com.moritzmessner.http_server.config.ConfigurationManager;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HTTPServer {

    public static void main(String[] args) {

        System.out.println("Server starting...");
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/config.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfig();

        System.out.print("Port: " + conf.getPort() + "\nWeb Root: " + conf.getWebroot() + "\n");

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String HTMLFileContent;
            try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/html/index.html")) {
                HTMLFileContent = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String CRLF = "\n\r"; //carriage return, line feed

            final String HTTPResponse =
                    "HTTP/1.1 200 OK" +                                                     //HTTP version + status code
                            CRLF +
                            "Content-Lenght: " + HTMLFileContent.getBytes().length + CRLF + //header
                            CRLF +
                            HTMLFileContent +                                               //content
                            CRLF + CRLF;

            outputStream.write(HTTPResponse.getBytes());

            inputStream.close();
            outputStream.close();
            serverSocket.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
