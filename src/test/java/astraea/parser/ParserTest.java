package astraea.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import astraea.command.Alias;
import astraea.command.Command;
import astraea.command.CommandType;
import astraea.command.DeadlineCommand;
import astraea.command.DeleteCommand;
import astraea.command.EventCommand;
import astraea.command.ExitCommand;
import astraea.command.FindCommand;
import astraea.command.ListCommand;
import astraea.command.TodoCommand;
import astraea.command.ToggleCommand;
import astraea.exception.AstraeaInputException;

public class ParserTest {
    @Test
    public void commandParse_validTaskInput_success() throws AstraeaInputException {
        Command cmd;

        // valid list command returns ListCommand
        cmd = Parser.parseInput("list remaining input doesn't matter");
        assertEquals(new ListCommand(CommandType.LIST, null), cmd);

        // valid mark command returns ToggleCommand with argument index separated
        cmd = Parser.parseInput("mark 5");
        assertEquals(new ToggleCommand(CommandType.MARK, new String[]{"5"}), cmd);

        // valid unmark command returns ToggleCommand with index separated
        cmd = Parser.parseInput("unmark 21");
        assertEquals(new ToggleCommand(CommandType.UNMARK, new String[]{"21"}), cmd);

        // valid todotask command returns TodoCommand with name separated
        cmd = Parser.parseInput("todo test phrase to copy");
        assertEquals(new TodoCommand(CommandType.TODO, new String[]{"test phrase to copy"}), cmd);

        // valid deadline command returns DeadlineCommand with name and deadline separated
        cmd = Parser.parseInput("deadline test phrase to copy /by test time to copy");
        assertEquals(new DeadlineCommand(CommandType.DEADLINE,
                new String[]{"test phrase to copy", "test time to copy"}), cmd);

        // valid event command returns EventCommand with name, start and end separated
        cmd = Parser.parseInput("event test phrase to copy /from start time to copy /to end time to copy");
        assertEquals(new EventCommand(CommandType.EVENT,
                new String[]{"test phrase to copy", "start time to copy", "end time to copy"}), cmd);

        // valid delete command returns DeleteCommand with index separated
        cmd = Parser.parseInput("delete 999");
        assertEquals(new DeleteCommand(CommandType.DELETE, new String[]{"999"}), cmd);

        // valid find command returns FindCommand with name separated
        cmd = Parser.parseInput("find test phrase to copy");
        assertEquals(new FindCommand(CommandType.FIND, new String[]{"test phrase to copy"}), cmd);

        // valid bye command returns ExitCommand
        cmd = Parser.parseInput("bye");
        assertEquals(new ExitCommand(CommandType.EXIT, null), cmd);
    }

    @Test
    public void commandParse_validAliasInput_success() throws AstraeaInputException {
        Command cmd;

        cmd = Parser.parseInput("add_alias add_alias a");
        assertEquals(new ListCommand(CommandType.ADD_ALIAS, new String[]{"add_alias", "a"}), cmd);
        Alias.addAlias("a", "remove_alias");
        cmd = Parser.parseInput("a a");
        assertEquals(new ListCommand(CommandType.REMOVE_ALIAS, new String[]{"a"}), cmd);
    }

    @Test
    public void commandParse_invalidAliasInput_exceptionThrown() {
        Alias.addAlias("a", "add_alias");
        Alias.addAlias("r", "remove_alias");
        Stream.of(
                new TestCase("", "empty"),
                new TestCase("b", "invalid"),
                new TestCase("a", "add_alias_wrongUsage"),
                new TestCase("a addAlias aa", "add_alias_invalidCommand"),
                new TestCase("a deadline a", "add_alias_existingName"),
                new TestCase("r", "remove_alias_wrongUsage")
        ).forEach(testCase -> {
            AstraeaInputException exception = assertThrows(
                    AstraeaInputException.class, () -> Parser.parseInput(testCase.input));
            assertEquals(new AstraeaInputException(testCase.expectedError), exception);
        });
    }

    // rewritten by ChatGPT to avoid repeated try-catch blocks
    @Test
    public void commandParse_invalidInput_exceptionThrown() {
        Stream.of(
                new TestCase("", "empty"),
                new TestCase("pretend this is button mashing", "invalid"),
                new TestCase("mark   ", "mark"),
                new TestCase("mark notANumber", "mark"),
                new TestCase("mark 5068096 068405", "mark"),
                new TestCase("unmark   ", "unmark"),
                new TestCase("unmark notANumber", "unmark"),
                new TestCase("unmark 1 0 0 0 0 0 0", "unmark"),
                new TestCase("todo   ", "todo_noName"),
                new TestCase("deadline   ", "deadline_noName"),
                new TestCase("deadline test name without deadline time", "deadline_noTime"),
                new TestCase("event /from start time /to end time", "event_noName"),
                new TestCase("event test name without start /to end time", "event_noStart"),
                new TestCase("event test name /from start time without end", "event_noEnd"),
                new TestCase("find      ", "find_noName")
        ).forEach(testCase -> {
            AstraeaInputException exception = assertThrows(
                    AstraeaInputException.class, () -> Parser.parseInput(testCase.input));
            assertEquals(new AstraeaInputException(testCase.expectedError), exception);
        });
    }

    private record TestCase(String input, String expectedError) {}

}
