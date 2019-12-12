package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuitGroupRequestPacket extends Packet {
	
	private String groupId;

	@Override
	public Byte getCommand() {
		return Command.QUIT_GROUP_REQUEST;
	}

}
