package com.alcanl.app.modules.console;

import com.alcanl.app.modules.IBuilder;
import java.io.IOException;
import java.net.Socket;

public interface IConsoleReceiverBuilder extends IBuilder {
    IConsoleReceiverBuilder setBufferedReader(Socket socket) throws IOException;

}
