package de.uniba.wiai.dsg.ajp.assignment2.literature.logic;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helps to validate IDs and email addresses.
 * 
 */
public class ValidationHelper {

	private static final String ZERO_OR_MORE = "*";
	private static final String ONE_OR_MORE = "+";
	private static final String ANY_LETTER_OR_CONSTANT = "[a-zA-Z0-9]";
	private static final String START_WITH_LETTER = "[a-zA-Z]";
	private static final String VALID_NAME = "[a-zA-Z0-9._%-]" + ONE_OR_MORE;
	private static final String AT_SIGN = "[@]";
	private static final String VALID_DOMAIN = "[a-zA-Z0-9.-]" + ONE_OR_MORE;
	private static final String DOT = "[.]";
	private static final String COUNTRY_CODE = "[a-zA-Z]{2,4}";
	private static final Pattern VALID_ID_REGEX = Pattern
			.compile(START_WITH_LETTER + ANY_LETTER_OR_CONSTANT + ZERO_OR_MORE);
	private static final Pattern VALID_EMAIL_REGEX = Pattern
			.compile(VALID_NAME + AT_SIGN + VALID_DOMAIN + DOT + COUNTRY_CODE);

	/**
	 * Validates an ID.
	 * 
	 * An ID has to start with a letter followed by zero or more letters or numbers.
	 * 
	 * @param id the id to be checked. must not be null.
	 * @return true if the id is valid, false otherwise
	 * 
	 * @throws NullPointerException if id is null
	 */
	public static boolean isId(String id) {
		Objects.requireNonNull(id, "input must not be null");

		Matcher idMatcher = VALID_ID_REGEX.matcher(id);
		return idMatcher.matches();
	}

	/**
	 * Validates an email address.
	 * 
	 * This is a simple validation and does not conform to all correctness criteria
	 * for email addresses.
	 * 
	 * @param email the email to be validated. must not be null.
	 * @return true if the email is valid, false otherwise
	 * 
	 * @throws NullPointerException if email is null
	 */
	public static boolean isEmail(String email) {
		email = Objects.requireNonNull(email, "email passed is null");

		Matcher emailMatcher = VALID_EMAIL_REGEX.matcher(email);
		return emailMatcher.matches();
	}

}
