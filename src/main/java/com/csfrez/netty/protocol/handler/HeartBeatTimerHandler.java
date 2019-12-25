package com.csfrez.netty.protocol.handler;

import java.util.concurrent.TimeUnit;

import com.csfrez.netty.protocol.packet.HeartBeatRequestPacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

	private static final int HEARTBEAT_INTERVAL = 5;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		scheduleSendHeartBeat(ctx);
		super.channelActive(ctx);
	}

	private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
		ctx.channel().eventLoop().execute(new Runnable() {
		    @Override
		    public void run() {
		        //...
		    }
		});
		System.out.println(System.currentTimeMillis() + ":" +Thread.currentThread().getName() + "=>发送心跳报文");
		ctx.executor().schedule(() -> {

			if (ctx.channel().isActive()) {
				ctx.writeAndFlush(new HeartBeatRequestPacket());
				scheduleSendHeartBeat(ctx);
			}

		}, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
	}

}
