package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutResponsePacket extends Packet {

	private boolean success;

	private String userName;

	@Override
	public Byte getCommand() {
		return Command.LOGOUT_RESPONSE;
	}

}
