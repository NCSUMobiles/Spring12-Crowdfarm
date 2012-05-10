package edu.ncsu.csc.crowdfarm.validate;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.crowdfarm.beans.ProduceBean;


public class ProduceValidator extends BeanValidator<ProduceBean>{ 
	
	
	/*
	 * Validates the bean to make sure all data has been entered correctly.
	 * 
	 * @param b A NewProduce bean to be validated
	 */
	public List<String> validate(ProduceBean b) {
		List<String> errorList = new ArrayList<String>();
		String value = null;
		
		value = checkFormat("produce_type", b.getType(), ValidationFormat.PRODUCE_TYPE, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("quanity", b.getAmount(), ValidationFormat.QUANITY, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("cost", b.getCost(), ValidationFormat.COST, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		value = checkFormat("pick_date", b.getPickDate(), ValidationFormat.PICK_DATE, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}

		value = checkFormat("shelf_life", b.getShelfLife(), ValidationFormat.SHELF_LIFE, false);
		if( !(value.equals("")) ) {
			errorList.add(value);
		}
		
		return errorList;
	}

}
