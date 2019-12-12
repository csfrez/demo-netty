package com.csfrez.netty.protocol.command;

import java.util.Arrays;
import java.util.Scanner;

import com.csfrez.netty.protocol.packet.CreateGroupRequestPacket;

import io.netty.channel.Channel;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }

}