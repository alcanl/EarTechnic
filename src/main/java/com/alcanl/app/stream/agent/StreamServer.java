package com.alcanl.app.stream.agent;

import com.github.sarxos.webcam.Webcam;

import java.awt.*;
import java.net.InetSocketAddress;


public class StreamServer {
	public static void run(String ipAddress, int port) {
		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();
		Dimension dimension = new Dimension(320, 240);
		webcam.setViewSize(dimension);

		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension);
		serverAgent.start(new InetSocketAddress(ipAddress, port));
	}

}
