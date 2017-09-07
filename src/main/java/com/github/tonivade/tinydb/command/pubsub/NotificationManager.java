/*
 * Copyright (c) 2015-2017, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.tinydb.command.pubsub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.tonivade.tinydb.TinyDBServerContext;
import com.github.tonivade.tinydb.event.Event;

public class NotificationManager extends SubscriptionManager {
  
  private final TinyDBServerContext server;
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  
  public NotificationManager(TinyDBServerContext server) {
    this.server = server;
  }
  
  public void enqueue(Event event) {
    executor.execute(() -> publish(server, event));
  }
}
