package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListGroupMembersRequestPacket extends Packet {

	private String groupId;
	
	@Override
	public Byte getCommand() {
		return Command.LIST_GROUP_REQUEST;
	}

}
