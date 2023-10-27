package com.alcanl.app.modules.console;

import com.alcanl.app.modules.IBuilder;

import java.io.IOException;
import java.net.Socket;

public interface IConsoleSenderBuilder extends IBuilder {
    IBuilder setPrintWriter(Socket socket) throws IOException;
}
