package com.csfrez.netty.protocol.packet;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequestPacket extends Packet {

	private List<String> userIdList;
	
	@Override
	public Byte getCommand() {
		return Command.CREATE_GROUP_REQUEST;
	}

}
