package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Database {

	@XmlElement(name = "author")
	private List<Author> authors = new LinkedList<>();


	@XmlElement(name = "publication")
	private List<Publication> publications = new LinkedList<>();

	public Database() {
		super();
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public void addPublication(Publication publication) {
		this.publications.add(publication);
	}

	public List<Author> getAuthorsByIds(List<String> authorIDs) {
		List<Author> authors = new LinkedList<>();
		List<Author> allAuthors;
		allAuthors = this.authors;

		for (Author allAuthor : allAuthors) {
			if (authorIDs.contains(allAuthor.getId())) {
				authors.add(allAuthor);
			}
		}
		return authors;
	}

}
