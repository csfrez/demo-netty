package com.csfrez.netty.encode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EncodeClient {
	
	public static void main(String[] args) {
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		final Bootstrap bootStrap = new Bootstrap();
		bootStrap
			.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new UserConvertHandler());
					ch.pipeline().addLast(new EncodeClientHandler());
				}
				
			});
		
		try {
			// 发起异步连接操作
		    ChannelFuture channelFuture= bootStrap.connect("127.0.0.1", 8080).sync();
		    // 当代客户端链路关闭
		    channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 // 优雅退出，释放线程池资源
	    workerGroup.shutdownGracefully();
	}
}
