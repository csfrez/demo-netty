package com.csfrez.netty.protocol.command;

import java.util.Scanner;

import com.csfrez.netty.protocol.packet.GroupMessageRequestPacket;

import io.netty.channel.Channel;

public class SendToGroupConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		System.out.println("请发送消息给指定群组: ");
		String toGroupId = scanner.next();
		String message = scanner.next();
		channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));

	}

}
