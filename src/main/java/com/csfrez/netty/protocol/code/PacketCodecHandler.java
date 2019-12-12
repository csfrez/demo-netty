package com.csfrez.netty.protocol.code;

import java.util.List;

import com.csfrez.netty.protocol.packet.Packet;
import com.csfrez.netty.protocol.packet.PacketCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

@Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
	
	public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
		ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, packet);
        out.add(byteBuf);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
		out.add(PacketCodec.INSTANCE.decode(byteBuf));
	}

}
