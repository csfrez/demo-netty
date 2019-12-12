package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestPacket extends Packet {
	
	private String userName;

	@Override
	public Byte getCommand() {
		return Command.LOGOUT_REQUEST;
	}
}