package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.ListGroupMembersResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
		System.out.println("群[" + listGroupMembersResponsePacket.getGroupId() + "]中的人包括：" + listGroupMembersResponsePacket.getSessionList());
	}

}
