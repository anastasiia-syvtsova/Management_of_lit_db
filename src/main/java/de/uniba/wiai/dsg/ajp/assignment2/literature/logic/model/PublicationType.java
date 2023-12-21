package de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PublicationType {

	@XmlEnumValue("article") ARTICLE,
	@XmlEnumValue("techreport") TECHREPORT,
	@XmlEnumValue("book") BOOK,
	@XmlEnumValue("masterthesis") MASTERSTHESIS,
	@XmlEnumValue("phdthesis") PHDTHESIS,
	@XmlEnumValue("inproceedings") INPROCEEDINGS

}