package com.csfrez.netty.first;

import java.nio.charset.Charset;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf byteBuf = (ByteBuf) msg;
		System.out.println(Thread.currentThread().getName() + ": " + new Date() + ": 服务端读到数据 -> "
				+ byteBuf.toString(Charset.forName("utf-8")));

		// 回复数据到客户端
		System.out.println(new Date() + ": 服务端写出数据");
		ByteBuf out = getByteBuf(ctx);
		ctx.channel().writeAndFlush(out);
	}

	private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
		byte[] bytes = ("你好，欢迎访问的我网站：www.csfrez.com!"+System.getProperty("line.separator")).getBytes(Charset.forName("utf-8"));

		ByteBuf buffer = ctx.alloc().buffer();

		buffer.writeBytes(bytes);

		return buffer;
	}

}