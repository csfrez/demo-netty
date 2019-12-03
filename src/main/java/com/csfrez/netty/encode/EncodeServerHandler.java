package com.csfrez.netty.encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EncodeServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		User user = new User();
		user.setName("csfrez");
		user.setAge(18);
		ctx.writeAndFlush(user);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		User user = (User)msg;
		System.out.println(user.getName()+ ":" + user.getAge());
		ctx.writeAndFlush(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
