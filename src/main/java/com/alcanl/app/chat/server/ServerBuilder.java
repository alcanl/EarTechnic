package com.alcanl.app.chat.server;

import com.karandev.util.console.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.alcanl.app.chat.thread.ConnectionHandler.threadServerReceiver;
import static com.alcanl.app.chat.thread.ConnectionHandler.threadServerSender;

public class ServerBuilder {
    private String connector;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    public static final Scanner kb = new Scanner(System.in);
    ServerBuilder()
    {

    }
    ServerBuilder setConnector(String connector)
    {
        this.connector = connector;
        return this;
    }
    ServerBuilder setBufferedReader(Socket server)
    {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream(), StandardCharsets.UTF_8));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
        return this;
    }
    ServerBuilder setPrintWriter(Socket server)
    {
        try {
            this.printWriter = new PrintWriter(server.getOutputStream(), true, StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
        return this;
    }
    PrintWriter getPrintWriter()
    {
        return this.printWriter;
    }
    BufferedReader getBufferedReader()
    {
        return this.bufferedReader;
    }
    void connect(ServerSocket serverSocket, Socket clientSocket)
    {
        threadServerSender(serverSocket,clientSocket, printWriter, kb).start();
        threadServerReceiver(serverSocket, clientSocket, bufferedReader, connector).start();
    }
}
