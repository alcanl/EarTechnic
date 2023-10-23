package com.alcanl.app.stream.handler;

import java.awt.image.BufferedImage;

public interface StreamFrameListener {
    void onFrameReceived(BufferedImage image);
}
