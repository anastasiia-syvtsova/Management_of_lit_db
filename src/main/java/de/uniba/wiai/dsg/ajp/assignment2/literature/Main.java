package de.uniba.wiai.dsg.ajp.assignment2.literature;


import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl.MainServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.literature.ui.MainMenu;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class Main {
    /**
     * Test
     **/
    public static void main(String[] args) throws LiteratureDatabaseException, JAXBException, IOException {

        MainServiceImpl mainService = new MainServiceImpl();
        MainMenu mainMenu = new MainMenu(mainService);
        mainMenu.open();

    }

}
