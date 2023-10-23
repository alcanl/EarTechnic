package com.alcanl.app.global;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class Resources {
    private Resources()
    {

    }
    public static String getIpAddress()
    {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iFace = interfaces.nextElement();
                if (iFace.isLoopback() || !iFace.isUp() || iFace.isVirtual() || iFace.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iFace.getInetAddresses();
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
    public static void setJFrame(ImageDisplayPanel imageDisplayPanel)
    {
        JFrame jframe = new JFrame();
        jframe.setSize(800, 600);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLayout(new BorderLayout());
        jframe.add(imageDisplayPanel, BorderLayout.CENTER);
        jframe.setVisible(true);
    }
}
