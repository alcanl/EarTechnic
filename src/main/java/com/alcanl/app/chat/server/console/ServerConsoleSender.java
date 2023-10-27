package com.alcanl.app.chat.server.console;

import com.alcanl.app.chat.server.Server;
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
    public static class Builder {
        private static ServerConsoleSender serverConsoleSender;
        public Builder(ServerSocket serverSocket, Socket clientSocket)
        {
            serverConsoleSender = new ServerConsoleSender(serverSocket, clientSocket);
        }
        public Builder setPrintWriter(Socket server) throws IOException
        {
            serverConsoleSender.printWriter = new PrintWriter(server.getOutputStream(), true, StandardCharsets.UTF_8);
            return this;
        }
        public Builder setConnector(String connector)
        {
            serverConsoleSender.connector = connector;
            return this;
        }
        public ServerConsoleSender create()
        {
            return serverConsoleSender;
        }
    }
}
