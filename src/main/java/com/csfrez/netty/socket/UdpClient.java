package com.csfrez.netty.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpClient {
	
	public static void main(String[] args) throws SocketException {
		DatagramSocket ds = new DatagramSocket();
		try {
			ds.setSoTimeout(1000);
			ds.connect(InetAddress.getByName("localhost"), 6666); // 连接指定服务器和端口
			// 发送:
			byte[] data = "Hello".getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ds.send(packet);
			// 接收:
			byte[] buffer = new byte[1024];
			packet = new DatagramPacket(buffer, buffer.length);
			ds.receive(packet);
			String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
			System.out.println("respone data ==> " + resp);
			ds.disconnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			ds.close();
		}
	}
}
