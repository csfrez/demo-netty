package com.csfrez.netty.protocol.util;

public class IdUtil {
	
	public static String randomId() {
		return System.currentTimeMillis() + "";
	}

}
