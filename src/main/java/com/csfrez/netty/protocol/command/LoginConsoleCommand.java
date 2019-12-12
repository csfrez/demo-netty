package com.csfrez.netty.protocol.command;

import java.util.Scanner;

import com.csfrez.netty.protocol.packet.LoginRequestPacket;

import io.netty.channel.Channel;

public class LoginConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入用户名登录: ");
        String userName = scanner.next();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(userName);
        loginRequestPacket.setUserName(userName);
        
        // 密码使用默认的
        loginRequestPacket.setPassword("123456");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();

	}

	private static void waitForLoginResponse() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}
}
