package de.uniba.wiai.dsg.ajp.assignment2.literature.ui;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;

import java.io.IOException;

/**
 * UI class to write main menu and handle user input.
 *
 * @author Group29
 * @version 1.0
 */
public class MainMenu {

    private static final int LOAD = 1;
    private static final int NEW = 2;
    private static final int EXIT = 0;

    private final MainService mainService;
    private final ExtendedConsoleHelper consoleHelper;

    public MainMenu(MainServiceImpl mainService) {
        super();
        this.mainService = mainService;
        this.consoleHelper = ExtendedConsoleHelper.build();
    }

    /**
     * Creates a menu, lets the user select a value from a list of values.
     *
     * This is a factory method.
     */
    public void open() throws IOException, LiteratureDatabaseException {
        boolean insideMenu = true;
        while (insideMenu) {
            printMainMenu();
            try {
                int option = consoleHelper.askIntegerInRange("Please enter option", 0, 2);
                switch (option) {
                    case LOAD:
                        String pathToLoad = consoleHelper.askNonEmptyString("Please enter the path of the file to load: ");
                        mainService.validate(pathToLoad);
                        new SubMenu(mainService.load(pathToLoad), consoleHelper).open();
                        break;
                    case NEW:
                        new SubMenu(mainService.create(), consoleHelper).open();
                        break;
                    case EXIT:
                        consoleHelper.getOut().println("Exiting ...");
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
     * Helper method to print out menu
     */
    private void printMainMenu() {
        consoleHelper.getOut().format(" (%d) Load and Validate Literature Database %n", LOAD);
        consoleHelper.getOut().format(" (%d) Create New Literature Database %n", NEW);
        consoleHelper.getOut().format(" (%d) Exit System %n", EXIT);
    }

}
