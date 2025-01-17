/*
 * Copyright (c) 2015-2022, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package com.github.tonivade.claudb.command.set;

import static com.github.tonivade.claudb.DatabaseValueMatchers.set;

import org.junit.Rule;
import org.junit.Test;

import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.claudb.command.CommandRule;
import com.github.tonivade.claudb.command.CommandUnderTest;

@CommandUnderTest(SetCardinalityCommand.class)
public class SetCardinalityCommandTest {

  @Rule
  public final CommandRule rule = new CommandRule(this);

  @Test
  public void testExecute()  {
    rule.withData("key", set("a", "b", "c"))
    .withParams("key")
    .execute()
    .assertThat(RedisToken.integer(3));

    rule.withParams("notExists")
    .execute()
    .assertThat(RedisToken.integer(0));
  }

}
