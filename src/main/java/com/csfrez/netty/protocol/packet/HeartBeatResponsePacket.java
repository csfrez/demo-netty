package com.csfrez.netty.protocol.packet;

public class HeartBeatResponsePacket extends Packet {

	@Override
	public Byte getCommand() {
		return Command.HEART_BEAT_RESPONSE;
	}

}
