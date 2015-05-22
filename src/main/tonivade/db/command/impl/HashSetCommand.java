package tonivade.db.command.impl;

import java.util.HashMap;
import java.util.Map;

import tonivade.db.command.ICommand;
import tonivade.db.command.IRequest;
import tonivade.db.command.IResponse;
import tonivade.db.command.annotation.ParamLength;
import tonivade.db.command.annotation.ParamType;
import tonivade.db.data.DataType;
import tonivade.db.data.DatabaseValue;
import tonivade.db.data.IDatabase;

@ParamLength(3)
@ParamType(DataType.HASH)
public class HashSetCommand implements ICommand {

    @Override
    public void execute(IDatabase db, IRequest request, IResponse response) {
        DatabaseValue value = new DatabaseValue(DataType.HASH);
        HashMap<String, String> map = new HashMap<>();
        map.put(request.getParam(1), request.getParam(2));
        value.setValue(map);

        DatabaseValue resultValue = db.merge(request.getParam(0), value, (oldValue, newValue) -> {
            if (oldValue != null) {
                Map<Object, Object> oldMap = oldValue.getValue();
                Map<Object, Object> newMap = newValue.getValue();
                oldMap.putAll(newMap);
                return oldValue;
            }
            return newValue;
        });

        Map<String, String> resultMap = resultValue.getValue();

        response.addInt(resultMap.get(request.getParam(1)) == null);
    }

}