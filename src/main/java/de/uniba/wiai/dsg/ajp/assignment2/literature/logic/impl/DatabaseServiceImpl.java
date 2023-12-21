package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Author;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Publication;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.PublicationType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import static de.uniba.wiai.dsg.ajp.assignment2.literature.logic.ValidationHelper.isEmail;
import static de.uniba.wiai.dsg.ajp.assignment2.literature.logic.ValidationHelper.isId;

public class DatabaseServiceImpl implements DatabaseService {

    private final Database database;

    public DatabaseServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addPublication(String title, int yearPublished, PublicationType type, List<String> authorIDs, String id)
            throws LiteratureDatabaseException {
        if (title == null || title.length() <= 0) {
            throw new LiteratureDatabaseException("The title of the publication must not be empty or null!");
        }

        if (yearPublished <= 0) {
            throw new LiteratureDatabaseException("The year the publication must not be negative!");
        }

        if (type == null) {
            throw new LiteratureDatabaseException("The type of the publication must not be null!");
        }

        if (!isId(id)) {
            throw new LiteratureDatabaseException("The id of the publication must be a valid! An ID has" +
                    " to start with a letter followed by zero or more letters or numbers.");
        }

        if (!isUniquePublicationId(id)) {
            throw new LiteratureDatabaseException("The id of the publication must be unique within the current " +
                    "database!");
        }

        List<Author> authorsList = database.getAuthorsByIds(authorIDs);

        if (authorsList.size() == 0) {
            throw new LiteratureDatabaseException("The ID(s) of the author(s) does not match with database! Add " +
                    "author(s) first");
        }

        Publication publication = new Publication(authorsList, id, title, type, yearPublished);

        try {
            for (Author author : authorsList) {
                List<Publication> publications = author.getPublications();
                publications.add(publication);
                author.setPublications(publications);
            }
        } catch (Exception e) {
            throw new LiteratureDatabaseException("Could not add publication" + e.getMessage());
        }
        database.addPublication(publication);
        database.setAuthors(authorsList);
    }

    @Override
    public void removePublicationByID(String id) throws LiteratureDatabaseException {
        if (id == null || id.length() <= 0) {
            throw new LiteratureDatabaseException("The ID of the publication to be removed must not be empty or null!");
        }

        if (isUniquePublicationId(id)) {
            throw new LiteratureDatabaseException(String.format("There is no publication with the ID: %s!", id));
        }

        var publications = database.getPublications();
        var authors = database.getAuthors();

        try {
            publications.removeIf(publication -> publication.getId().equals(id));

            for (Author author : authors) {
                var publicationsOfTheAuthor = author.getPublications();
                publicationsOfTheAuthor.removeIf(publication -> publication.getId().equals(id));
                author.setPublications(publicationsOfTheAuthor);
            }

        } catch (Exception e) {
            throw new LiteratureDatabaseException("Could not remove publication" + e.getMessage());
        }
        database.setPublications(publications);
        database.setAuthors(authors);
        System.out.printf("Publication with ID (%s) successfully removed!%n", id);
    }


    @Override
    public void removeAuthorByID(String id) throws LiteratureDatabaseException {
        if (id == null || id.length() <= 0) {
            throw new LiteratureDatabaseException("The ID of the author to be removed must not be empty or null!");
        }
        if (isUniqueAuthorId(id)) {
            throw new LiteratureDatabaseException(String.format("There is no author with the ID: %s!", id));
        }

        var authors = database.getAuthors();

        try {
            if (authors.removeIf(author -> author.getId().equals(id) && author.getPublications().size() == 0)) {
                System.out.printf("Author with the ID (%s) removed successful.%n", id);
            } else
                System.out.printf("Could not remove the author with the ID (%s). Please remove the publication(s) of the author first%n", id);
        } catch (Exception e) {
            throw new LiteratureDatabaseException("Could not remove author with ID: " + id);
        }
        database.setAuthors(authors);
    }

    @Override
    public void addAuthor(String name, String email, String id) throws LiteratureDatabaseException {

        var authors = database.getAuthors();

        if (name == null || name.length() <= 0) {
            throw new LiteratureDatabaseException("The name of the author must not be null or empty!");
        }

        if (email.length() <= 0) {
            throw new LiteratureDatabaseException("The email address of the author must not be empty!");
        }

        if (!isEmail(email)) {
            throw new LiteratureDatabaseException("The email address of the author must be valid!");
        }

        if (!isId(id)) {
            throw new LiteratureDatabaseException("The id of the author must be a valid! An ID has" +
                    " to start with a letter followed by zero or more letters or numbers.");
        }

        if (!isUniqueAuthorId(id)) {
            throw new LiteratureDatabaseException("The id of the author must be unique within the current database!");
        }

        var author = new Author();
        try {
            author.setName(name);
            author.setEmail(email);
            author.setId(id);
            authors.add(author);
        } catch (Exception e) {
            throw new LiteratureDatabaseException("Could not add author " + e.getMessage());
        }

        database.setAuthors(authors);
    }


    @Override
    public List<Publication> getPublications() {
        return database.getPublications();
    }

    @Override
    public List<Author> getAuthors() {
        return database.getAuthors();
    }

    @Override
    public void clear() {
        List<Author> authors = new LinkedList<>();
        List<Publication> publications = new LinkedList<>();

        database.setPublications(publications);
        database.setAuthors(authors);

    }

    @Override
    public void printXMLToConsole() throws LiteratureDatabaseException {
        if (database == null) {
            throw new LiteratureDatabaseException("There are errors while marshalling the current database!");
        }

        try {
            Marshaller ms = getMarshaller();
            ms.marshal(database, System.out);
        } catch (JAXBException e) {
            throw new LiteratureDatabaseException("Could not print to console: " + e.getMessage());
        }

    }

    @Override
    public void saveXMLToFile(String path) throws LiteratureDatabaseException {
        if (path == null || path.length() <= 0) {
            throw new LiteratureDatabaseException("The path of the file must not be null or empty!");
        }

        try {
            Marshaller ms = getMarshaller();
            ms.marshal(database, new FileOutputStream(path));
        } catch (JAXBException | FileNotFoundException e) {
            throw new LiteratureDatabaseException("Could not save to file: " + e.getMessage());
        }

    }

    /**
     * Generates Marshaller Object
     *
     * @return Marshaller Object
     * @throws JAXBException
     */
    private Marshaller getMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Database.class);
        Marshaller ms = context.createMarshaller();
        ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return ms;
    }

    /**
     * Validates the ID to be against the List of the Authors
     *
     * @param id The ID to validate
     * @return false if the ID is not unique
     */
    public boolean isUniqueAuthorId(String id) {

        var authors = database.getAuthors();

        for (Author author : authors) {
            if (author.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the ID to be against the List of the Publications
     *
     * @param id The ID to validate
     * @return false if the ID is not unique
     */
    public boolean isUniquePublicationId(String id) {

        var publications = database.getPublications();

        for (Publication publication : publications) {
            if (publication.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }
}
