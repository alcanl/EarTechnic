package com.alcanl.app.chat.client;

import static com.alcanl.app.chat.thread.ThreadHandler.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private final int port;
    private final String ip_address;
    private final Scanner kb = new Scanner(System.in);

    private Client(int port, String ip_address)
    {
        this.port = port;
        this.ip_address = ip_address;
    }

    public static Client of(String ip_address, int port) {
        return new Client(port, ip_address);
    }

    private void doWorkWithSockets(Socket clientSocket, PrintWriter printWriter,
                                   BufferedReader bufferedReader)
    {
            threadClientSender(clientSocket,printWriter, kb).start();
            threadClientReceiver(clientSocket, bufferedReader, "Server: ").start();
    }
    public void run() {

        try {
            var clientSocket = new Socket(ip_address, port);
            var printWriter = new PrintWriter(clientSocket.getOutputStream(), false, StandardCharsets.UTF_8);
            var bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            doWorkWithSockets(clientSocket, printWriter, bufferedReader);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
