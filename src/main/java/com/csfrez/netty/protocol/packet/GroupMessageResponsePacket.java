package com.csfrez.netty.protocol.packet;

import com.csfrez.netty.protocol.util.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMessageResponsePacket extends Packet {
	
	private String fromGroupId;
	
	private Session fromUser;
	
	private String message;

	@Override
	public Byte getCommand() {
		return Command.GROUP_MESSAGE_RESPONSE;
	}

}
