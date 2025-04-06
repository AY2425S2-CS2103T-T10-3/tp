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
            %s all

            Note:
            FILE_PATH is the name of the file to load or save, without the .json extension.

            Example:
            file /load classA
            file /save homeroomB
            file /list all
            """;

    public static final String MESSAGE_USAGE = String.format(MESSAGE_STRING_UNFORMATTED, COMMAND_WORD, PREFIX_FILE_LOAD,
            PREFIX_FILE_SAVE, PREFIX_FILE_LIST);

    private static final String ADDRESSBOOK_FILE_DIR = "data";

    private final FileOperation operation;

    private final String arg;

    /**
     * Creates a FileCommand to load, save or append data to a file.
     *
     * @param operation The operation to be performed (LOAD, SAVE, LIST).
     * @param arg       The file path or name to be used for the operation.
     */
    public FileCommand(FileOperation operation, String arg) {
        this.operation = operation;
        this.arg = arg;
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
            return list(model);
        default:
            throw new CommandException(String.format(MESSAGE_ERROR, "Invalid file operation"));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileCommand // instanceof handles nulls
                        && operation.equals(((FileCommand) other).operation)
                        && arg.equals(((FileCommand) other).arg)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operation", operation)
                .add("arg", arg)
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
        return arg;
    }

    /**
     * Executes the load operation.
     *
     * @param model representing the current address book
     * @return CommandResult indicating the result of the load operation
     */
    private CommandResult load(Model model) {

        String fullFileName = String.format("%s.json", arg);
        Path filePath = Path.of(ADDRESSBOOK_FILE_DIR, fullFileName);

        // Check if file exists
        if (!filePath.toFile().exists() && !filePath.toFile().isFile()) {
            return new CommandResult(String.format(MESSAGE_ERROR, "File does not exist: " + fullFileName));
        }

        assert filePath.toFile().exists() : "File should exist";

        model.setAddressBookFilePath(Path.of(ADDRESSBOOK_FILE_DIR, fullFileName));

        assert model.getAddressBookFilePath().toString()
                .equals(Path.of(ADDRESSBOOK_FILE_DIR, fullFileName).toString()) : "File path should be set";

        // Load data from file
        try {
            ((AddressBook) model.getAddressBook())
                    .resetData(new JsonAddressBookStorage(filePath).readAddressBook().get());
        } catch (DataLoadingException e) {
            return new CommandResult(
                    String.format(MESSAGE_ERROR, "Failed to load data from save file: " + fullFileName));
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, "Loaded data from save file. Current save file: " + fullFileName));
    }

    /**
     * Changes the address book file path to the specified file path.
     *
     * @param model Model to save
     * @return CommandResult indicating the result of the save operation
     */
    private CommandResult save(Model model) {

        String fullFileName = String.format("%s.json", arg);

        model.setAddressBookFilePath(Path.of(ADDRESSBOOK_FILE_DIR, fullFileName));

        assert model.getAddressBookFilePath().toString()
                .equals(Path.of(ADDRESSBOOK_FILE_DIR, fullFileName).toString()) : "File path should be set";

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, "File saved. Current save file: " + arg));
    }

    /**
     * Lists all files in the data directory, storing addressbook data.
     *
     * @return CommandResult indicating the result of the list operation
     */
    private CommandResult list(Model model) {

        // Check if the argument is "all"
        if (arg.equals("all")) {
            return listAll(model);
        }

        return new CommandResult(MESSAGE_USAGE);
    }

    /**
     * Lists all files in the data directory, storing addressbook data.
     *
     * @return CommandResult indicating the result of the list operation
     */
    private CommandResult listAll(Model model) {
        File dataDir = new File(Path.of(ADDRESSBOOK_FILE_DIR).toString());
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(dataDir.list()));

        assert fileNames != null : "File names should not be null";

        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = fileNames.size(); i < n; i++) {
            String fullFileName = fileNames.get(i);

            assert fullFileName != null : "File name should not be null";

            sb.append(String.format("%d) ", i + 1)).append(fullFileName.strip().replace(".json", ""));
            if (model.getAddressBookFilePath().toString()
                    .equals(Path.of(ADDRESSBOOK_FILE_DIR, fullFileName).toString())) {
                sb.append(" (current save file)");
            }
            sb.append("\n");
        }

        return new CommandResult(String.format("Listing all save files: \n%s", sb.toString()));
    }
}
