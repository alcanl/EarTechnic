package com.alcanl.app.chat.server;

import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server extends Thread{
    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    protected String connector;
    public static final String IP_ADDRESS = "192.168.2.69";
    protected abstract void connect();
    @Override
    public void run()
    {
        super.run();
    }
}
