package com.moritzmessner.http_server.core;

import com.diogonunes.jcolor.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.diogonunes.jcolor.Ansi.colorize;


public class ServerListener extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListener.class);

    private int port;
    private String webRoot;
    private ServerSocket serverSocket;

    public ServerListener(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }


    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(colorize("New Connection: " + socket.getInetAddress(), Attribute.BRIGHT_YELLOW_TEXT(), Attribute.BRIGHT_WHITE_BACK(), Attribute.BOLD()));

                HTTPConnectionWorker worker = new HTTPConnectionWorker(socket);
                worker.start();
            }
        } catch (IOException e) {
            LOGGER.error(colorize("Socket Error", Attribute.RED_TEXT(), Attribute.BRIGHT_WHITE_BACK(), Attribute.BOLD()), e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ignored) { }
            }
        }
    }
}
