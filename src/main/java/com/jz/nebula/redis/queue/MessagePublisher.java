package com.jz.nebula.redis.queue;

public interface MessagePublisher {

  void publish(final String message);
}