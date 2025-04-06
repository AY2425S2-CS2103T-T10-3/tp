package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_SAVE;

import seedu.address.logic.commands.FileCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FileCommand object
 */
public class FileCommandParser implements Parser<FileCommand> {

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
                PREFIX_FILE_LIST);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE_LOAD, PREFIX_FILE_SAVE,
                PREFIX_FILE_LIST);
        argMultimap.verifyOnlyOnePrefix(PREFIX_FILE_LOAD, PREFIX_FILE_SAVE,
                PREFIX_FILE_LIST);

        if (argMultimap.getValue(PREFIX_FILE_LOAD).isPresent()) { // Load command
            String fileName = stripFileName(argMultimap.getValue(PREFIX_FILE_LOAD).get());
            checkIllegalFileName(fileName);
            return new FileCommand(FileCommand.FileOperation.LOAD, fileName);

        } else if (argMultimap.getValue(PREFIX_FILE_SAVE).isPresent()) { // Save command
            String fileName = stripFileName(argMultimap.getValue(PREFIX_FILE_SAVE).get());
            checkIllegalFileName(fileName);
            return new FileCommand(
                    FileCommand.FileOperation.SAVE,
                    argMultimap.getValue(PREFIX_FILE_SAVE).get());

        } else if (argMultimap.getValue(PREFIX_FILE_LIST).isPresent()) { // List command
            return new FileCommand(
                    FileCommand.FileOperation.LIST,
                    argMultimap.getValue(PREFIX_FILE_LIST).get());
        }
        throw new ParseException(FileCommand.MESSAGE_USAGE);
    }

    /**
     * Strips whitespaces from the file name to "_".
     *
     * @param fileName The input file name to be stripped.
     * @return The stripped file name.
     */
    private String stripFileName(String fileName) {
        return fileName.strip().replaceAll("\\s+", "_");
    }

    /**
     * Checks if the input file name contains illegal characters.
     *
     * @param inputName The input file name to check.
     * @return true if the file name is valid, false otherwise.
     */
    private boolean checkIllegalFileName(String inputName) throws ParseException {
        if (inputName.contains("..")) {
            throw new ParseException(String.format(FileCommand.MESSAGE_ERROR,
                    String.format("File name %s cannot contain directory traversal characters", inputName)));
        } else if (inputName.isBlank()) {
            throw new ParseException(String.format(FileCommand.MESSAGE_ERROR,
                    "File name cannot be empty"));
        } else if (inputName.length() > 255) {
            throw new ParseException(String.format(FileCommand.MESSAGE_ERROR,
                    String.format("File name %s is too long. Maximum length is 255 characters.", inputName)));
        } else if (inputName.matches(".*[\\\\/:*?\"<>|].*")) {
            throw new ParseException(String.format(FileCommand.MESSAGE_ERROR,
                    String.format("File name %s contains illegal characters.", inputName)));
        } else if (inputName.contains(" ")) {
            throw new ParseException(String.format(FileCommand.MESSAGE_ERROR,
                    String.format("File name %s cannot contain spaces.", inputName)));
        }
        return true;
    }
}
