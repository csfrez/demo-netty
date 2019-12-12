package com.csfrez.netty.protocol.code;

import java.util.List;

import com.csfrez.netty.protocol.packet.PacketCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		out.add(PacketCodec.INSTANCE.decode(in));
	}

}
