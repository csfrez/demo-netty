package com.csfrez.netty.protocol.handler;

import java.util.ArrayList;
import java.util.List;

import com.csfrez.netty.protocol.packet.CreateGroupRequestPacket;
import com.csfrez.netty.protocol.packet.CreateGroupResponsePacket;
import com.csfrez.netty.protocol.util.IdUtil;
import com.csfrez.netty.protocol.util.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

@Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler< CreateGroupRequestPacket> {
	
	public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
	
	private CreateGroupRequestHandler() {
		
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
		List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();
        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        
        // 3. 创建群聊创建结果的响应
        String groupId = IdUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);
        
        SessionUtil.setChannelGroup(groupId, channelGroup);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

	}

}
