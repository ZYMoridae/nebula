package com.jz.nebula.config.redis;

public interface MessagePublisher {

    void publish(final String message);
}
