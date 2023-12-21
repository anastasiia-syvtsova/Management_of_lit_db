package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
public class Publication {

	@XmlElement(name = "author", required = true)
	@XmlIDREF
	private List<Author> authors = new LinkedList<>();
	@XmlElement(required = true)
	@XmlID
	private String id;
	@XmlElement(required = true)
	private String title;
	@XmlAttribute(required = true)
	private PublicationType type;
	@XmlAttribute(required = true)
	private int yearPublished;

	public Publication(List<Author> authors, String id, String title, PublicationType type, int yearPublished) {
		this.authors = authors;
		this.id = id;
		this.title = title;
		this.type = type;
		this.yearPublished = yearPublished;
	}

	public Publication() {
		super();
	}

	public PublicationType getType() {
		return type;
	}

	public void setType(PublicationType type) {
		this.type = type;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	@Override
	public String toString() {
		return String.format(
				"[%s] The author(s) %s published %s as a %s in %d", id,
				getAuthorNames(), title, type, yearPublished);
	}

	private String getAuthorNames() {
		StringJoiner result = new StringJoiner(", ");
		for (int i = 0; i < authors.size(); i++) {
			result.add(authors.get(i).getName());
		}
		return result.toString();
	}
}