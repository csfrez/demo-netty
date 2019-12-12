package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinGroupRequestPacket extends Packet {
	
	private String groupId;

	@Override
	public Byte getCommand() {
		return Command.JOIN_GROUP_REQUEST;
	}

}
