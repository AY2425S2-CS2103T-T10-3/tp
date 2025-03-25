package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_SAVE;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Represents a FileCommand which can load, save or append data to a file.
 */
public class FileCommand extends Command {
    /**
     * Represents the operation of the FileCommand.
     */
    public enum FileOperation {
        LOAD, SAVE, LIST
    }

    public static final String COMMAND_WORD = "file";
    public static final String MESSAGE_SUCCESS = "File operation successful: %1$s";
    public static final String MESSAGE_ERROR = "File operation failed: %1$s";
    public static final String MESSAGE_STRING_UNFORMATTED = """
            %s: List, Load and save addressbook data to a file in the 'data' directory.
            Parameters:
            %s FILE_PATH
            %s FILE_PATH
            %s

            Example:
            file /load data.json
            file /save data.json
            file /list
            """;

    public static final String MESSAGE_USAGE = String.format(MESSAGE_STRING_UNFORMATTED, COMMAND_WORD, PREFIX_FILE_LOAD,
            PREFIX_FILE_SAVE, PREFIX_FILE_LIST);

    private static final String ADDRESSBOOK_FILE_DIR = "data";

    private final FileOperation operation;

    private final String fileName;

    /**
     * Creates a FileCommand to load, save or append data to a file.
     */
    public FileCommand(FileOperation operation, String filePath) {
        this.operation = operation;
        this.fileName = filePath;
    }

    /**
     * Executes the FileCommand.
     *
     * @param model Model
     * @return CommandResult
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (operation) {
        case LOAD:
            return load(model);
        case SAVE:
            return save(model);
        case LIST:
            return list();
        default:
            throw new CommandException(String.format(MESSAGE_ERROR, "Invalid file operation"));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileCommand // instanceof handles nulls
                        && operation.equals(((FileCommand) other).operation)
                        && fileName.equals(((FileCommand) other).fileName)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operation", operation)
                .add("filePath", fileName)
                .toString();
    }

    /**
     * Returns the operation of the FileCommand.
     *
     * @return FileOperation
     */
    public FileOperation getOperation() {
        return operation;
    }

    /**
     * Returns the file path of the FileCommand.
     *
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Executes the load operation.
     *
     * @param model representing the current address book
     * @return CommandResult indicating the result of the load operation
     */
    public CommandResult load(Model model) {

        Path filePath = Path.of(ADDRESSBOOK_FILE_DIR, fileName);

        // Check if file exists
        if (!filePath.toFile().exists() && !filePath.toFile().isFile()) {
            return new CommandResult(String.format(MESSAGE_ERROR, "File does not exist: " + fileName));
        }

        model.setAddressBookFilePath(Path.of(ADDRESSBOOK_FILE_DIR, fileName));

        // Load data from file
        try {
            ((AddressBook) model.getAddressBook())
                    .resetData(new JsonAddressBookStorage(filePath).readAddressBook().get());
        } catch (DataLoadingException e) {
            return new CommandResult(String.format(MESSAGE_ERROR, "Failed to load data from " + fileName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, "Loaded data from " + fileName));
    }

    /**
     * Changes the address book file path to the specified file path.
     *
     * @param model Model to save
     * @return CommandResult indicating the result of the save operation
     */
    public CommandResult save(Model model) {

        model.setAddressBookFilePath(Path.of(ADDRESSBOOK_FILE_DIR, fileName));

        return new CommandResult(String.format(MESSAGE_SUCCESS, "Change saved file to " + fileName));
    }

    /**
     * Lists all files in the data directory, storing addressbook data.
     *
     * @return CommandResult indicating the result of the list operation
     */
    public CommandResult list() {

        File dataDir = new File(Path.of(ADDRESSBOOK_FILE_DIR).toString());
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(dataDir.list()));
        StringBuilder sb = new StringBuilder();
        for (String fileName : fileNames) {
            sb.append(fileName).append("\n");
        }

        return new CommandResult(String.format("Listing all files: \n%s", sb.toString()));
    }
}
