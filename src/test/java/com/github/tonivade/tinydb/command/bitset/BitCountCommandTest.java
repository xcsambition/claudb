/*
 * Copyright (c) 2016-2017, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package com.github.tonivade.tinydb.command.bitset;

import static com.github.tonivade.resp.protocol.RedisToken.integer;

import org.junit.Rule;
import org.junit.Test;

import com.github.tonivade.tinydb.command.CommandRule;
import com.github.tonivade.tinydb.command.CommandUnderTest;
import com.github.tonivade.tinydb.data.DatabaseValue;

@CommandUnderTest(BitCountCommand.class)
public class BitCountCommandTest {
  @Rule
  public final CommandRule rule = new CommandRule(this);

  @Test
  public void testExecute()  {
    rule.withData("test", DatabaseValue.bitset(1, 5, 10, 15))
    .withParams("test")
    .execute()
    .assertThat(integer(4));
  }
}