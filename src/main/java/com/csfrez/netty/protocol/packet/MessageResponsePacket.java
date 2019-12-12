package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponsePacket extends Packet {
	
	private String fromUserId;
	
	private String fromUserName;
	
	private String message;
	
	private boolean success;
	
	@Override
	public Byte getCommand() {
		return Command.MESSAGE_RESPONSE;
	}

}
