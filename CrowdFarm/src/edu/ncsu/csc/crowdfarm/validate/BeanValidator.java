package edu.ncsu.csc.crowdfarm.validate;

import java.util.List;
import edu.ncsu.csc.crowdfarm.validate.ValidationFormat;


/**
 * 
 * @param 
 */
abstract public class BeanValidator<T> {
	abstract public List<String> validate(T bean);
	
	/**
	 * Check the format against the given enum. isNullable will check if the string is empty or a Java null.
	 * Otherwise, an error message will be returned. Use this in conjunction with {@link ErrorList}.
	 * 
	 * @param name
	 * @param value
	 * @param format
	 * @param isNullable
	 * @return
	 */
	protected String checkFormat(String name, String value, ValidationFormat format, boolean isNullable) {
		 
		String errorMessage = name + ": " + format.getDescription();
		 if (value == null || "".equals(value))
			return isNullable ? "" : errorMessage;
		else if (format.getRegex().matcher(value).matches())
			return "";
		else
			return errorMessage;
	}

	/**
	 * Check a long value against a particular format. isNullable will check if it is empty or a Java null.
	 * Otherwise, an error message will be returned. Use this in conjunction with {@link ErrorList}.
	 * 
	 * @param name
	 * @param longValue
	 * @param format
	 * @param isNullable
	 * @return
	 */
	protected String checkFormat(String name, Long longValue, ValidationFormat format, boolean isNullable) {
		String str = "";
		if (longValue != null)
			str = String.valueOf(longValue);
		return checkFormat(name, str, format, isNullable);
	}

	/**
	 * Check the format against the given enum. isNullable will check if it is a Java null. Otherwise, an
	 * error message will be returned. Use this in conjunction with {@link ErrorList}.
	 * 
	 * @param name
	 * @param doubleValue
	 * @param format
	 * @param isNullable
	 * @return
	 */
	protected String checkFormat(String name, Double doubleValue, ValidationFormat format, boolean isNullable) {
		String str = "";
		if (doubleValue != null)
			str = String.valueOf(doubleValue);
		return checkFormat(name, str, format, isNullable);
	}

	/**
	 * The that an integer is the proper format, and is in the correct range
	 * 
	 * @param name
	 * @param value
	 * @param lower
	 * @param upper
	 * @param isNullable
	 * @return
	 */
	protected String checkInt(String name, String value, int lower, int upper, boolean isNullable) {
		if (isNullable && (value == null || "".equals(value)))
			return "";
		try {
			int intValue = Integer.valueOf(value);
			if (lower <= intValue && intValue <= upper)
				return "";
		} catch (NumberFormatException e) {
			// just fall through to returning the error message
		}

		return name + " must be an integer in [" + lower + "," + upper + "]";
	}

	/**
	 * Check that a double is in the proper format and is in the correct range
	 * 
	 * @param name
	 * @param value
	 * @param lower
	 * @param upper
	 * @return
	 */
	protected String checkDouble(String name, String value, double lower, double upper) {
		try {
			double doubleValue = Double.valueOf(value);
			if (lower <= doubleValue && doubleValue < upper)
				return "";
		} catch (NumberFormatException e) {
			// just fall through to returning the error message
		}
		return name + " must be a decimal in [" + lower + "," + upper + ")";
	}
}
