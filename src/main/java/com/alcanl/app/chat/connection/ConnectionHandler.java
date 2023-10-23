package com.alcanl.app.chat.connection;

import com.alcanl.app.global.ImageDisplayPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.karandev.util.console.Console;

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
    private static void takeImageLoop(ImageDisplayPanel imageDisplayPanel, DataInputStream dataInputStream) throws IOException
    {
        while (true) {
            int frameWidth = dataInputStream.readInt();
            int frameHeight = dataInputStream.readInt();
            int[] pixelData = new int[frameWidth * frameHeight];

            for (int i = 0; i < pixelData.length; i++) {
                pixelData[i] = dataInputStream.readInt();
            }
            BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
            frame.setRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

            imageDisplayPanel.setBackground(frame);
        }
    }
    private static void sendImageLoop(Webcam webcam, DataOutputStream dataOutputStream) throws IOException
    {
        while (true) {
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
    }
    private static void sendMessageLoop(PrintWriter printWriter, Scanner kb, String connector)
    {
        while (true) {
            printWriter.println(connector + ": " + kb.nextLine());
        }
    }
    private static void takeMessageLoop(String message, BufferedReader bufferedReader) throws IOException
    {
        while (message != null) {
            Console.writeLine(message);
            message = bufferedReader.readLine();
        }
    }
    public static void clientReceiverCallback(Socket clientSocket, BufferedReader bufferedReader) throws IOException
    {
        try(clientSocket; bufferedReader) {
            takeMessageLoop(bufferedReader.readLine(), bufferedReader);
        }
    }
    public static void clientSenderCallback(Socket clientSocket, PrintWriter printWriter,
                                             Scanner kb, String connector) throws IOException
    {
        try(clientSocket; printWriter)
        {
            sendMessageLoop(printWriter, kb, connector);
        }
    }
    public static void serverReceiverCallback(ServerSocket serverSocket, Socket clientSocket,
                                               BufferedReader bufferedReader) throws IOException
    {
        try(serverSocket; clientSocket; bufferedReader) {
            takeMessageLoop(bufferedReader.readLine(), bufferedReader);

        }
    }
    public static void serverSenderCallback(ServerSocket serverSocket, Socket clientSocket,
                                             PrintWriter printWriter, Scanner kb, String connector) throws IOException
    {
        try(serverSocket; clientSocket; printWriter)
        {
            sendMessageLoop(printWriter, kb, connector);
        }
    }


    public static void serverImageReceiverCallback(ServerSocket serverSocket, Socket clientSocket,
                                                   ImageDisplayPanel imageDisplayPanel, DataInputStream dataInputStream)
            throws IOException
    {
        try(serverSocket; clientSocket; dataInputStream)
        {
            takeImageLoop(imageDisplayPanel, dataInputStream);
        }
    }
    public static void serverImageSenderCallback(ServerSocket serverSocket, Socket clientSocket,
                                                 Webcam webcam, DataOutputStream dataOutputStream) throws IOException
    {
        try(serverSocket; clientSocket; dataOutputStream)
        {
            openWebcam(webcam);
            sendImageLoop(webcam, dataOutputStream);
        }
    }
    public static void clientImageReceiverCallback(Socket socket, ImageDisplayPanel imageDisplayPanel,
                                                   DataInputStream dataInputStream) throws IOException
    {
        try(socket; dataInputStream)
        {
            takeImageLoop(imageDisplayPanel, dataInputStream);
        }
    }
    public static void clientImageSenderCallback(Socket socket, Webcam webcam, DataOutputStream dataOutputStream) throws IOException
    {
        try(socket; dataOutputStream)
        {
            openWebcam(webcam);
            sendImageLoop(webcam, dataOutputStream);
        }
    }

    public static void serverAudioReceiverCallback()
    {

    }
    public static void serverAudioSenderCallback()
    {

    }
    public static void clientAudioReceiverCallback()
    {

    }
    public static void clientAudioSenderCallback()
    {

    }
}
