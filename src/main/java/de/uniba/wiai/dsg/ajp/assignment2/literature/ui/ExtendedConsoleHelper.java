package de.uniba.wiai.dsg.ajp.assignment2.literature.ui;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A helper utility to read and write from streams with multiple values and catch Exceptions.
 *
 * @author Group29
 * @version 1.0
 */
public class ExtendedConsoleHelper extends ConsoleHelper {

    public ExtendedConsoleHelper(BufferedReader in, PrintStream out) {
        super(in, out);
    }

    /**
     * Creates a {@link ExtendedConsoleHelper} using System.in and System.out for
     * their streams.
     * <p>
     * This is a factory method.
     * <p>
     * Usage
     *
     * <code>
     * ExtendedConsoleHelper console = ExtendedConsoleHelper.build();
     * </code>
     *
     * @return the configured {@link ExtendedConsoleHelper}
     */
    public static ExtendedConsoleHelper build() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return new ExtendedConsoleHelper(reader, System.out);
    }

    /**
     * Lets the user input multiple values with a single line. The values must be
     * separated by commas (",")
     *
     * @param message the message shown to the user
     * @return a set of strings parsed from the user input by separation through
     * commas
     * @throws IOException if an error occurs during reading from or writing to a stream
     */
    public List<String> askForMultipleEntries(String message) throws IOException {
        List<String> entries = new LinkedList<>();
        String allEntries = askString(message + System.lineSeparator() + "Separate multiple values with commas");
        if (!allEntries.trim().isEmpty()) {
            entries = Arrays.asList(allEntries);
        }
        return entries;
    }

    /**
     * Lets the user select a value from a list of values
     *
     * @param values a list of the values to select from
     * @return the selected value
     * @throws IOException if and error occurs during reading or writing to a stream
     */
    public <T> T askValueFromList(List<T> values) throws IOException, LiteratureDatabaseException {
        int count = -1;
        for (T value : values) {
            count++;
            getOut().format(" (%d) %s %n", count, value.toString());
        }

        return values.get(askIntegerInRange("Publication type:", 0, count));
    }

    @Override
    public int askIntegerInList(String message, List<Integer> correctValues)
            throws IOException {
        Objects.requireNonNull(correctValues,
                "passed list correctValues is null");
        if (correctValues.isEmpty()) {
            throw new IllegalArgumentException(
                    "passed list correctValues is empty");
        }

        int enteredValue = askInteger(message);

        if (!correctValues.contains(enteredValue)) {
            throw new IOException("ERROR: Entered integer (" + enteredValue + ") is not allowed.");
        } else {
            return enteredValue;
        }

    }

    @Override
    public int askInteger(String message) throws IOException {
        String line = askString(message);
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IOException("ERROR: Entered line (" + line + ") is no integer.");
        }

    }

    @Override
    public String askNonEmptyString(String message) throws IOException {
        String line = askString(message);
        if (line.isEmpty()) {
            throw new IOException("ERROR: Given string is empty!");
        } else {
            return line;
        }
    }

}
