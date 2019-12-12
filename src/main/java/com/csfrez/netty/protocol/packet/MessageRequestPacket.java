package com.csfrez.netty.protocol.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequestPacket extends Packet {
	
	private String toUserId;
	
	private String message;
	
	@Override
	public Byte getCommand() {
		return Command.MESSAGE_REQUEST;
	}

}
