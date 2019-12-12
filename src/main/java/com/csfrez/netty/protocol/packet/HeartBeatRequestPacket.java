package com.csfrez.netty.protocol.packet;

public class HeartBeatRequestPacket extends Packet {

	@Override
	public Byte getCommand() {
		return Command.HEART_BEAT_REQUEST;
	}

}
