package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinGroupResponsePacket extends Packet {
	
	private boolean success;
	
	private String groupId;
	
	private String reason;

	@Override
	public Byte getCommand() {
		return Command.JOIN_GROUP_RESPONSE;
	}

}
