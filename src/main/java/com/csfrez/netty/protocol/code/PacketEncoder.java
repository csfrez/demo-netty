package com.csfrez.netty.protocol.code;

import com.csfrez.netty.protocol.packet.Packet;
import com.csfrez.netty.protocol.packet.PacketCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
		PacketCodec.INSTANCE.encode(out, packet);
	}

}
