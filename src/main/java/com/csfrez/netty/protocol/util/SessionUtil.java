package com.csfrez.netty.protocol.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

public class SessionUtil {
	// userId -> channel 的映射
	private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
	
	private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

	public static void bindSession(Session session, Channel channel) {
		userIdChannelMap.put(session.getUserId(), channel);
		channel.attr(Attributes.SESSION).set(session);
	}

	public static void unBindSession(Channel channel) {
		if (hasLogin(channel)) {
			userIdChannelMap.remove(getSession(channel).getUserId());
			channel.attr(Attributes.SESSION).set(null);
		}
	}

	public static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION) && (channel.attr(Attributes.SESSION).get() != null);
	}

	public static Session getSession(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}

	public static Channel getChannel(String userId) {
		return userIdChannelMap.get(userId);
	}
	
	public static void setChannelGroup(String groupId, ChannelGroup channelGroup) {
		channelGroupMap.put(groupId, channelGroup);
	}
	
	public static ChannelGroup getChannelGroup(String groupId) {
		return channelGroupMap.get(groupId);
	}
}