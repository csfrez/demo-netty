package com.csfrez.netty.decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class DecodeServer {
	
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		final ServerBootstrap serverBootStrap = new ServerBootstrap();
		serverBootStrap
			.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childOption(ChannelOption.TCP_NODELAY, true)
			.childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast( new ReadTimeoutHandler(30));
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast( new DecodeServerHandler());
				}
				
			});
		try {
			// 绑定端口，同步等待成功
			ChannelFuture channelFuture = serverBootStrap.bind(8080).sync();
			// 等待服务端监听端口关闭
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 // 优雅退出，释放线程池资源
	    bossGroup.shutdownGracefully();
	    workerGroup.shutdownGracefully();
	}
}
