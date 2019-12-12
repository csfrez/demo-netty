package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.MessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket)
			throws Exception {
		if(messageResponsePacket.isSuccess()) {
			String fromUserId = messageResponsePacket.getFromUserId();
			System.out.println(fromUserId + "：" + messageResponsePacket.getMessage());
		} else {
			System.out.println("消息发送失败原因：" + messageResponsePacket.getMessage());
		}
		//System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
	}

}
