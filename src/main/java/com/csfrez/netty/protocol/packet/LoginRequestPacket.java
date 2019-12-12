package com.csfrez.netty.protocol.packet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestPacket extends Packet {
	
	private String userId;

	private String userName;

	private String password;

	@Override
	public Byte getCommand() {
		return Command.LOGIN_REQUEST;
	}
}