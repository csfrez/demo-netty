package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.HeartBeatResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
		System.out.println(System.currentTimeMillis() + ":" +Thread.currentThread().getName()+"=>客户端心跳响应处理");
	}

}
