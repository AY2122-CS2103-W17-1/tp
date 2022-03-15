package seedu.contax.logic.parser;

import static seedu.contax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.contax.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.util.HashSet;
import java.util.stream.Stream;

import seedu.contax.logic.commands.ImportCsvCommand;
import seedu.contax.logic.parser.exceptions.ParseException;
import seedu.contax.model.IndexedCsvFile;

/**
 * Parses input arguments and creates a new ImportCsvCommand object
 */
public class ImportCsvParser implements Parser<ImportCsvCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCsvCommand
     * and returns a ImportCsvCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCsvCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_FILE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        if (!argMultimap.arePrefixesPresent(PREFIX_FILE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCsvCommand.MESSAGE_USAGE));
        }

        File file = ParserUtil.parseCsvFilePath(argMultimap.getValue(PREFIX_FILE).get());

        //check if file exists, else throw exception

        int namePosition = 1;
        int phonePosition = 2;
        int emailPosition = 3;
        int addressPosition = 4;
        int tagPosition = 5;
        if (argMultimap.arePrefixesPresent(PREFIX_NAME)) {
            namePosition = ParserUtil.parseCsvPositions(argMultimap.getValue(PREFIX_NAME).get());
        }
        if (argMultimap.arePrefixesPresent(PREFIX_PHONE)) {
            phonePosition = ParserUtil.parseCsvPositions(argMultimap.getValue(PREFIX_PHONE).get());
        }
        if (argMultimap.arePrefixesPresent(PREFIX_EMAIL)) {
            emailPosition = ParserUtil.parseCsvPositions(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        if (argMultimap.arePrefixesPresent(PREFIX_ADDRESS)) {
            addressPosition = ParserUtil.parseCsvPositions(argMultimap.getValue(PREFIX_ADDRESS).get());
        }
        if (argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            tagPosition = ParserUtil.parseCsvPositions(argMultimap.getValue(PREFIX_TAG).get());
        }

        //Check if all the positions are distinct
        HashSet<Integer> positionHashSet = new HashSet<>();
        positionHashSet.add(namePosition);
        checkDuplicateAndAdd(positionHashSet, phonePosition);
        checkDuplicateAndAdd(positionHashSet, emailPosition);
        checkDuplicateAndAdd(positionHashSet, addressPosition);
        checkDuplicateAndAdd(positionHashSet, tagPosition);

        IndexedCsvFile indexedCsvFileObject = new IndexedCsvFile(file, namePosition, phonePosition,
                emailPosition, addressPosition, tagPosition);

        return new ImportCsvCommand(indexedCsvFileObject);

    }

    /**
     * Checks if the integer doesn't exist, add to {@code HashSet}, else throw {@code ParseException}
     * Used to check for duplicate positions
     */
    private void checkDuplicateAndAdd(HashSet<Integer> hashSet, int position) throws ParseException {
        if (!hashSet.contains(position)) {
            hashSet.add(position);
        } else {
            throw new ParseException(IndexedCsvFile.MESSAGE_CLASHING_POSITIONS);
        }
    }

}
