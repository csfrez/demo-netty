package com.csfrez.netty.protocol;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.csfrez.netty.protocol.packet.LoginRequestPacket;
import com.csfrez.netty.protocol.packet.LoginResponsePacket;
import com.csfrez.netty.protocol.packet.MessageRequestPacket;
import com.csfrez.netty.protocol.packet.MessageResponsePacket;
import com.csfrez.netty.protocol.packet.Packet;
import com.csfrez.netty.protocol.packet.PacketCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf requestByteBuf = (ByteBuf) msg;

		// 解码
		Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

		// 判断是否是登录请求数据包
		if (packet instanceof LoginRequestPacket) {
			LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
			System.out.println(JSON.toJSONString(loginRequestPacket));
			
			LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
			loginResponsePacket.setVersion(packet.getVersion());
			if (valid(loginRequestPacket)) {
			    loginResponsePacket.setSuccess(true);
			    loginResponsePacket.setReason("登录成功");
			} else {
			    loginResponsePacket.setReason("账号密码校验失败");
			    loginResponsePacket.setSuccess(false);
			}
			// 编码
			ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(loginResponsePacket);
			ctx.channel().writeAndFlush(responseByteBuf);
		} else if (packet instanceof MessageRequestPacket) {
	        // 处理消息
	        MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);
	        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
	        
	        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
	        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
	        ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(messageResponsePacket);
	        ctx.channel().writeAndFlush(responseByteBuf);
	    }
	}

	private boolean valid(LoginRequestPacket loginRequestPacket) {
		return true;
	}

}
