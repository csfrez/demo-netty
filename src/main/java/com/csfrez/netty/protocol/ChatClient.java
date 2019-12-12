package com.csfrez.netty.protocol;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.csfrez.netty.protocol.code.PacketCodecHandler;
import com.csfrez.netty.protocol.code.Spliter;
import com.csfrez.netty.protocol.command.ConsoleCommandManager;
import com.csfrez.netty.protocol.command.LoginConsoleCommand;
import com.csfrez.netty.protocol.handler.CreateGroupResponseHandler;
import com.csfrez.netty.protocol.handler.GroupMessageResponseHandler;
import com.csfrez.netty.protocol.handler.HeartBeatResponseHandler;
import com.csfrez.netty.protocol.handler.HeartBeatTimerHandler;
import com.csfrez.netty.protocol.handler.JoinGroupResponseHandler;
import com.csfrez.netty.protocol.handler.ListGroupMembersResponseHandler;
import com.csfrez.netty.protocol.handler.LoginResponseHandler;
import com.csfrez.netty.protocol.handler.LogoutResponseHandler;
import com.csfrez.netty.protocol.handler.MessageResponseHandler;
import com.csfrez.netty.protocol.handler.NettyIdleStateHandler;
import com.csfrez.netty.protocol.handler.QuitGroupResponseHandler;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {

	public static final int MAX_RETRY = 5;

	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();

		bootstrap
				// 1.指定线程模型
				.group(group)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) {
						//ch.pipeline().addLast(new ChatClientHandler());
						//ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
						ch.pipeline().addLast(new NettyIdleStateHandler());
						ch.pipeline().addLast(new Spliter());
						ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
						ch.pipeline().addLast(new HeartBeatTimerHandler());
						ch.pipeline().addLast(new HeartBeatResponseHandler());
						ch.pipeline().addLast(new LoginResponseHandler());
		                ch.pipeline().addLast(new LogoutResponseHandler());
		                ch.pipeline().addLast(new MessageResponseHandler());
		                ch.pipeline().addLast(new CreateGroupResponseHandler());
		                ch.pipeline().addLast(new JoinGroupResponseHandler());
		                ch.pipeline().addLast(new ListGroupMembersResponseHandler());
		                ch.pipeline().addLast(new QuitGroupResponseHandler());
		                ch.pipeline().addLast(new GroupMessageResponseHandler());
					}
				});
		// 4.建立连接
		connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
	}

	private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
		bootstrap.connect(host, port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println("连接成功!");
				Channel channel = ((ChannelFuture) future).channel();
				// 连接成功之后，启动控制台线程
				startConsoleThread(channel);
			} else if (retry == 0) {
				System.err.println("重试次数已用完，放弃连接！");
			} else {
				// 第几次重连
				int order = (MAX_RETRY - retry) + 1;
				// 本次重连的间隔
				int delay = 1 << order;
				System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
				bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay,
						TimeUnit.SECONDS);
			}
		});
	}
	
	private static void startConsoleThread(Channel channel) {
	    new Thread(() -> {
	    	ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
	    	LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
	    	Scanner scanner = new Scanner(System.in);
	        while (!Thread.interrupted()) {
	            if (!SessionUtil.hasLogin(channel)) {
	                loginConsoleCommand.exec(scanner, channel);
	            } else {
	                consoleCommandManager.exec(scanner, channel);
	            }
	        }
	    }).start();
	}
	
//	private static void startConsoleThread(Channel channel) {
//	    new Thread(() -> {
//	    	LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//	    	Scanner sc = new Scanner(System.in);
//	        while (!Thread.interrupted()) {
//	            if (!SessionUtil.hasLogin(channel)) {
//	                System.out.print("输入用户名登录: ");
//	                String userName = sc.nextLine();
//	                loginRequestPacket.setUserId(userName);
//	                loginRequestPacket.setUserName(userName);
//	                
//	                // 密码使用默认的
//	                loginRequestPacket.setPassword("123456");
//
//	                // 发送登录数据包
//	                channel.writeAndFlush(loginRequestPacket);
//	                waitForLoginResponse();
//	            } else {
//	                String toUserId = sc.next();
//	                String message = sc.next();
//	                channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
//	            }
//	        }
//	        if(sc != null) {
//	        	sc.close();
//	        }
//	    }).start();
//	    
//	}
//	
//	private static void waitForLoginResponse() {
//	    try {
//	        Thread.sleep(1000);
//	    } catch (InterruptedException ignored) {
//	    }
//	}

//	private static void startConsoleThread(Channel channel) {
//		new Thread(() -> {
//			Scanner sc = null;
//			while (!Thread.interrupted()) {
//				//if (LoginUtil.hasLogin(channel)) {
//				System.out.println("输入消息发送至服务端: ");
//				sc = new Scanner(System.in);
//				String line = sc.nextLine();
//
//				MessageRequestPacket packet = new MessageRequestPacket();
//				packet.setMessage(line);
//				ByteBuf byteBuf = PacketCodec.INSTANCE.encode(packet);
//				channel.writeAndFlush(byteBuf);
//				//}
//			}
//			if(sc != null) {
//				sc.close();
//			}
//		}).start();
//	}

}
