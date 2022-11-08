/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

package com.lin.multi.database.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // open or close
        System.out.println("key 过期了"+message.toString());
        byte[] channel = message.getChannel();
        String topic = new String(channel);
        System.out.println("key 过期了, topic = "+ topic);
    }
}
