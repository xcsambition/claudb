/*
 * Copyright (c) 2015, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */

package tonivade.db.command.hash;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static tonivade.db.data.DatabaseValue.entry;
import static tonivade.db.data.DatabaseValue.hash;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import tonivade.db.command.hash.HashValuesCommand;
import tonivade.db.command.impl.CommandRule;
import tonivade.db.command.impl.CommandUnderTest;

@CommandUnderTest(HashValuesCommand.class)
public class HashValuesCommandTest {

    @Rule
    public final CommandRule rule = new CommandRule(this);

    @Captor
    private ArgumentCaptor<Collection<String>> captor;

    @Test
    public void testExecute() {
        rule.withData("test",
                hash(entry("key1", "value1"),
                     entry("key2", "value2"),
                     entry("key3", "value3")))
            .withParams("test")
            .execute()
            .verify().addArray(captor.capture());

        Collection<String> values = captor.getValue();

        assertThat(values.size(), is(3));
        assertThat(values.contains("value1"), is(true));
        assertThat(values.contains("value2"), is(true));
        assertThat(values.contains("value3"), is(true));
    }

    @Test
    public void testExecuteNotExists() {
        rule.withParams("test")
            .execute()
            .verify().addArray(captor.capture());

        Collection<String> values = captor.getValue();

        assertThat(values.isEmpty(), is(true));
    }

}