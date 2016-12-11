/*
 * Copyright (c) 2016, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package tonivade.db.command.bitset;

import static tonivade.db.data.DatabaseKey.safeKey;
import static tonivade.db.data.DatabaseValue.bitset;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;
import com.github.tonivade.resp.protocol.SafeString;

import tonivade.db.command.ITinyDBCommand;
import tonivade.db.command.annotation.ParamType;
import tonivade.db.data.DataType;
import tonivade.db.data.IDatabase;

@Command("setbit")
@ParamLength(3)
@ParamType(DataType.STRING)
public class SetBitCommand implements ITinyDBCommand {

    @Override
    public void execute(IDatabase db, IRequest request, IResponse response) {
        try {
            int offset = Integer.parseInt(request.getParam(1).toString());
            int bit = Integer.parseInt(request.getParam(2).toString());
            Queue<Boolean> queue = new LinkedList<>();
            db.merge(safeKey(request.getParam(0)), bitset(), (oldValue, newValue) -> {
                BitSet bitSet = BitSet.valueOf(oldValue.<SafeString>getValue().getBuffer());
                queue.add(bitSet.get(offset));
                bitSet.set(offset, bit != 0);
                return oldValue;
            });
            response.addInt(queue.poll());
        } catch (NumberFormatException e) {
            response.addError("bit or offset is not an integer");
        }
    }

}
