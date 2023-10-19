package com.alcanl.app.chat;

import com.alcanl.app.chat.client.Client;
import com.alcanl.app.chat.server.Server;
import com.karandev.util.console.Console;

public class Application {
    public static void run()
    {
        Console.writeLine("""
                Welcome to ConsoleChatApp
                Please select your joiner type:
                Server[S]/Client[C]
                If you need more information, you can try '-help' command for usage.""");

        while (true) {

            var command = Console.readLine();

            if (command.equals("-help"))
                help();

            else if (command.equals("S")) {
                createServer();
                break;
            }

            else if (command.equals("C")) {
                joinServer();
                break;
            }
            else
                Console.writeLine("Invalid command!");
            }
        }
    private static void createServer()
    {
        var server = Server.of("Server");
        server.startConnection();
    }

    private static void help()
    {
        Console.writeLine("With usage of 'S' command you can be a host for a chat room and invite your friends with your ip address and port number.");
        Console.writeLine("With usage of 'C' command you can join a chat room with ip address and port number that the host created before ");
    }

    private static void joinServer()
    {
        var client = Client.of("Guest");
        client.startConnection();
        Console.writeLine("Connection establishing....\nConnection verified.");
    }

}
