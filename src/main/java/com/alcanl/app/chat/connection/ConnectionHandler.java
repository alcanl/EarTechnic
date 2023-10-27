package com.alcanl.app.chat.connection;

import com.alcanl.app.global.ImageDisplayPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.karandev.util.console.Console;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public final class ConnectionHandler {
    private ConnectionHandler()
    {

    }
    private static void openWebcam(Webcam webcam)
    {
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
    }
    private static void sendAudioLoop()
    {

    }
    private static void takeAudioLoop()
    {

    }
    private static void takeImage(ImageDisplayPanel imageDisplayPanel, DataInputStream dataInputStream) throws IOException
    {
        int frameWidth = dataInputStream.readInt();
        int frameHeight = dataInputStream.readInt();
        int[] pixelData = new int[frameWidth * frameHeight];

        for (int i = 0; i < pixelData.length; i++)
            pixelData[i] = dataInputStream.readInt();

        BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
        frame.setRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

        imageDisplayPanel.setBackground(frame);
    }
    private static void sendImage(Webcam webcam, DataOutputStream dataOutputStream) throws IOException
    {
        BufferedImage frame = webcam.getImage();

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        dataOutputStream.writeInt(frameWidth);
        dataOutputStream.writeInt(frameHeight);

        int[] pixelData = new int[frameWidth * frameHeight];
        frame.getRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);
        for (int pixelDatum : pixelData)
            dataOutputStream.writeInt(pixelDatum);

    }
    private static void sendMessage(PrintWriter printWriter, Scanner kb, String connector)
    {
        printWriter.println(connector + ": " + kb.nextLine());
    }
    private static void takeMessage(BufferedReader bufferedReader) throws IOException
    {
        Console.writeLine(bufferedReader.readLine());

    }
    public static void clientMessageReceiver(Socket clientSocket, BufferedReader bufferedReader)
    {
        try(clientSocket; bufferedReader) {
            while (true) {
                takeMessage(bufferedReader);
            }
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public static void clientMessageSender(Socket clientSocket, PrintWriter printWriter,
                                           Scanner kb, String connector)
    {
        try(clientSocket; printWriter) {
            while (true) {
                sendMessage(printWriter, kb, connector);
            }
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public static void serverMessageReceiver(ServerSocket serverSocket, Socket clientSocket,
                                              BufferedReader bufferedReader)
    {
        try(serverSocket) {
            clientMessageReceiver(clientSocket, bufferedReader);
        }
        catch(IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }

    public static void serverMessageSender(ServerSocket serverSocket, Socket clientSocket,
                                           PrintWriter printWriter, Scanner kb, String connector)
    {
        try (serverSocket) {
            clientMessageSender(clientSocket, printWriter, kb, connector);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }


    public static void serverImageReceiver(ServerSocket serverSocket, Socket clientSocket,
                                           ImageDisplayPanel imageDisplayPanel, DataInputStream dataInputStream)
    {
        try(serverSocket) {
            clientImageReceiver(clientSocket, imageDisplayPanel, dataInputStream);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public static void serverImageSender(ServerSocket serverSocket, Socket clientSocket,
                                         Webcam webcam, DataOutputStream dataOutputStream)
    {
        try(serverSocket)
        {
            clientImageSender(clientSocket, webcam, dataOutputStream);
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public static void clientImageReceiver(Socket socket, ImageDisplayPanel imageDisplayPanel,
                                                   DataInputStream dataInputStream)
    {
        try(socket; dataInputStream) {
        while (true) {
                takeImage(imageDisplayPanel, dataInputStream);
            }
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }
    public static void clientImageSender(Socket socket, Webcam webcam, DataOutputStream dataOutputStream)
    {
        openWebcam(webcam);
        try(socket; dataOutputStream) {
            while (true) {
                sendImage(webcam, dataOutputStream);
            }
        }
        catch (IOException ex)
        {
            Console.writeLine(ex.getMessage());
        }
    }

    public static void serverAudioReceiver()
    {

    }
    public static void serverAudioSender()
    {

    }
    public static void clientAudioReceiver()
    {

    }
    public static void clientAudioSender()
    {

    }
}
