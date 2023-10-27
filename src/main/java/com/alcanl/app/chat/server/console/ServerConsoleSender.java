package com.alcanl.app.chat.server.console;

import com.alcanl.app.chat.server.Server;
import com.alcanl.app.chat.server.ServerBuilder;
import com.alcanl.app.modules.console.IConsoleSenderBuilder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static com.alcanl.app.chat.connection.ConnectionHandler.serverMessageSender;

public class ServerConsoleSender extends Server {
    private PrintWriter printWriter;
    private static final Scanner kb = new Scanner(System.in);
    public static final int PORT = 19420;

    private ServerConsoleSender(ServerSocket serverSocket, Socket clientSocket)
    {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }
    @Override
    public void run()
    {
        connect();
    }
    @Override
    public void connect()
    {
        serverMessageSender(serverSocket, clientSocket, printWriter, kb, connector);
    }
    public static class Builder extends ServerBuilder implements IConsoleSenderBuilder {
        public Builder(ServerSocket serverSocket, Socket clientSocket)
        {
            server = new ServerConsoleSender(serverSocket, clientSocket);
        }
        @Override
        public Builder setPrintWriter(Socket clientSocket) throws IOException
        {
            ((ServerConsoleSender)server).printWriter = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
            return this;
        }
        @Override
        public Builder setConnector(String connector)
        {
            ((ServerConsoleSender)server).connector = connector;
            return this;
        }
        @Override
        public ServerConsoleSender create()
        {
            return ((ServerConsoleSender)server);
        }
    }
}
