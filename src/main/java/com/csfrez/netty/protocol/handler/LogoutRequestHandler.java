package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.LogoutRequestPacket;
import com.csfrez.netty.protocol.packet.LogoutResponsePacket;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

	public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();
	
	private LogoutRequestHandler() {
		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) throws Exception {
		System.out.println("用户["+logoutRequestPacket.getUserName()+"]退出登录");
		SessionUtil.unBindSession(ctx.channel());
		
		LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
		logoutResponsePacket.setUserName(logoutRequestPacket.getUserName());
		logoutResponsePacket.setSuccess(true);
		ctx.channel().writeAndFlush(logoutResponsePacket);
	}
}
