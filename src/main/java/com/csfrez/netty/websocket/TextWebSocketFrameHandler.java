package com.csfrez.netty.websocket;

import java.util.Date;

import com.csfrez.netty.http.HttpRequestHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private final ChannelGroup group;

	public TextWebSocketFrameHandler(ChannelGroup group) {
		this.group = group;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
		//System.out.println(evt);
		if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
			String text = "Client " + ctx.channel() + " joined";
			System.out.println(text);
			ctx.pipeline().remove(HttpRequestHandler.class);
			group.writeAndFlush(new TextWebSocketFrame(text));
			group.add(ctx.channel());
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		System.out.println(msg.text());
		TextWebSocketFrame text = new TextWebSocketFrame(new Date().toString() + "==>" + msg.text());
		group.writeAndFlush(text);
	}
}
