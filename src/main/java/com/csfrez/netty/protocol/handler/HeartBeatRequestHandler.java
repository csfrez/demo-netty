package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.HeartBeatRequestPacket;
import com.csfrez.netty.protocol.packet.HeartBeatResponsePacket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
	
	public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
		System.out.println(System.currentTimeMillis() + ":" +Thread.currentThread().getName()+"=>服务端心跳请求处理");
		ctx.writeAndFlush(new HeartBeatResponsePacket());
	}

}
