package com.csfrez.netty.protocol.handler;

import com.csfrez.netty.protocol.packet.MessageRequestPacket;
import com.csfrez.netty.protocol.packet.MessageResponsePacket;
import com.csfrez.netty.protocol.util.Session;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
	
	public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
	
	private MessageRequestHandler() {
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
		// 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
		
        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        
        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        
        // 4.将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
        	messageResponsePacket.setSuccess(true);
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
            messageResponsePacket.setSuccess(false);
            messageResponsePacket.setMessage(messageRequestPacket.getToUserId()+"不在线");
            ctx.channel().writeAndFlush(messageResponsePacket);
        }
		
//		MessageResponsePacket messageResponsePacket = receiveMessage(messageRequestPacket);
//        messageResponsePacket = new MessageResponsePacket();
//        messageResponsePacket.setMessage("消息发送成功");
//        ctx.channel().writeAndFlush(messageResponsePacket);
	}
//
//	private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
//		 // 处理消息
//        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
//		return messageResponsePacket;
//	}

}
