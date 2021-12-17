package com.moritzmessner.http_server.core;

import com.diogonunes.jcolor.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static com.diogonunes.jcolor.Ansi.colorize;

public class HTTPConnectionWorker extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HTTPConnectionWorker.class);

    private Socket socket;

    public HTTPConnectionWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String HTMLFileContent;
            try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/html/index.html")) {
                HTMLFileContent = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String CRLF = "\n\r"; //carriage return, line feed

            final String httpResponse =
                    "HTTP/1.1 200 OK" +                                                     //HTTP version + status code
                            CRLF +
                            "Content-Lenght: " + HTMLFileContent.getBytes().length + CRLF + //header
                            CRLF +
                            HTMLFileContent +                                               //content
                            CRLF + CRLF;

            outputStream.write(httpResponse.getBytes());

            LOGGER.info(colorize("Connection processed", Attribute.BRIGHT_YELLOW_TEXT(), Attribute.CYAN_BACK(), Attribute.BOLD()));
        } catch (IOException e) {
            LOGGER.error(colorize("Communication Error", Attribute.RED_TEXT(), Attribute.BRIGHT_WHITE_BACK(), Attribute.BOLD()), e);
        } finally {
            try {
                if (inputStream != null && outputStream != null) {
                    inputStream.close();
                    outputStream.close();
                }
                socket.close();
            } catch (IOException ignored) { }
        }
    }
}
