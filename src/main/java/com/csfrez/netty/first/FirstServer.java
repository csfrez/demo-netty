package com.csfrez.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class FirstServer {

	public static void main(String[] args) {
		ServerBootstrap serverBootstrap = new ServerBootstrap();

		NioEventLoopGroup boos = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		serverBootstrap.group(boos, worker).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.handler(new ChannelInitializer<NioServerSocketChannel>() {
				    protected void initChannel(NioServerSocketChannel ch) {
				        System.out.println(Thread.currentThread().getName()+": 服务端启动中");
				    }
				})
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) {
						System.out.println(Thread.currentThread().getName()+": 新连接处理");
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						ch.pipeline().addLast(new FirstServerHandler());
					}
				});
		bind(serverBootstrap, 8000);
	}

	private static void bind(final ServerBootstrap serverBootstrap, final int port) {
		serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
			public void operationComplete(Future<? super Void> future) {
				if (future.isSuccess()) {
					System.out.println(Thread.currentThread().getName()+": 端口[" + port + "]绑定成功!");
				} else {
					System.err.println(Thread.currentThread().getName()+": 端口[" + port + "]绑定失败!");
					bind(serverBootstrap, port + 1);
				}
			}
		});
	}
}
