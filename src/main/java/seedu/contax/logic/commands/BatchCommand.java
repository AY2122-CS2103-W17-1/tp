package seedu.contax.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.contax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_EQUALS;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_SEARCH_TYPE;

import java.util.ArrayList;
import java.util.List;

import seedu.contax.commons.core.index.Index;
import seedu.contax.logic.commands.exceptions.CommandException;
import seedu.contax.logic.parser.AddressBookParser;
import seedu.contax.logic.parser.ParserUtil;
import seedu.contax.logic.parser.exceptions.ParseException;
import seedu.contax.model.Model;
import seedu.contax.model.person.Person;
import seedu.contax.model.util.SearchType;




/**
 * Batch edit or delete a person identified using base on string =/ provided.
 */
public class BatchCommand extends Command {
    public static final String COMMAND_WORD = "batch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Perform command in batch \n"
            + "Parameters: "
            + "COMMAND (must be valid command without index) "
            + PREFIX_SEARCH_TYPE + "SEARCH_TYPE "
            + PREFIX_EQUALS + "PATTERN \n"
            + "Example: " + COMMAND_WORD + " edit "
            + PREFIX_SEARCH_TYPE + "phone "
            + PREFIX_EQUALS + "^123 ";

    private final String commandInput;
    private final SearchType searchType;
    private final String userInput;

    /**
     * Creates an BatchCommand base on specified {@code commandInput} {@code searchType} and {@code userInput}.
     *
     * @param commandInput              details to word of command
     * @param searchType                search type of field apply matcher
     * @param userInput                 regex provided by user
     */
    public BatchCommand(String commandInput, SearchType searchType, String userInput) {
        requireNonNull(commandInput);
        requireNonNull(searchType);
        requireNonNull(userInput);
        this.commandInput = commandInput;
        this.searchType = searchType;
        this.userInput = userInput.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Index> indexList = matchInputStringToIndex(model, searchType, userInput);
        if (indexList.size() == 0) {
            return new CommandResult(COMMAND_WORD
                    + ": No result matching \"" + userInput + "\"");
        }
        List<CommandResult> commandResultList = new ArrayList<>();
        for (Index index: indexList) {
            AddressBookParser addressBookParser = new AddressBookParser();
            try {
                String commandText = ParserUtil.parseAndCreateNewCommand(
                        commandInput, Integer.toString(index.getOneBased()));
                Command command = addressBookParser.parseCommand(commandText);
                commandResultList.add(command.execute(model));
            } catch (ParseException pe) {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
            }
        }
        StringBuilder resultOutput = new StringBuilder();
        for (CommandResult result: commandResultList) {
            resultOutput.append(result.getFeedbackToUser()).append("\n");
        }
        return new CommandResult(resultOutput.toString());
    }

    private List<Index> matchInputStringToIndex(Model model, SearchType searchType, String userInput)
            throws CommandException {
        List<Index> indexList = new ArrayList<>();
        List<Person> personList = model.getFilteredPersonList();
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            Index index = Index.fromZeroBased(i);

            String targetField;
            switch (searchType.searchType) {
            case SearchType.TYPE_PHONE:
                targetField = person.getPhone().toString();
                break;
            case SearchType.TYPE_EMAIL:
                targetField = person.getEmail().toString();
                break;
            case SearchType.TYPE_ADDRESS:
                targetField = person.getAddress().toString();
                break;
            default:
                targetField = person.getName().toString();
                break;
            }
            if (!targetField.isEmpty() && targetField.equals(userInput)) {
                indexList.add(index);
            }

        }
        return indexList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchCommand // instanceof handles nulls
                && commandInput.equals(((BatchCommand) other).commandInput)
                && searchType.equals(((BatchCommand) other).searchType)
                && userInput.equals(((BatchCommand) other).userInput));
    }
}
