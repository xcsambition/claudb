package tonivade.db.command.impl;

import static org.hamcrest.CoreMatchers.is;
import static tonivade.db.data.DatabaseValue.list;

import org.junit.Rule;
import org.junit.Test;

@CommandUnderTest(LeftPushCommand.class)
public class LeftPushCommandTest {

    @Rule
    public final CommandRule rule = new CommandRule(this);

    @Test
    public void testExecute() throws Exception {
        rule.withParams("key", "a", "b", "c")
            .execute()
            .assertThat("key", is(list("a", "b", "c")))
            .verify().addInt(3);
    }

}
