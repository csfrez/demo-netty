package com.csfrez.netty.protocol.handler;

import java.util.Date;

import com.csfrez.netty.protocol.packet.LoginRequestPacket;
import com.csfrez.netty.protocol.packet.LoginResponsePacket;
import com.csfrez.netty.protocol.util.Session;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 创建登录对象
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		loginRequestPacket.setUserId("1");
		loginRequestPacket.setUserName("csfrez");
		loginRequestPacket.setPassword("123456");
		
		// 写数据
		//ctx.channel().writeAndFlush(loginRequestPacket);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
		if (loginResponsePacket.isSuccess()) {
			System.out.println(new Date() + ": 客户端登录成功，说明：" + loginResponsePacket.getReason());
            //LoginUtil.markAsLogin(ctx.channel());
			Session session = new Session(loginResponsePacket.getUserId(), loginResponsePacket.getUserName());
			SessionUtil.bindSession(session, ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
	}

	@Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
