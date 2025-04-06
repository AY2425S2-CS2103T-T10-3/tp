package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FileCommand;
import seedu.address.logic.commands.FileCommand.FileOperation;

public class FileCommandParserTest {

    private FileCommandParser parser = new FileCommandParser();

    @Test
    public void parse_validArgs_returnsFileCommand() {
        String args = " /load data test";
        FileCommand expectedFileCommand = new FileCommand(FileOperation.LOAD, "data_test");
        assertParseSuccess(parser, args, expectedFileCommand);
    }

    @Test
    public void parse_listFiles_returnsFileCommand() {
        String args = " /list all";
        FileCommand expectedFileCommand = new FileCommand(FileOperation.LIST, "all");
        assertParseSuccess(parser, args, expectedFileCommand);
    }

    @Test
    public void parse_listFiles_throwsParseException() {
        String args = " /list";
        assertParseFailure(parser, args, FileCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_missingArgValue_throwsParseException() {
        String args = " /load";
        assertParseFailure(parser, args, FileCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_moreThanOneFields_throwsParseException() {
        String args = " /load data.json /save data.json";
        String expected = MESSAGE_DUPLICATE_FIELDS + "/save /load";
        assertParseFailure(parser, args, expected);
    }

    @Test
    public void parse_duplicateArgs_throwsParseException() {
        String args = " /load data.json /load data.json";
        String expected = MESSAGE_DUPLICATE_FIELDS + "/load";
        assertParseFailure(parser, args, expected);
    }

    @Test void parse_invalidFileName_throwsParseException() {
        String args = " /save ../invalid.json";
        assertParseFailure(
                parser,
                args,
                "File operation failed: File name ../invalid.json cannot contain directory traversal characters");
    }
}
