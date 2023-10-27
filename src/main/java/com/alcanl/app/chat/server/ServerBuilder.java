package com.alcanl.app.chat.server;

import com.alcanl.app.chat.server.Server;

public abstract class ServerBuilder {
    protected Server server;
    protected Server create()
    {
        return server;
    }
}
