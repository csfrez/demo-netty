package com.csfrez.netty.protocol.handler;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.csfrez.netty.protocol.packet.LoginRequestPacket;
import com.csfrez.netty.protocol.packet.LoginResponsePacket;
import com.csfrez.netty.protocol.util.Session;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
	
	public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
		LoginResponsePacket loginResponsePacket = login(ctx.channel(), loginRequestPacket);
		ctx.channel().writeAndFlush(loginResponsePacket);
	}

	private LoginResponsePacket login(Channel channel, LoginRequestPacket loginRequestPacket) {
		System.out.println(JSON.toJSONString(loginRequestPacket));
		LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		if(StringUtils.isEmpty(loginRequestPacket.getUserName())) {
			loginResponsePacket.setReason("用户名不能为空");
		    loginResponsePacket.setSuccess(false);
		    return loginResponsePacket;
		}
		loginResponsePacket.setVersion(loginRequestPacket.getVersion());
		if (valid(loginRequestPacket)) {
		    loginResponsePacket.setSuccess(true);
		    loginResponsePacket.setReason("登录成功");
		    loginResponsePacket.setUserId(loginRequestPacket.getUserId());
		    loginResponsePacket.setUserName(loginRequestPacket.getUserName());
		    //LoginUtil.markAsLogin(channel);
		    Session session = new Session(loginRequestPacket.getUserId(), loginRequestPacket.getUserName());
		    SessionUtil.bindSession(session, channel);
		} else {
		    loginResponsePacket.setReason("账号密码校验失败");
		    loginResponsePacket.setSuccess(false);
		}
		return loginResponsePacket;
	}
	
	private boolean valid(LoginRequestPacket loginRequestPacket) {
		return true;
	}

}
