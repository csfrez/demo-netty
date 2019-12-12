package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponsePacket extends Packet {

	private boolean success;

	private String reason;
	
	private String userId;
	
	private String userName;

	@Override
	public Byte getCommand() {
		return Command.LOGIN_RESPONSE;
	}

}
