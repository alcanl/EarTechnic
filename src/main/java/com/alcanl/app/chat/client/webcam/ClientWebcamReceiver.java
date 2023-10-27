package com.alcanl.app.chat.client.webcam;

import com.alcanl.app.chat.client.Client;
import com.alcanl.app.global.ImageDisplayPanel;
import com.alcanl.app.global.Resources;
import com.karandev.util.console.Console;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import static com.alcanl.app.chat.connection.ConnectionHandler.clientImageReceiver;

public class ClientWebcamReceiver extends Client {
    private DataInputStream dataInputStream;
    private  ImageDisplayPanel imageDisplayPanel;
    private ClientWebcamReceiver(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }
    @Override
    protected void connect()
    {
        clientImageReceiver(clientSocket, imageDisplayPanel, dataInputStream);
    }
    @Override
    public void run()
    {
        connect();
    }
    public static class Builder {
        private static ClientWebcamReceiver clientWebcamReceiver;
        public Builder(Socket clientSocket)
        {
            clientWebcamReceiver = new ClientWebcamReceiver(clientSocket);
        }
        public Builder setDataInputStream(Socket clientSocket)
        {
            try {
                clientWebcamReceiver.dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            }
            catch (IOException ex)
            {
                Console.writeLine(ex.getMessage());
            }
            return this;
        }
        public Builder setConnector(String connector)
        {
            clientWebcamReceiver.connector = connector;
            return this;
        }
        public Builder setImageDisplayPanel(ImageDisplayPanel imageDisplayPanel)
        {
            clientWebcamReceiver.imageDisplayPanel = imageDisplayPanel;
            Resources.setJFrame(clientWebcamReceiver.imageDisplayPanel);

            return this;
        }
        public ClientWebcamReceiver create() {
            return clientWebcamReceiver;
        }
    }
}
