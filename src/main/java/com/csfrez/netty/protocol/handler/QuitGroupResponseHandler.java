package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.QuitGroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
		System.out.println("退出群["+quitGroupResponsePacket.getGroupId()+"]，结果：" + quitGroupResponsePacket.isSuccess());
	}

}
