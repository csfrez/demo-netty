package com.csfrez.netty.simple;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {

	public static final int MAX_RETRY = 5;

	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();

		bootstrap
				// 1.指定线程模型
				.group(group)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
		        .option(ChannelOption.SO_KEEPALIVE, true)
		        .option(ChannelOption.TCP_NODELAY, true)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) {
						ch.pipeline().addLast(new StringEncoder());
					}
				});
		// 4.建立连接
		connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
		
//		Channel channel = bootstrap.connect("127.0.0.1", 8000).addListener(future -> {
//			if (future.isSuccess()) {
//				System.out.println("连接成功!");
//			} else {
//				System.err.println("连接失败!");
//			}
//		}).channel();
//		while (true) {
//			if (channel.isActive()) {
//				String msg = new Date() + ": hello world!";
//				System.out.println(Thread.currentThread().getName() + "==>" + msg);
//				channel.writeAndFlush(msg);
//			}
//			Thread.sleep(2000);
//		}
	}

	private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
		Channel channel = bootstrap.connect(host, port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功!");
			} else if (retry == 0) {
				System.err.println("重试次数已用完，放弃连接！");
			} else {
				// 第几次重连
				int order = (MAX_RETRY - retry) + 1;
				// 本次重连的间隔
				int delay = 1 << order;
				System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
				bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
			}
		}).channel();
		while (true) {
			System.out.println(Thread.currentThread().getName() + "; channel.isActive()="+channel.isActive());
			if (channel.isActive()) {
				String msg = new Date() + ": hello world!";
				System.out.println(Thread.currentThread().getName() + "==>" + msg);
				channel.writeAndFlush(msg);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
