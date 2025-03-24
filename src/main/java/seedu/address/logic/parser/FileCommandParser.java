package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_APPEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_SAVE;

import seedu.address.logic.commands.FileCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FileCommand object
 */
public class FileCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FileCommand
     * and returns a FileCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FileCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_LOAD, PREFIX_FILE_SAVE,
                PREFIX_FILE_APPEND);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE_LOAD, PREFIX_FILE_SAVE, PREFIX_FILE_APPEND);
        argMultimap.verifyOnlyOnePrefix(PREFIX_FILE_LOAD, PREFIX_FILE_SAVE, PREFIX_FILE_APPEND);

        if (argMultimap.getValue(PREFIX_FILE_LOAD).isPresent()) {
            return new FileCommand(FileCommand.FileOperation.LOAD, argMultimap.getValue(PREFIX_FILE_LOAD).get());
        } else if (argMultimap.getValue(PREFIX_FILE_SAVE).isPresent()) {
            return new FileCommand(FileCommand.FileOperation.SAVE, argMultimap.getValue(PREFIX_FILE_SAVE).get());
        } else if (argMultimap.getValue(PREFIX_FILE_APPEND).isPresent()) {
            return new FileCommand(FileCommand.FileOperation.APPEND, argMultimap.getValue(PREFIX_FILE_APPEND).get());
        }
        throw new ParseException(FileCommand.MESSAGE_USAGE);
    }
}
