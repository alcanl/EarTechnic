package com.alcanl.app.chat.client;

import com.karandev.util.console.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.alcanl.app.chat.thread.ConnectionHandler.threadClientReceiver;
import static com.alcanl.app.chat.thread.ConnectionHandler.threadClientSender;

class ClientBuilder {
    private String connector;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    public static final Scanner kb = new Scanner(System.in);
    ClientBuilder()
    {

    }
    String getConnector()
    {
        return connector;
    }
    ClientBuilder setConnector(String connector)
    {
        this.connector = connector;
        return this;
    }
    ClientBuilder setBufferedReader(Client client)
    {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream(), StandardCharsets.UTF_8));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
        return this;
    }
    ClientBuilder setPrintWriter(Client client)
    {
        try {
            this.printWriter = new PrintWriter(client.getClientSocket().getOutputStream(), true, StandardCharsets.UTF_8);
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
    void connect(Socket clientSocket)
    {
        threadClientSender(clientSocket,printWriter, kb).start();
        threadClientReceiver(clientSocket, bufferedReader, connector).start();
    }

}
