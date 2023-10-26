package com.alcanl.app.chat.client.console;

import com.alcanl.app.chat.client.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import static com.alcanl.app.chat.connection.ConnectionHandler.clientMessageReceiver;

public class ClientConsoleReceiver extends Client {
    private BufferedReader bufferedReader;
    private ClientConsoleReceiver(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        clientMessageReceiver(clientSocket, bufferedReader);
    }
    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ClientConsoleReceiver clientConsoleReceiver;
        public Builder(Socket clientSocket)
        {
            clientConsoleReceiver = new ClientConsoleReceiver(clientSocket);
        }
        public Builder setBufferedReader(Socket clientSocket) throws IOException
        {
            clientConsoleReceiver.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            return this;
        }
        public Builder setConnector(String connector)
        {
            clientConsoleReceiver.connector = connector;
            return this;
        }
        public ClientConsoleReceiver create() {
            return clientConsoleReceiver;
        }
    }
}
