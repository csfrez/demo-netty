package com.csfrez.netty.encode;

import java.util.List;

import com.csfrez.netty.util.SerializableUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class UserConvertHandler extends MessageToMessageCodec<ByteBuf, User> {

	@Override
	protected void encode(ChannelHandlerContext ctx, User msg, List<Object> out) throws Exception {
		//Unpooled.wrappedBuffer(SerializableUtil.objectToByteArray(msg));
		out.add(Unpooled.wrappedBuffer(SerializableUtil.objectToByteArray(msg)));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		System.out.println("msg:" + msg);
		byte[] array = new byte[msg.readableBytes()];
        msg.getBytes(0, array);
        Object user = SerializableUtil.byteArrayToObject(array);
        out.add(user);
	}
}
