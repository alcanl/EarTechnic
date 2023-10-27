package com.alcanl.app.chat.client.console;

import com.alcanl.app.chat.client.Client;
import com.alcanl.app.chat.client.ClientBuilder;
import com.alcanl.app.modules.console.IConsoleSenderBuilder;
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
    public static class Builder extends ClientBuilder implements IConsoleSenderBuilder {
        public Builder(Socket clientSocket)
        {
            client = new ClientConsoleSender( clientSocket);
        }
        @Override
        public Builder setPrintWriter(Socket clientSocket) throws IOException
        {
            ((ClientConsoleSender)client).printWriter = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            return this;
        }
        @Override
        public Builder setConnector(String connector)
        {
            ((ClientConsoleSender)client).connector = connector;
            return this;
        }
        @Override
        public ClientConsoleSender create() {
            return ((ClientConsoleSender)client);
        }
    }
}


