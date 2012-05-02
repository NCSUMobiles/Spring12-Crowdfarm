package edu.ncsu.csc.crowdfarm.validate;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;

public class RegistrationValidator extends BeanValidator<RegistrationBean> { //add the bean form type in the parameters

	/*
	 * default constructor
	 */
	public RegistrationValidator() {
		
	}
	
	/*
	 * Validates the bean to make sure all data has been entered correctly.
	 * 
	 * @param b A NewProduce bean to be validated
	 */
	@Override
	public List<String> validate(RegistrationBean b) {
		List<String> errorList = new ArrayList<String>();
		String value = null;
		
		value = checkFormat("Email", b.getEmail(), ValidationFormat.EMAIL, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("First Name", b.getFirstName(), ValidationFormat.FIRST, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("MI", b.getMiddleInitial(), ValidationFormat.MI, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("Last Name", b.getLastName(), ValidationFormat.LAST, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("Location", b.getLocation(), ValidationFormat.ADDRESS, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("Phone", b.getPhone(), ValidationFormat.PHONE_NUMBER, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("Description", b.getDescription(), ValidationFormat.DESCRIPTION, true);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		return errorList;
	}
}
