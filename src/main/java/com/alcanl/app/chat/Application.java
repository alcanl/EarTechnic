package com.alcanl.app.chat;

import com.alcanl.app.chat.client.console.ClientConsoleSender;
import com.alcanl.app.chat.client.console.ClientConsoleReceiver;
import com.alcanl.app.chat.client.webcam.ClientWebcamReceiver;
import com.alcanl.app.chat.client.webcam.ClientWebcamSender;
import com.alcanl.app.chat.server.Server;
import com.alcanl.app.chat.server.console.ServerConsoleReceiver;
import com.alcanl.app.chat.server.console.ServerConsoleSender;
import com.alcanl.app.chat.server.webcam.ServerWebcamReceiver;
import com.alcanl.app.chat.server.webcam.ServerWebcamSender;
import com.alcanl.app.global.ImageDisplayPanel;
import com.github.sarxos.webcam.Webcam;
import com.karandev.util.console.Console;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {
    public static void run()
    {
        Console.writeLine("""
                Welcome to ConsoleChatApp
                Please select your joiner type:
                Server[S]/Joiner[J]
                If you need more information, you can try '-help' command for usage.""");

        while (true) {

            var command = Console.readLine();

            if (command.equals("-help"))
                help();

            else if (command.equalsIgnoreCase("S")) {
                try {
                    if (Console.readLine("For Console press 'C', for Webcam press 'W'").equalsIgnoreCase("C"))
                        createChatServer();
                    else
                        createWebcamServer();
                    break;
                }
                catch (IOException ex)
                {
                    Console.writeLine(ex.getMessage());
                }
            }

            else if (command.equalsIgnoreCase("J"))
            {
                try {
                    if (Console.readLine("To join for a console room press 'C', for a webcam room press 'W'").equalsIgnoreCase("C"))
                        joinChatServer();
                    else
                        joinWebcamServer();
                    break;
                }
                catch (IOException ex)
                {
                    Console.writeLine(ex.getMessage());
                }
            }
            else
                Console.writeLine("Invalid command!");
            }
        }
    private static void createChatServer() throws IOException
    {
        var serverSocket = new ServerSocket(ServerConsoleSender.PORT);
        var clientSocket = serverSocket.accept();

        var serverReceiver =  new ServerConsoleReceiver.Builder(serverSocket, clientSocket)
                .setBufferedReader(clientSocket).create();

        var serverSender = new ServerConsoleSender.Builder( serverSocket, clientSocket)
                .setPrintWriter(clientSocket).setConnector("SERVER: ").create();



        serverSender.start();
        serverReceiver.start();


    }
    private static void createWebcamServer() throws IOException
    {
        var serverSocket = new ServerSocket(ServerWebcamSender.PORT);
        var clientSocket = serverSocket.accept();

        var serverWebcamSender = new ServerWebcamSender.Builder(serverSocket, clientSocket)
                .setWebcam(Webcam.getDefault()).setDataOutputStream(clientSocket).create();

        var serverWebcamReceiver = new ServerWebcamReceiver.Builder(serverSocket, clientSocket)
                .setDataInputStream(clientSocket).setImageDisplayPanel(new ImageDisplayPanel()).create();

        serverWebcamReceiver.start();
        serverWebcamSender.start();
    }
    private static void joinWebcamServer()
    {
        try {
            var clientSocketWebcam = new Socket(Server.IP_ADDRESS, ServerWebcamSender.PORT);

            var clientWebcamReceiver = new ClientWebcamReceiver.Builder(clientSocketWebcam).setConnector("GUEST: ")
                    .setDataInputStream(clientSocketWebcam).setImageDisplayPanel(new ImageDisplayPanel()).create();

            var clientWebcamSender = new ClientWebcamSender.Builder(clientSocketWebcam).setWebcam(Webcam.getDefault())
                    .setDataOutputStream(clientSocketWebcam).create();

            clientWebcamSender.start();
            clientWebcamReceiver.start();

            Console.writeLine("Connection establishing....\nConnection verified.");
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }

    }

    private static void help()
    {
        Console.writeLine("With usage of 'S' command you can be a host for a chat room and invite your friends with your ip address and port number.");
        Console.writeLine("With usage of 'C' command you can join a chat room with ip address and port number that the host created before ");
    }

    private static void joinChatServer() throws IOException
    {
        var clientSocket = new Socket(Server.IP_ADDRESS, ServerConsoleSender.PORT);


        var clientSender = new ClientConsoleSender.Builder(clientSocket)
                .setConnector("GUEST: ").setPrintWriter(clientSocket).create();

        var clientReceiver = new ClientConsoleReceiver.Builder(clientSocket).setConnector("GUEST: ")
                        .setBufferedReader(clientSocket).create();

        clientReceiver.start();
        clientSender.start();
        Console.writeLine("Connection establishing....\nConnection verified.");

    }

}
