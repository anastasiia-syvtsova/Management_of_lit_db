package de.uniba.wiai.dsg.ajp.assignment2.literature.ui;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * UI class to write submenu and handle user input.
 *
 * @author Group29
 * @version 1.0
 */
public class SubMenu {

    private static final int ADD_AUTHOR = 1;
    private static final int REMOVE_AUTHOR = 2;
    private static final int ADD_PUBLICATION = 3;
    private static final int REMOVE_PUBLICATION = 4;
    private static final int LIST_AUTHORS = 5;
    private static final int LIST_PUBLICATIONS = 6;
    private static final int PRINT_TO_CONSOLE = 7;
    private static final int SAVE_TO_FILE = 8;
    private static final int BACK_TO_MAIN_MENU = 0;

    private final ExtendedConsoleHelper consoleHelper;
    private final DatabaseService databaseService;

    public SubMenu(DatabaseService databaseService, ExtendedConsoleHelper consoleHelper) {
        super();
        this.consoleHelper = consoleHelper;
        this.databaseService = databaseService;
    }

    /**
     * Creates submenu, lets the user select a value from a list of values.
     *
     * <p>
     * This is a factory method.
     * <p>
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    public void open() throws IOException, LiteratureDatabaseException {
        boolean insideMenu = true;
        while (insideMenu) {
            printLiteratureDatabaseMenu();
            try {
                int option = consoleHelper.askIntegerInRange("Please enter your choice", 0, 8);
                switch (option) {

                    case ADD_AUTHOR:
                        addAuthor();
                        break;

                    case REMOVE_AUTHOR:
                        removeAuthor();
                        break;

                    case ADD_PUBLICATION:
                        addPublication();
                        break;

                    case REMOVE_PUBLICATION:
                        removePublication();
                        break;

                    case LIST_AUTHORS:
                        listAuthors();
                        break;

                    case LIST_PUBLICATIONS:
                        listPublications();
                        break;

                    case PRINT_TO_CONSOLE:
                        printDatabase();
                        break;

                    case SAVE_TO_FILE:
                        saveToFile();
                        break;

                    case BACK_TO_MAIN_MENU:
                        insideMenu = false;
                        break;
                }
            } catch (LiteratureDatabaseException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("Please try again because of an " + e.getMessage());
            }
        }
    }

    /**
     * Helper method to save the XML to the file.
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void saveToFile() throws IOException, LiteratureDatabaseException {
        var path = consoleHelper.askNonEmptyString("Please enter path to save database");
        this.databaseService.saveXMLToFile(path);
    }

    /**
     * Helper method to print database to console.
     *
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void printDatabase() throws LiteratureDatabaseException {
        this.databaseService.printXMLToConsole();
    }

    /**
     * Helper method to print publication list to console
     */
    private void listPublications() {
        var publications = databaseService.getPublications();
        for (Publication publication : publications) {
            System.out.println(publication.toString());
        }
    }

    /**
     * Helper method to remove publication using the ID of the publication.
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void removePublication() throws IOException, LiteratureDatabaseException {
        var id = consoleHelper.askNonEmptyString("Please enter the ID of publication to remove");
        this.databaseService.removePublicationByID(id);
    }

    /**
     * Helper method to add publication.
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void addPublication() throws IOException, LiteratureDatabaseException {
        var title = consoleHelper.askNonEmptyString("Please enter the publication title");
        var yearPublished = consoleHelper.askInteger("Please enter year of publication");
        PublicationType type = consoleHelper.askValueFromList(Arrays.asList(PublicationType.values()));
        List<String> authors = consoleHelper.askForMultipleEntries("Enter IDs of the authors. ");
        var id = consoleHelper.askNonEmptyString("Please enter publications ID");

        this.databaseService.addPublication(title, yearPublished, type, authors, id);
    }

    /**
     * Helper method to print authors to console.
     */
    private void listAuthors() {
        var authors = databaseService.getAuthors();
        for (Author author : authors) {
            System.out.println(author.toString());
        }
    }

    /**
     * Helper method to remove author from database.
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void removeAuthor() throws IOException, LiteratureDatabaseException {
        var id = consoleHelper.askNonEmptyString("Pleas enter ID of the author to remove");
        this.databaseService.removeAuthorByID(id);
    }

    /**
     * Helper method to add author.
     *
     * @throws IOException                 if an error occurs during reading from or writing to a stream
     * @throws LiteratureDatabaseException if an error occurs during processing input
     */
    private void addAuthor() throws IOException, LiteratureDatabaseException {
        var id = consoleHelper.askNonEmptyString("Please enter authors name");
        var email = consoleHelper.askNonEmptyString("Pleas enter authors email");
        id = consoleHelper.askNonEmptyString("Pleas enter authors ID");
        this.databaseService.addAuthor(id, email, id);
    }

    /**
     * Prints aut the menu.
     */
    private void printLiteratureDatabaseMenu() {
        consoleHelper.getOut().format(" (%d) Add Author %n", ADD_AUTHOR);
        consoleHelper.getOut().format(" (%d) Remove Author %n", REMOVE_AUTHOR);
        consoleHelper.getOut().format(" (%d) Add Publication %n", ADD_PUBLICATION);
        consoleHelper.getOut().format(" (%d) Remove Publication %n", REMOVE_PUBLICATION);
        consoleHelper.getOut().format(" (%d) List Authors %n", LIST_AUTHORS);
        consoleHelper.getOut().format(" (%d) List Publications %n", LIST_PUBLICATIONS);
        consoleHelper.getOut().format(" (%d) Print XML on Console %n", PRINT_TO_CONSOLE);
        consoleHelper.getOut().format(" (%d) Save XML to File %n", SAVE_TO_FILE);
        consoleHelper.getOut().format(" (%d) Back to main menu / close without saving %n", BACK_TO_MAIN_MENU);
    }
}
