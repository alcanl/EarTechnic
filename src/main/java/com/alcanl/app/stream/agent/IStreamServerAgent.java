package com.alcanl.app.stream.agent;

import java.net.SocketAddress;

public interface IStreamServerAgent {
	void start(SocketAddress streamAddress);
	void stop();
}
