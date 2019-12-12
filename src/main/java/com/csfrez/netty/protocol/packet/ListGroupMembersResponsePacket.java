package com.csfrez.netty.protocol.packet;

import java.util.List;

import com.csfrez.netty.protocol.util.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListGroupMembersResponsePacket extends Packet {
	
	private String groupId;
	
	private List<Session> sessionList;

	@Override
	public Byte getCommand() {
		return Command.LIST_GROUP_RESPONSE;
	}

}
