package com.alcanl.app.chat;

import com.alcanl.app.chat.client.Client;
import com.alcanl.app.chat.server.Server;
import com.karandev.util.console.Console;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;


public class ChatApplication {
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
    private static int createPortNumber()
    {
        var random = new Random();
        return random.nextInt(1000, 10000);
    }
    private static void createServer()
    {
        var port = createPortNumber();
        var server = Server.of(port);
        Console.writeLine("The Server is ready, here is your ip address and port number :\nip: %s\nport: %d", getIpAddress(), port);
        server.run();
    }
    private static String getIpAddress()
    {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    final String ip = addr.getHostAddress();
                    if(Inet4Address.class == addr.getClass()) return ip;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private static void help()
    {
        Console.writeLine("With usage of 'S' command you can be a host for a chat room and invite your friends with your ip address and port number.");
        Console.writeLine("With usage of 'C' command you can join a chat room with ip address and port number that the host created before ");
    }

    private static void joinServer()
    {
        var ip = Console.readLine("Please input the ip address: ");
        var port = Console.readInt("Please input the port number of the host : ");
        Console.writeLine("Connection establishing....\nConnection verified.");
        var client = Client.of(ip, port);
        client.run();
    }

}
