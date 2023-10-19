package com.alcanl.app.chat.server;

import com.karandev.util.console.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final ServerBuilder serverBuilder;
    public static final int PORT = 19440;
    public static final String IP_ADDRESS = "192.168.2.68";
    private Server()
    {
        serverBuilder = new ServerBuilder();

        try {
            serverSocket = new ServerSocket(PORT);
            Console.writeLine("Server waiting for the clients connection...");
            clientSocket = serverSocket.accept();
            Console.writeLine("The client has connected.");
        }
        catch (SocketTimeoutException ex)
        {
            Console.writeLine("Socket timeout has ended while waiting process of the client");
        }
        catch (SecurityException ex)
        {
            Console.writeLine("Security Exception occurs");
        }
        catch (IOException ex)
        {
            Console.writeLine("IO Exception Occur");
        }
    }
    public static Server of(String nickName)
    {
        var server = new Server();

        server.serverBuilder.setConnector(nickName).setPrintWriter(server.clientSocket).setBufferedReader(server.clientSocket);

        return server;
    }
    public PrintWriter getPrintWriter()
    {
        return this.serverBuilder.getPrintWriter();
    }
    public BufferedReader getBufferedReader()
    {
        return this.serverBuilder.getBufferedReader();
    }
    public void startConnection()
    {
        this.serverBuilder.connect(serverSocket, clientSocket);
    }
}
