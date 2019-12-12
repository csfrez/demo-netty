package com.csfrez.netty.protocol.util;

import io.netty.util.AttributeKey;

public interface Attributes {
    
	AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    
	 AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
