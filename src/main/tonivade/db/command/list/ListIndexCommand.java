/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package tonivade.db.command.list;

import static tonivade.db.data.DatabaseKey.safeKey;

import java.util.List;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;
import com.github.tonivade.resp.protocol.SafeString;

import tonivade.db.command.ITinyDBCommand;
import tonivade.db.command.annotation.ParamType;
import tonivade.db.command.annotation.ReadOnly;
import tonivade.db.data.DataType;
import tonivade.db.data.DatabaseValue;
import tonivade.db.data.IDatabase;

@ReadOnly
@Command("lindex")
@ParamLength(2)
@ParamType(DataType.LIST)
public class ListIndexCommand implements ITinyDBCommand {

    @Override
    public void execute(IDatabase db, IRequest request, IResponse response) {
        try {
            DatabaseValue value = db.getOrDefault(safeKey(request.getParam(0)), DatabaseValue.EMPTY_LIST);
            List<SafeString> list = value.getValue();

            int index = Integer.parseInt(request.getParam(1).toString());
            if (index < 0) {
                index = list.size() + index;
            }

            response.addBulkStr(list.get(index));
        } catch (NumberFormatException e) {
            response.addError("ERR value is not an integer or out of range");
        } catch (IndexOutOfBoundsException e) {
            response.addBulkStr(null);
        }
    }

}
