package astraea.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import astraea.command.Command;
import astraea.command.CommandType;
import astraea.command.DeadlineCommand;
import astraea.command.DeleteCommand;
import astraea.command.EventCommand;
import astraea.command.ExitCommand;
import astraea.command.ListCommand;
import astraea.command.TodoCommand;
import astraea.command.ToggleCommand;
import astraea.exception.AstraeaInputException;

public class ParserTest {
    @Test
    public void commandParse_validInput_success() throws AstraeaInputException {
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

        // valid bye command returns ExitCommand
        cmd = Parser.parseInput("bye");
        assertEquals(new ExitCommand(CommandType.EXIT, null), cmd);
    }

    @Test
    public void commandParse_invalidInput_exceptionThrown() {
        try {
            Parser.parseInput("");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("empty"), ae);
        }

        try {
            Parser.parseInput("pretend this is button mashing");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("invalid"), ae);
        }

        try {
            Parser.parseInput("mark   ");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("mark"), ae);
        }

        try {
            Parser.parseInput("mark notANumber");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("mark"), ae);
        }

        try {
            Parser.parseInput("mark 5068096 068405");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("mark"), ae);
        }

        try {
            Parser.parseInput("unmark   ");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("unmark"), ae);
        }

        try {
            Parser.parseInput("unmark notANumber");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("unmark"), ae);
        }

        try {
            Parser.parseInput("unmark 1 0 0 0 0 0 0");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("unmark"), ae);
        }

        try {
            Parser.parseInput("todo   ");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("todo_noName"), ae);
        }

        try {
            Parser.parseInput("deadline   ");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("deadline_noName"), ae);
        }

        try {
            Parser.parseInput("deadline test name without deadline time");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("deadline_noTime"), ae);
        }

        try {
            Parser.parseInput("event /from start time /to end time");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("event_noName"), ae);
        }

        try {
            Parser.parseInput("event test name without start /to end time");
            fail();
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("event_noStart"), ae);
        }

        try {
            Parser.parseInput("event test name /from start time without end");
        } catch (AstraeaInputException ae) {
            assertEquals(new AstraeaInputException("event_noEnd"), ae);
        }

    }

}
