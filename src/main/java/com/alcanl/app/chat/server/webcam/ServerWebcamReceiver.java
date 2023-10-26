package com.alcanl.app.chat.server.webcam;

import com.alcanl.app.chat.server.Server;
import com.alcanl.app.global.ImageDisplayPanel;
import com.alcanl.app.global.Resources;
import com.karandev.util.console.Console;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.alcanl.app.chat.connection.ConnectionHandler.serverImageReceiver;

public class ServerWebcamReceiver extends Server {
    private DataInputStream dataInputStream;
    private ImageDisplayPanel imageDisplayPanel;
    private ServerWebcamReceiver(ServerSocket serverSocket, Socket clientSocket)
    {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        serverImageReceiver(serverSocket, clientSocket, imageDisplayPanel, dataInputStream);
    }
    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ServerWebcamReceiver serverWebcamReceiver;
        public Builder(ServerSocket serverSocket, Socket clientSocket)
        {
            serverWebcamReceiver = new ServerWebcamReceiver(serverSocket, clientSocket);
        }
        public Builder setDataInputStream(Socket clientSocket)
        {
            try {
                serverWebcamReceiver.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            }
            catch (IOException ex)
            {
                Console.writeLine(ex.getMessage());
            }
            return this;
        }
        public Builder setConnector(String connector)
        {
            serverWebcamReceiver.connector = connector;
            return this;
        }
        public Builder setImageDisplayPanel(ImageDisplayPanel imageDisplayPanel)
        {
            serverWebcamReceiver.imageDisplayPanel = imageDisplayPanel;
            Resources.setJFrame(serverWebcamReceiver.imageDisplayPanel);

            return this;
        }
        public ServerWebcamReceiver create()
        {
            return serverWebcamReceiver;
        }
    }
}
