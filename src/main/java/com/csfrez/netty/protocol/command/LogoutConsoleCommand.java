package com.csfrez.netty.protocol.command;

import java.util.Scanner;

import com.csfrez.netty.protocol.packet.LogoutRequestPacket;

import io.netty.channel.Channel;

public class LogoutConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		 System.out.print("请输入用户名退出登录：");
		 LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
		 String userName = scanner.next();
		 logoutRequestPacket.setUserName(userName);
		 channel.writeAndFlush(logoutRequestPacket);
	}

}
