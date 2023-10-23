package com.alcanl.app.stream.agent.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SingleVideoDisplayWindow {
	protected final VideoPanel videoPanel;
	protected final JFrame window;

	public SingleVideoDisplayWindow(String name,Dimension dimension) {
		super();
		this.window = new JFrame(name);
		this.videoPanel = new VideoPanel();

		this.videoPanel.setPreferredSize(dimension);
		this.window.add(videoPanel);
		this.window.pack();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setVisible(boolean visible) {
		this.window.setVisible(visible);
	}

	public void updateImage(BufferedImage image) {
		videoPanel.updateImage(image);
	}
	
	public void close(){
		window.dispose();
		videoPanel.close();
	}
}
