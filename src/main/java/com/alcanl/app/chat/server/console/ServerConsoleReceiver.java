package com.alcanl.app.chat.server.console;

import com.alcanl.app.chat.server.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import static com.alcanl.app.chat.connection.ConnectionHandler.serverMessageReceiver;

public class ServerConsoleReceiver extends Server {
    private BufferedReader bufferedReader;

    private ServerConsoleReceiver(ServerSocket serverSocket, Socket clientSocket)
    {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        serverMessageReceiver(serverSocket, clientSocket, bufferedReader);
    }
    @Override
    public void run()
    {
        connect();
    }

    public static class Builder {
        private static ServerConsoleReceiver serverConsoleReceiver;
        public Builder(ServerSocket serverSocket, Socket clientSocket)
        {
            serverConsoleReceiver = new ServerConsoleReceiver(serverSocket, clientSocket);
        }
        public Builder setBufferedReader(Socket server) throws IOException
        {
            serverConsoleReceiver.bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream(), StandardCharsets.UTF_8));
            return this;
        }
        public ServerConsoleReceiver create()
        {
            return serverConsoleReceiver;
        }
    }
}
