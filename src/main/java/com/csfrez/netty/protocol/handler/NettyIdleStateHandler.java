package com.csfrez.netty.protocol.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyIdleStateHandler extends IdleStateHandler {
	
    private static final int READER_IDLE_TIME = 15;

    public NettyIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
    	System.out.println(System.currentTimeMillis() + ":" +Thread.currentThread().getName() + "=>" + evt.state());
        //System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        //ctx.channel().close();
    }

}
