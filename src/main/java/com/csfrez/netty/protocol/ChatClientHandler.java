package com.csfrez.netty.protocol;

import java.util.Date;

import com.csfrez.netty.protocol.packet.LoginRequestPacket;
import com.csfrez.netty.protocol.packet.LoginResponsePacket;
import com.csfrez.netty.protocol.packet.MessageResponsePacket;
import com.csfrez.netty.protocol.packet.Packet;
import com.csfrez.netty.protocol.packet.PacketCodec;
import com.csfrez.netty.protocol.util.LoginUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println(new Date() + ": 客户端开始登录");

		// 创建登录对象
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		loginRequestPacket.setUserId("1");
		loginRequestPacket.setUserName("flash");
		loginRequestPacket.setPassword("pwd");

		// 编码
		ByteBuf buffer = PacketCodec.INSTANCE.encode(loginRequestPacket);

		// 写数据
		ctx.channel().writeAndFlush(buffer);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    ByteBuf byteBuf = (ByteBuf) msg;

	    Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

	    if (packet instanceof LoginResponsePacket) {
	        LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

	        if (loginResponsePacket.isSuccess()) {
	        	 LoginUtil.markAsLogin(ctx.channel());
	            System.out.println(new Date() + ": 客户端登录成功，说明：" + loginResponsePacket.getReason());
	        } else {
	            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
	        }
	    } else if (packet instanceof MessageResponsePacket) {
	        MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
	        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
	    }
	}

}
