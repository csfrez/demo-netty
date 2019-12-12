package com.csfrez.netty.protocol;

import com.csfrez.netty.protocol.code.PacketCodecHandler;
import com.csfrez.netty.protocol.code.Spliter;
import com.csfrez.netty.protocol.handler.AuthHandler;
import com.csfrez.netty.protocol.handler.HeartBeatRequestHandler;
import com.csfrez.netty.protocol.handler.IntegratorHandler;
import com.csfrez.netty.protocol.handler.LoginRequestHandler;
import com.csfrez.netty.protocol.handler.NettyIdleStateHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ChatServer {

	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		NioEventLoopGroup boos = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		serverBootstrap.group(boos, worker).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.handler(new ChannelInitializer<NioServerSocketChannel>() {
				    protected void initChannel(NioServerSocketChannel ch) {
				        System.out.println("服务端启动中");
				    }
				})
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) {
						System.out.println("新连接处理");
						//ch.pipeline().addLast(new ChatServerHandler());
						//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
						//ch.pipeline().addLast(new LifeCyCleTestHandler());
						ch.pipeline().addLast(new NettyIdleStateHandler());
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
						ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
						ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IntegratorHandler.INSTANCE);
					}
				});
		bind(serverBootstrap, 8000);
	}

	private static void bind(final ServerBootstrap serverBootstrap, final int port) {
		serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
			public void operationComplete(Future<? super Void> future) {
				if (future.isSuccess()) {
					System.out.println("端口[" + port + "]绑定成功!");
				} else {
					System.err.println("端口[" + port + "]绑定失败!");
					bind(serverBootstrap, port + 1);
				}
			}
		});
	}
}
