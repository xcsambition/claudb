/*
 * Copyright (c) 2015-2022, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.claudb.junit5;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.github.tonivade.resp.RespServer;

public class ClauDBExtension implements BeforeAllCallback, AfterAllCallback {
  
  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Class<?> testClass = context.getTestClass().get();

    Field[] declaredFields = testClass.getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      Object value = field.get(null);
      if (value instanceof RespServer) {
        ((RespServer) value).start();
      }
    }
  }
  
  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    Class<?> testClass = context.getTestClass().get();

    Field[] declaredFields = testClass.getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      Object value = field.get(null);
      if (value instanceof RespServer) {
        ((RespServer) value).stop();
      }
    }
  }
}
