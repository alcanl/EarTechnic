package com.alcanl.app.chat.client;

import com.alcanl.app.chat.server.Server;
import com.alcanl.app.global.Resources;
import com.karandev.util.console.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private final String ip_address;
    private final ClientBuilder clientBuilder;
    private Client()
    {
        clientBuilder = new ClientBuilder();
        ip_address = Resources.getIpAddress();

        try {
            clientSocket = new Socket(Server.IP_ADDRESS, Server.PORT);
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
    public String getIp_address()
    {
        return ip_address;
    }
    public void startConnection()
    {
        this.clientBuilder.connect(this.clientSocket);
    }
    public PrintWriter getPrintWriter()
    {
        return this.clientBuilder.getPrintWriter();
    }
    public BufferedReader getBufferedReader()
    {
        return this.clientBuilder.getBufferedReader();
    }
    public static Client of(String nickName) {

        var client = new Client();
        client.clientBuilder.setConnector(nickName).setPrintWriter(client).setBufferedReader(client);
        return client;
    }
}


