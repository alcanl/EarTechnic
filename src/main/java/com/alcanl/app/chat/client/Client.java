package com.alcanl.app.chat.client;

import com.alcanl.app.chat.server.Server;
import com.alcanl.app.global.Resources;
import com.github.sarxos.webcam.Webcam;
import com.karandev.util.console.Console;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private Socket clientWebcamSocket;
    private final ClientBuilder clientBuilder;
    private Client()
    {
        clientBuilder = new ClientBuilder();

        try {
            clientSocket = new Socket(Server.IP_ADDRESS, Server.PORT_CHAT);
            clientWebcamSocket = new Socket(Server.IP_ADDRESS, Server.PORT_WEBCAM);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public Socket getClientSocket()
    {
        return clientSocket;
    }
    public void startConnection()
    {
        this.clientBuilder.connectForChat(this.clientSocket);
        this.clientBuilder.connectForWebcam(this.clientWebcamSocket);
    }
    public PrintWriter getPrintWriter()
    {
        return this.clientBuilder.getPrintWriter();
    }
    public BufferedReader getBufferedReader()
    {
        return this.clientBuilder.getBufferedReader();
    }
    public DataInputStream getDataInputStream()
    {
        return this.clientBuilder.getDataInputStream();
    }
    public DataOutputStream getDataOutputStream()
    {
        return this.clientBuilder.getDataOutputStream();
    }
    public static Client of(String nickName)
    {
        var client = new Client();
        client.clientBuilder.setConnector(nickName).setPrintWriter(client.clientSocket).setBufferedReader(client.clientSocket)
                .setDataOutputStream(client.clientWebcamSocket)
                .setDataInputStream(client.clientWebcamSocket)
                .setWebcam(Webcam.getDefault());

        return client;
    }
}


