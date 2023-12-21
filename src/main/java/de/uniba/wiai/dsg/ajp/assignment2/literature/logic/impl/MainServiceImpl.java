package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.impl;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.DatabaseService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.LiteratureDatabaseException;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.MainService;
import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.nio.file.Path;

public class MainServiceImpl implements MainService {

    /**
     * Default constructor required for grading.
     */
    public MainServiceImpl() {
        /*
         * DO NOT REMOVE - REQUIRED FOR GRADING
         *
         * YOU CAN EXTEND IT BELOW THIS COMMENT
         */
    }

    @Override
    public void validate(String path) throws LiteratureDatabaseException {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(Path.of("dbschema.xsd").toFile());
            Validator validator = schema.newValidator();

            try {
                validator.validate(new StreamSource(Path.of(path).toFile()));
                System.out.println("Validation successful!");
            } catch (SAXException e) {
                throw new LiteratureDatabaseException("The provided file " + path + " does not match the schema", e);
            } catch (IOException e) {
                throw new LiteratureDatabaseException("The file " + path + " could not be read", e);
            }
        } catch (SAXException e) {
            throw new LiteratureDatabaseException("The provided schema could not be read", e);
        }

	}

	@Override
	public DatabaseService load(String path) throws LiteratureDatabaseException {
		try {
			JAXBContext context = JAXBContext.newInstance(Database.class);
			Unmarshaller um = context.createUnmarshaller();
			Database database = (Database) um.unmarshal(Path.of(path).toFile());
			return new DatabaseServiceImpl(database);
		} catch (JAXBException e) {
            throw new LiteratureDatabaseException("The load of the XML file failed", e);
		}
	}

    @Override
    public DatabaseService create() throws LiteratureDatabaseException {
        try {
            Database database = new Database();
            return new DatabaseServiceImpl(database);
        } catch (Exception e) {
            throw new LiteratureDatabaseException("The database was not crated", e);
        }
    }

}
