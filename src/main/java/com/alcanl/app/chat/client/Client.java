package com.alcanl.app.chat.client;

import java.net.Socket;

public abstract class Client extends Thread{

    protected Socket clientSocket;
    protected String connector;
    protected abstract void connect();
    @Override
    public void run()
    {
        super.run();
    }
}
