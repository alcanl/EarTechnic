package com.alcanl.app.stream.handler;

import org.jboss.netty.channel.Channel;

public interface StreamServerListener {
	void onClientConnectedIn(Channel channel);
	void onClientDisconnected(Channel channel);
	void onException(Channel channel, Throwable t);
}
