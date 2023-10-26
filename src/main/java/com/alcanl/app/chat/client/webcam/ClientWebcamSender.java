package com.alcanl.app.chat.client.webcam;

import com.alcanl.app.chat.client.Client;
import com.github.sarxos.webcam.Webcam;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.alcanl.app.chat.connection.ConnectionHandler.clientImageSender;

public class ClientWebcamSender extends Client {
    private Webcam webcam;
    private DataOutputStream dataOutputStream;
    private ClientWebcamSender(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        clientImageSender(clientSocket, webcam, dataOutputStream);
    }
    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ClientWebcamSender clientWebcamSender;
        public Builder(Socket clientSocket)
        {
            clientWebcamSender = new ClientWebcamSender(clientSocket);
        }
        public Builder setDataOutputStream(Socket clientSocket) throws IOException
        {
            clientWebcamSender.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            return this;
        }
        public Builder setConnector(String connector)
        {
            clientWebcamSender.connector = connector;
            return this;
        }
        public Builder setWebcam(Webcam webcam)
        {
            clientWebcamSender.webcam = webcam;
            return this;
        }
        public ClientWebcamSender create() {
            return clientWebcamSender;
        }
    }
}
