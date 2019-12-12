package com.csfrez.netty.protocol.packet;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupResponsePacket extends Packet {
	
	private boolean success;
	
	private String groupId;
	
	private List<String> userNameList;

	@Override
	public Byte getCommand() {
		return Command.CREATE_GROUP_RESPONSE;
	}

}
