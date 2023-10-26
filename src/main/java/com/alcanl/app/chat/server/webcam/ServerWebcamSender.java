package com.alcanl.app.chat.server.webcam;

import com.alcanl.app.chat.server.Server;
import com.github.sarxos.webcam.Webcam;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static com.alcanl.app.chat.connection.ConnectionHandler.serverImageSender;

public class ServerWebcamSender extends Server {
    public static final int PORT = 19430;
    private DataOutputStream dataOutputStream;
    private Webcam webcam;

    private ServerWebcamSender(ServerSocket serverSocket, Socket clientSocket)
    {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        serverImageSender(serverSocket, clientSocket, webcam, dataOutputStream);
    }

    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ServerWebcamSender serverWebcamSender;

        public Builder(ServerSocket serverSocket, Socket clientSocket) {
            serverWebcamSender = new ServerWebcamSender(serverSocket, clientSocket);
        }

        public Builder setDataOutputStream(Socket clientSocket) throws IOException {
            serverWebcamSender.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            return this;
        }

        public Builder setConnector(String connector) {
            serverWebcamSender.connector = connector;
            return this;
        }

        public Builder setWebcam(Webcam webcam) {
            serverWebcamSender.webcam = webcam;
            return this;
        }

        public ServerWebcamSender create() {
            return serverWebcamSender;
        }
    }
}
