/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package tonivade.db.command.server;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;

import tonivade.db.command.ITinyDBCommand;
import tonivade.db.data.IDatabase;

@Command("flushdb")
public class FlushDBCommand implements ITinyDBCommand {

    @Override
    public void execute(IDatabase db, IRequest request, IResponse response) {
        db.clear();
        response.addSimpleStr(IResponse.RESULT_OK);
    }

}
