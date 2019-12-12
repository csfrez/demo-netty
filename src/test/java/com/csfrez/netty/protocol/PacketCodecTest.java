package com.csfrez.netty.protocol;

import org.junit.Assert;
import org.junit.Test;

import com.csfrez.netty.protocol.packet.LoginRequestPacket;
import com.csfrez.netty.protocol.packet.Packet;
import com.csfrez.netty.protocol.packet.PacketCodec;
import com.csfrez.netty.protocol.serialize.JSONSerializer;
import com.csfrez.netty.protocol.serialize.Serializer;

import io.netty.buffer.ByteBuf;

public class PacketCodecTest {

	
	@Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("1");
        loginRequestPacket.setUserName("csfrez");
        loginRequestPacket.setPassword("1");

        PacketCodec packetCodeC = PacketCodec.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
