package com.csfrez.netty.protocol.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupMessageRequestPacket extends Packet {

	private String toGroupId;
	
	private String message;
	
	@Override
	public Byte getCommand() {
		return Command.GROUP_MESSAGE_REQUEST;
	}

}
