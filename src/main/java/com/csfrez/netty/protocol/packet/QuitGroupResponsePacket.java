package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuitGroupResponsePacket extends Packet {
	
	private String groupId;
	
	private boolean success;

	@Override
	public Byte getCommand() {
		return Command.QUIT_GROUP_RESPONSE;
	}

}
