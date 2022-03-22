package seedu.contax.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.contax.logic.commands.exceptions.CommandException;
import seedu.contax.model.Model;

/**
 * Adds chain command.
 */
public class ChainCommand extends Command {
    public static final String COMMAND_WORD = "chain";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": [Command] && [Command]";

    private final List<Command> commands;
    /**
     * Creates Commands list
     */
    public ChainCommand(List<Command> userCommands) {
        requireNonNull(userCommands);
        commands = userCommands;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<CommandResult> commandResultList = new ArrayList<>();
        for (Command command:commands) {
            commandResultList.add(command.execute(model));
        }
        StringBuilder resultOutput = new StringBuilder();
        for (CommandResult result: commandResultList) {
            resultOutput.append(result.getFeedbackToUser()).append("\n");
        }
        return new CommandResult(resultOutput.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ChainCommand) {
            for (int i = 0; i < commands.size(); i++) {
                if (((ChainCommand) other).commands.size() != 0) {
                    if (!commands.get(i).equals(((ChainCommand) other).commands.get(i))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
