package edu.ncsu.csc.crowdfarm.validate;

import java.util.regex.Pattern;

/**
 * All of the validation formats
 *
 */

public enum ValidationFormat {
	//Registration Form
	FIRST("[a-zA-Z'\\s\\-]{1,50}", 							"Up to 40 letters, space, ', and -"),
	MI("[a-zA-Z]{1}", 									"Only one letter allowed for middle initial"),
	LAST("[a-zA-Z'\\-\\s]{1,50}", 							"Up to 40 letters, space, ', and -"),
	PHONE_NUMBER("[\\d]{3}-[\\d]{3}-[\\d]{4}", 				"xxx-xxx-xxxx"),
	ADDRESS("[a-zA-Z0-9.,\\-\\s]{1,100}", 					"Up to 100 alphanumeric characters, and .,-"),
	DESCRIPTION("[a-zA-Z0-9.\\-',!;:()?\\s\\/]{1,500}", 	"Up to 500 alphanumeric characters and .-',!;:()?"),
	EMAIL("^[_A-Za-z0-9\\-]+(\\.[_A-Za-z0-9\\-]+)*@[A-Za-z0-9\\-]+(\\.[A-Za-z0-9\\-]+)*((\\.[A-Za-z]{2,}){1}$)",		
															"Up to 30 alphanumeric characters and symbols . and _ @"), 
			
	//New Produce Form
	PRODUCE_TYPE("[a-zA-Z\\s]{1,50}", 						"Up to 50 letters and spaces"),	
	QUANITY("[0-9]{1,10}",									"Up to 10 digits"),
	PICK_DATE("[\\d]{2}/[\\d]{2}/[\\d]{4}", 				"MM/DD/YYYY"),
	SHELF_LIFE("[0-9]{1,5}",								"Up to 5 digits"),
	COST("[0-9]{1,5}+(\\.[0-9]{2})",						"xx.xx");
	
	private Pattern regex;
	private String description;
	
	ValidationFormat(String regex, String errorMessage) {
		this.regex = Pattern.compile(regex);
		this.description = errorMessage;
	}
	
	public Pattern getRegex() {
		return regex;
	}
	
	public String getDescription() {
		return description;
	}
}
