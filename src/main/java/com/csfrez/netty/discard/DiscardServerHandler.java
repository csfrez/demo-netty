package com.csfrez.netty.discard;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class DiscardServerHandler extends SimpleChannelHandler {
	
	@Override
	 public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		e.getChannel().write(buf);
		while(buf.readable()) {
			System.out.println((char) buf.readByte());
			System.out.flush();
		}
	 }
	
	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		 e.getCause().printStackTrace();
		 Channel ch = e.getChannel();
		 ch.close();
	 }
}
