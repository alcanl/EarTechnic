package com.alcanl.app.chat.server;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWebcamHandler
{
    public static void main (String[] args) throws IOException, ClassNotFoundException
    {
        ServerSocket server = new ServerSocket(19420);
        Socket socket = server.accept();

        JFrame jframe = new JFrame();
        jframe.setSize(800, 600);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BorderLayout());

        ImageDisplayPanel imageDisplayPanel = new ImageDisplayPanel();
        jframe.add(imageDisplayPanel, BorderLayout.CENTER);

        jframe.setVisible(true);

        try (DataInputStream rcv = new DataInputStream(new BufferedInputStream(socket.getInputStream())))
        {

            while (true)
            {
                int frameWidth = rcv.readInt();
                int frameHeight = rcv.readInt();

                int[] pixelData = new int[frameWidth * frameHeight];

                for (int i = 0; i < pixelData.length; i++)
                {
                    pixelData[i] = rcv.readInt();
                }

                BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
                frame.setRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

                imageDisplayPanel.setBackground(frame);
            }
        }
    }


    private static class ImageDisplayPanel extends JPanel
    {
        private static final Object BACKGROUND_LOCK = new Object();
        private BufferedImage background = null;

        public ImageDisplayPanel () throws HeadlessException
        {
            this.setDoubleBuffered(true); //to avoid flicker
        }

        public void setBackground (Image newBackground)
        {
            synchronized (BACKGROUND_LOCK)
            {
                if (background == null)
                {
                    background = new BufferedImage(newBackground.getWidth(null), newBackground.getHeight(null), BufferedImage.TYPE_INT_RGB);
                }
                else if (background.getWidth() != newBackground.getWidth(null) || background.getHeight() != newBackground.getHeight(null))
                {
                    background.flush();//flush old resources first
                    background = new BufferedImage(newBackground.getWidth(null), newBackground.getHeight(null), BufferedImage.TYPE_INT_RGB);
                }
                Graphics graphics = background.createGraphics();
                graphics.drawImage(newBackground, 0, 0, null);
            }
            repaint();
        }

        @Override
        public void paint (Graphics g)
        {
            super.paint(g);
            synchronized (BACKGROUND_LOCK)
            {
                if (background != null)
                {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                }
            }
        }
    }
}
