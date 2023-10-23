package com.alcanl.app.chat.server;

import com.alcanl.app.global.Resources;
import com.github.sarxos.webcam.Webcam;
import com.karandev.util.console.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private ServerSocket serverSocket;
    private ServerSocket serverWebcamSocket;
    private Socket clientSocket;
    private Socket clientWebcamSocket;
    private final ServerBuilder serverBuilder;
    public static final int PORT_CHAT = 19450;
    public static final int PORT_WEBCAM = 19460;
    public static final String IP_ADDRESS = "192.168.2.108";
    private Server()
    {
        serverBuilder = new ServerBuilder();

        try {
            serverSocket = new ServerSocket(PORT_CHAT);
            serverWebcamSocket = new ServerSocket(PORT_WEBCAM);
            Console.writeLine("Server waiting for the client connection...");
            clientSocket = serverSocket.accept();
            clientWebcamSocket = serverWebcamSocket.accept();
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
    public BufferedReader getBufferedReader()
    {
        return this.serverBuilder.getBufferedReader();
    }
    public PrintWriter getPrintWriter()
    {
        return this.serverBuilder.getPrintWriter();
    }
    public static Server of(String nickName)
    {
        var server = new Server();

        server.serverBuilder.setConnector(nickName).setPrintWriter(server.clientSocket)
                .setBufferedReader(server.clientSocket).setWebcam(Webcam.getDefault())
                .setDataInputStream(server.clientWebcamSocket).setDataOutputStream(server.clientWebcamSocket);

        return server;
    }
    public void startConnection()
    {
        this.serverBuilder.connectForChat(serverSocket, clientSocket);
        this.serverBuilder.connectForWebcam(serverWebcamSocket, clientWebcamSocket);
    }
}
