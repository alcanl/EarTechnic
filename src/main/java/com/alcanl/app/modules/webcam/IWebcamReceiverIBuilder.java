package com.alcanl.app.modules.webcam;

import com.alcanl.app.global.ImageDisplayPanel;
import com.alcanl.app.modules.IBuilder;

import java.net.Socket;

public interface IWebcamReceiverIBuilder extends IBuilder {
    IBuilder setDataInputStream(Socket socket);
    IBuilder setImageDisplayPanel(ImageDisplayPanel imageDisplayPanel);

}
