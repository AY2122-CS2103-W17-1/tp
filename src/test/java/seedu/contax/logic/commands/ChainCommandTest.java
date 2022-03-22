package seedu.contax.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.contax.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.contax.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.contax.logic.commands.exceptions.CommandException;
import seedu.contax.model.AddressBook;
import seedu.contax.model.Model;
import seedu.contax.model.ModelManager;
import seedu.contax.model.Schedule;
import seedu.contax.model.UserPrefs;
import seedu.contax.model.person.Person;
import seedu.contax.testutil.EditPersonDescriptorBuilder;
import seedu.contax.testutil.PersonBuilder;

public class ChainCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new Schedule(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new Schedule(), new UserPrefs());
    }

    @Test
    public void equals() {
        ChainCommand testChainCommand = new ChainCommand(List.of(new ListPersonCommand()));
        ChainCommand testChainCommand2 = new ChainCommand(new ArrayList<Command>());

        // same object -> returns true
        assertTrue(testChainCommand.equals(testChainCommand));

        // same values -> returns true

        ChainCommand expectedChainCommandCopy =
                new ChainCommand(List.of(new ListPersonCommand()));
        assertTrue(testChainCommand.equals(expectedChainCommandCopy));

        // different types -> returns false
        assertFalse(testChainCommand.equals(1));

        // null -> returns false
        assertFalse(testChainCommand.equals(null));

        // different command -> returns false
        assertFalse(testChainCommand.equals(testChainCommand2));
    }

    @Test
    public void execute_chainedList_success() throws CommandException {
        ChainCommand testChainCommand = new ChainCommand(List.of(new ListPersonCommand()));
        assertEquals(testChainCommand.execute(model).getFeedbackToUser().trim(),
                new ListPersonCommand().execute(model).getFeedbackToUser());
    }

    @Test
    public void execute_chainedEditThenList_success() throws CommandException {
        Person editedPerson = new PersonBuilder().build();
        EditPersonCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_PERSON, descriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Schedule(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        List<Command> commandList = new ArrayList<>();

        commandList.add(editPersonCommand);
        commandList.add(new ListPersonCommand());

        ChainCommand testChainCommand = new ChainCommand(commandList);
        assertEquals(testChainCommand.execute(model).getFeedbackToUser().trim(), (
                "Edited Person: Amy Bee; Phone: 85355255; Email: amy@gmail.com; "
                + "Address: 123, Jurong West Ave 6, #08-111\n"
                + "Listed all persons"));
    }
}
