package com.alcanl.app.stream.agent;

import com.github.sarxos.webcam.Webcam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alcanl.app.stream.agent.ui.SingleVideoDisplayWindow;
import com.alcanl.app.stream.handler.StreamFrameListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;

public class StreamClient {
	private final static Dimension dimension = new Dimension(320,240);
	private final static SingleVideoDisplayWindow displayWindow = new SingleVideoDisplayWindow("Stream example",dimension);
	protected final static Logger logger = LoggerFactory.getLogger(StreamClient.class);
	public static void run(String ipAddress, int port) {
		//set up the videoWindow
		displayWindow.setVisible(true);
		
		//set up the connection
		logger.info("setup dimension :{}",dimension);
		StreamClientAgent clientAgent = new StreamClientAgent(new StreamFrameListenerIMPL(),dimension);
		clientAgent.connect(new InetSocketAddress(ipAddress, port));


		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();
		Dimension dimension2 = new Dimension(320, 240);
		webcam.setViewSize(dimension2);
		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension2); // get set ip and host
		serverAgent.start(new InetSocketAddress(ipAddress, port));
	}
	
	
	protected static class StreamFrameListenerIMPL implements StreamFrameListener{
		private volatile long count = 0;
		@Override
		public void onFrameReceived(BufferedImage image) {
			logger.info("frame received :{}",count++);
			displayWindow.updateImage(image);			
		}
		
	}
	

}
