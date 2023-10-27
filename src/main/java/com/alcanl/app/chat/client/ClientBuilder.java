package com.alcanl.app.chat.client;

import com.alcanl.app.chat.client.Client;
public abstract class ClientBuilder {
    protected Client client;
    protected Client create()
    {
        return client;
    }
}
