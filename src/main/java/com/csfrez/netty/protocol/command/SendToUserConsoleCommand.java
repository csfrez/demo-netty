package com.csfrez.netty.protocol.command;

import java.util.Scanner;

import com.csfrez.netty.protocol.packet.MessageRequestPacket;

import io.netty.channel.Channel;

public class SendToUserConsoleCommand implements ConsoleCommand {

	@Override
	public void exec(Scanner scanner, Channel channel) {
		System.out.println("请发送消息: ");
		String toUserId = scanner.next();
		String message = scanner.next();
		channel.writeAndFlush(new MessageRequestPacket(toUserId, message));

	}

}
