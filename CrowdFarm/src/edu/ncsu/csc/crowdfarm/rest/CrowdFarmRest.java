package edu.ncsu.csc.crowdfarm.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;

public class CrowdFarmRest {
	private String baseUrl = "http://h.55.lt:8000/api/"; 
	private Gson gson = new Gson();
	
	/**
	 * Class to hold registration json data returned by the api
	 *   
	 * @author ryan
	 */
	class RegistrationGson{
		@SerializedName("fname")
		public String fname;
		
		@SerializedName("mname")
		public String mname;
		
		@SerializedName("lname")
		public String lname;
		
		@SerializedName("unique_id")
		public String unique_id;
		
		public RegistrationGsonProperties properties;
		
		class RegistrationGsonProperties{
			@SerializedName("email")
			public String email;
			
			@SerializedName("phone")
			public String phone;
			
			@SerializedName("password")
			public String password;
			
			@SerializedName("location")
			public String location;
			
			@SerializedName("description")
			public String description;
		}
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
		String url = baseUrl + "blah"; //todo: use the correct url
		
		try{
			DefaultHttpClient client = new DefaultHttpClient(); 
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(httpPost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			InputStream responseStream = responseEntity.getContent();
			
			Reader reader = new InputStreamReader(responseStream);
			RegistrationGson registrationResponse = gson.fromJson(reader, RegistrationGson.class);
			
			RegistrationBean reg = new RegistrationBean();
			reg.setUniqueId(registrationResponse.unique_id);
			reg.setFirstName(registrationResponse.fname);
			reg.setMiddleInitial(registrationResponse.mname);
			reg.setLastName(registrationResponse.lname);
			reg.setEmail(registrationResponse.properties.email);
			reg.setPassword(registrationResponse.properties.password);
			reg.setPhone(registrationResponse.properties.phone);
			reg.setLocation(registrationResponse.properties.location);
			reg.setDescription(registrationResponse.properties.description);
			
			return reg;
		}
		catch(ClientProtocolException e){
			
		}
		catch(IOException e){
			
		}
		return null;
	}
	
	/**
	 * Adds a new user to the system
	 * 
	 * @return the new unique_id for the user
	 * @throws Exception
	 */
	public String addRegistration(RegistrationBean reg) throws Exception {
		String url = baseUrl + "actor/";
		
		String uniqueId = UUID.randomUUID().toString();
		reg.setUniqueId(uniqueId);
		
		DefaultHttpClient client = new DefaultHttpClient(); 
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response;
		
		try{
			RegistrationGson registrationGson = new RegistrationGson();
			registrationGson.unique_id = reg.getUniqueId();
			registrationGson.fname = reg.getFirstName();
			registrationGson.mname = reg.getMiddleInitial();
			registrationGson.lname = reg.getLastName();
			registrationGson.properties.email = reg.getEmail();
			registrationGson.properties.password = reg.getPassword();
			registrationGson.properties.phone = reg.getPhone();
			registrationGson.properties.location = reg.getLocation();
			registrationGson.properties.description = reg.getDescription();
			
			httpPost.setEntity(new ByteArrayEntity(gson.toJson(registrationGson).getBytes("UTF8")));
			response = client.execute(httpPost);
			
			//checks if it was successfully created
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != 201){
				throw new Exception("Failed to create user. Status code: " + statusCode);
			}
			
			return uniqueId;
		}
		catch(ClientProtocolException e){
			
		}
		catch(IOException e){
			
		}
		return null;
	}
	
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