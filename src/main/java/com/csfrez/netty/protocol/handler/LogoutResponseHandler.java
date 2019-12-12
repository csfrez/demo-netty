package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.LogoutResponsePacket;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) throws Exception {
		System.out.println("用户[" + logoutResponsePacket.getUserName()+"]退出登录，结果：" + logoutResponsePacket.isSuccess());
		SessionUtil.unBindSession(ctx.channel());
	}

	@Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
