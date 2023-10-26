package com.alcanl.app.chat.client.console;

import com.alcanl.app.chat.client.Client;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static com.alcanl.app.chat.connection.ConnectionHandler.clientMessageSender;

public class ClientConsoleSender extends Client {
    private PrintWriter printWriter;
    private static final Scanner kb = new Scanner(System.in);
    private ClientConsoleSender(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }
    @Override
    public void connect()
    {
        clientMessageSender(clientSocket, printWriter, kb, connector);
    }
    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ClientConsoleSender clientConsoleSender;
        public Builder(Socket clientSocket)
        {
            clientConsoleSender = new ClientConsoleSender( clientSocket);
        }
        public ClientConsoleSender.Builder setPrintWriter(Socket clientSocket) throws IOException
        {
            clientConsoleSender.printWriter = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            return this;
        }
        public ClientConsoleSender.Builder setConnector(String connector)
        {
            clientConsoleSender.connector = connector;
            return this;
        }
        public ClientConsoleSender create() {
            return clientConsoleSender;
        }
    }
}


