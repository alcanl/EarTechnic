package com.alcanl.app.chat.server;

import com.alcanl.app.global.ImageDisplayPanel;
import com.alcanl.app.global.Resources;
import com.github.sarxos.webcam.Webcam;
import com.karandev.util.console.Console;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static com.alcanl.app.chat.connection.ConnectionHandler.*;

class ServerBuilder {
    private String connector;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private static final ImageDisplayPanel imageDisplayPanel = new ImageDisplayPanel();
    private Webcam webcam;
    public static final Scanner kb = new Scanner(System.in);
    ServerBuilder()
    {
        Resources.setJFrame(imageDisplayPanel);
    }
    void connectForChat(ServerSocket serverSocket, Socket clientSocket)
    {
        new Thread(() -> {
            try {
                serverSenderCallback(serverSocket, clientSocket, printWriter, kb, connector);
            } catch (IOException ex) {
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                serverReceiverCallback(serverSocket, clientSocket, bufferedReader);
            } catch (IOException ex) {
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();
    }
    void connectForWebcam(ServerSocket serverSocket, Socket clientSocket)
    {
        new Thread(() -> {
            try {
                serverImageSenderCallback(serverSocket, clientSocket, webcam, dataOutputStream);
            } catch (IOException ex) {
                webcam.close();
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                serverImageReceiverCallback(serverSocket, clientSocket, new ImageDisplayPanel(), dataInputStream);
            } catch (IOException ex) {
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();
    }
    BufferedReader getBufferedReader()
    {
        return this.bufferedReader;
    }
    PrintWriter getPrintWriter()
    {
        return this.printWriter;
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
    ServerBuilder setConnector(String connector)
    {
        this.connector = connector;
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
    ServerBuilder setDataOutputStream(Socket server)
    {
        try {
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(server.getOutputStream()));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }

        return this;
    }
    ServerBuilder setDataInputStream(Socket server)
    {
        try {
            this.dataInputStream = new DataInputStream(new BufferedInputStream(server.getInputStream()));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }

        return this;
    }
    ServerBuilder setWebcam(Webcam webcam)
    {
        this.webcam = webcam;
        return this;
    }
}
