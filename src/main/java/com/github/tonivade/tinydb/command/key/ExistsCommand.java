/*
 * Copyright (c) 2015-2017, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package com.github.tonivade.tinydb.command.key;

import static com.github.tonivade.tinydb.data.DatabaseKey.safeKey;

import java.time.Instant;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.Request;
import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.tinydb.command.TinyDBCommand;
import com.github.tonivade.tinydb.command.annotation.ReadOnly;
import com.github.tonivade.tinydb.data.DatabaseKey;
import com.github.tonivade.tinydb.data.Database;

@ReadOnly
@Command("exists")
@ParamLength(1)
public class ExistsCommand implements TinyDBCommand {

  @Override
  public RedisToken<?> execute(Database db, Request request) {
    DatabaseKey key = db.getKey(safeKey(request.getParam(0)));
    return RedisToken.integer(key != null ? !key.isExpired(Instant.now()) : false);
  }

}