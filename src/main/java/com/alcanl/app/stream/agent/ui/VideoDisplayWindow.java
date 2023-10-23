package com.alcanl.app.stream.agent.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoDisplayWindow {
	protected final DoubleVideoPanel videoPanel;
	protected final JFrame window;

	public VideoDisplayWindow(String name,Dimension dimension) {
		super();
		this.window = new JFrame(name);
		this.videoPanel = new DoubleVideoPanel();

		this.videoPanel.setPreferredSize(dimension);
		this.window.add(videoPanel);
		this.window.pack();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setVisible(boolean visible) {
		this.window.setVisible(visible);
	}

	public void updateBigVideo(BufferedImage image) {
		videoPanel.updateBigImage(image);
	}

	public void updateSmallVideo(BufferedImage image) {
		videoPanel.updateSmallImage(image);
	}
	
	public void close(){
		window.dispose();
		videoPanel.close();
	}
}
