package com.alcanl.app.chat.server;

import com.karandev.util.console.Console;

import static com.alcanl.app.chat.thread.ThreadHandler.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {
    private final Scanner kb = new Scanner(System.in);
    private final int port;
    private void doWorkWithSockets(ServerSocket serverSocket, Socket clientSocket, PrintWriter printWriter,
                                   BufferedReader bufferedReader)
    {
            threadServerSender(serverSocket,clientSocket,printWriter, kb).start();
            threadServerReceiver(serverSocket, clientSocket, bufferedReader, "Client: ").start();
    }
    private Server(int port)
    {
        this.port = port;
    }
    public static Server of(int port)
    {
        return new Server(port);
    }
    public void run(){

        try {
            var serverSocket = new ServerSocket(port);
            var clientSocket = serverSocket.accept();
            Console.writeLine("Another device has connected to room. Say hi!");
            var printWriter = new PrintWriter(clientSocket.getOutputStream(), false, StandardCharsets.UTF_8);
            var bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            doWorkWithSockets(serverSocket, clientSocket, printWriter, bufferedReader);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
