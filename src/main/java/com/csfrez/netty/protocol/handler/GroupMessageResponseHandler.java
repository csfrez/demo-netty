package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.GroupMessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket)
			throws Exception {
		System.out.println("收到群[" + groupMessageResponsePacket.getFromGroupId() + "]中["
				+ groupMessageResponsePacket.getFromUser().getUserName() + "]发来的消息："
				+ groupMessageResponsePacket.getMessage());

	}

}
