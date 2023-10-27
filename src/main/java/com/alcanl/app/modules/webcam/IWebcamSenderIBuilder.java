package com.alcanl.app.modules.webcam;

import com.alcanl.app.modules.IBuilder;
import com.github.sarxos.webcam.Webcam;

import java.io.IOException;
import java.net.Socket;

public interface IWebcamSenderIBuilder extends IBuilder {
    IBuilder setDataOutputStream(Socket clientSocket) throws IOException;
    IBuilder setWebcam(Webcam webcam);
}
