package com.alcanl.app.chat.client;

import com.alcanl.app.global.ImageDisplayPanel;
import com.alcanl.app.global.Resources;
import com.github.sarxos.webcam.Webcam;
import com.karandev.util.console.Console;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static com.alcanl.app.chat.connection.ConnectionHandler.*;

class ClientBuilder {
    private String connector;
    private Webcam webcam;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private static final ImageDisplayPanel imageDisplayPanel = new ImageDisplayPanel();
    public static final Scanner kb = new Scanner(System.in);
    ClientBuilder()
    {
        Resources.setJFrame(imageDisplayPanel);
    }
    void connectForChat(Socket clientSocket)
    {
        new Thread(() -> {
            try {
                clientSenderCallback(clientSocket, printWriter, kb, connector);
            } catch (IOException ex) {
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                clientReceiverCallback(clientSocket, bufferedReader);
            } catch (IOException ex) {
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();
    }
    void connectForWebcam(Socket clientSocket)
    {
        new Thread(() -> {
            try {
                clientImageSenderCallback(clientSocket, webcam, dataOutputStream);
            } catch (IOException ex) {
                webcam.close();
                Console.writeLine("The connection has lost. Cause: %s", ex.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                clientImageReceiverCallback(clientSocket, new ImageDisplayPanel(), dataInputStream);
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
    DataInputStream getDataInputStream()
    {
        return this.dataInputStream;
    }
    DataOutputStream getDataOutputStream()
    {
        return this.dataOutputStream;
    }
    ClientBuilder setBufferedReader(Socket clientSocket)
    {
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
        return this;
    }
    ClientBuilder setConnector(String connector)
    {
        this.connector = connector;
        return this;
    }

    ClientBuilder setPrintWriter(Socket clientSocket)
    {
        try {
            this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
        return this;
    }
    ClientBuilder setDataOutputStream(Socket clientWebcamSocket)
    {
        try {
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientWebcamSocket.getOutputStream()));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }

        return this;
    }
    ClientBuilder setDataInputStream(Socket clientWebcamSocket)
    {
        try {
            this.dataInputStream = new DataInputStream(new BufferedInputStream(clientWebcamSocket.getInputStream()));
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }

        return this;
    }
    ClientBuilder setWebcam(Webcam webcam)
    {
        this.webcam = webcam;

        return this;
    }
}
