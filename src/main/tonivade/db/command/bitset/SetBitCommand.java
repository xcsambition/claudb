package tonivade.db.command.bitset;

import static tonivade.db.data.DatabaseKey.safeKey;
import static tonivade.db.data.DatabaseValue.bitset;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

import tonivade.db.command.ITinyDBCommand;
import tonivade.db.command.annotation.ParamType;
import tonivade.db.data.DataType;
import tonivade.db.data.IDatabase;
import tonivade.redis.annotation.Command;
import tonivade.redis.annotation.ParamLength;
import tonivade.redis.command.IRequest;
import tonivade.redis.command.IResponse;

@Command("setbit")
@ParamLength(3)
@ParamType(DataType.BITSET)
public class SetBitCommand implements ITinyDBCommand {

    @Override
    public void execute(IDatabase db, IRequest request, IResponse response) {
        try {
            int offset = Integer.parseInt(request.getParam(1).toString());
            int bit = Integer.parseInt(request.getParam(2).toString());
            Queue<Boolean> queue = new LinkedList<>();
            db.merge(safeKey(request.getParam(0)), bitset(), (oldValue, newValue) -> {
                BitSet bitSet = oldValue.<BitSet> getValue();
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
