package com.alcanl.app.chat.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public final class ConnectionHandler {
    private ConnectionHandler()
    {

    }
    private static void clientReceiverCallback(Socket clientSocket, BufferedReader bufferedReader, String connector)
    {
        try(clientSocket; bufferedReader) {
            takeMessageLoop(bufferedReader.readLine(), connector, bufferedReader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void clientSenderCallback(Socket clientSocket, PrintWriter printWriter, Scanner kb)
    {
        try(clientSocket; printWriter)
        {
            sendMessageLoop(printWriter, kb);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    private static void serverReceiverCallback(ServerSocket serverSocket, Socket clientSocket, BufferedReader bufferedReader, String connector)
    {
        try(serverSocket; clientSocket; bufferedReader) {
            takeMessageLoop(bufferedReader.readLine(), connector, bufferedReader);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void serverSenderCallback(ServerSocket serverSocket, Socket clientSocket, PrintWriter printWriter, Scanner kb)
    {
        try(serverSocket; clientSocket; printWriter)
        {
            sendMessageLoop(printWriter, kb);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    private static void sendMessageLoop(PrintWriter printWriter, Scanner kb)
    {
        while (true) {
            printWriter.println(kb.nextLine());
        }
    }
    private static void takeMessageLoop(String message, String connector, BufferedReader bufferedReader) throws IOException
    {
        while (message != null) {
            System.out.println(connector + message);
            message = bufferedReader.readLine();
        }
    }
    public static Thread threadServerSender(ServerSocket serverSocket, Socket clientSocket, PrintWriter printWriter, Scanner kb)
    {
        return new Thread(() -> serverSenderCallback(serverSocket, clientSocket, printWriter, kb));
    }
    public static Thread threadServerReceiver(ServerSocket serverSocket, Socket clientSocket, BufferedReader bufferedReader, String connector)
    {
        return new Thread(() -> serverReceiverCallback(serverSocket, clientSocket, bufferedReader, connector));
    }
    public static Thread threadClientSender(Socket clientSocket, PrintWriter printWriter, Scanner kb)
    {
        return new Thread(() -> clientSenderCallback(clientSocket, printWriter, kb));
    }
    public static Thread threadClientReceiver(Socket clientSocket, BufferedReader bufferedReader, String connector)
    {
        return new Thread(() -> clientReceiverCallback(clientSocket, bufferedReader, connector));
    }
}
