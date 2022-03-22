package seedu.contax.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.contax.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.contax.commons.core.GuiListContentType;
import seedu.contax.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends Command {

    public static final String COMMAND_WORD = "listpersons";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, GuiListContentType.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ListPersonCommand);
    }
}
