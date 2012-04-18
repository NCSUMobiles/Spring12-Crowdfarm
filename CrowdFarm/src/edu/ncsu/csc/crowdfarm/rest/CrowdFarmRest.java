package edu.ncsu.csc.crowdfarm.rest;

import java.util.List;

import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;

public class CrowdFarmRest {

	/**
	 * Adds a new produce
	 * 
	 * @param p
	 * @return the new produce id (hopefully the api returns this back)
	 * @throws Exception
	 */
	public int addNewProduce(ProduceBean p) throws Exception {
		
		return 0;
	}
	
	/**
	 * Logs a user in
	 * 
	 * @param username
	 * @param password
	 * @return registration bean for that user or null if it failed
	 * @throws Exception
	 */
	public RegistrationBean login(String username, String password) throws Exception {
		
		return null;
	}
	
	/**
	 * Adds a new user to the system
	 * 
	 * @return
	 * @throws Exception
	 */
	public int addRegistration(RegistrationBean r) throws Exception {
		
		return 0;
	}
	
	/**
	 * Loads all the produces that the past registration passed
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public List<ProduceBean> loadProduce(RegistrationBean r) throws Exception {
		
		return null;
	}
	
	
}