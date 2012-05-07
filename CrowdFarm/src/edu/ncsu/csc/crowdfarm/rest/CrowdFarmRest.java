package edu.ncsu.csc.crowdfarm.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
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

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;

public class CrowdFarmRest {
	//private String baseUrl = "http://h.55.lt:8000/api/";
	// TODO fix this when the api is up
	private String baseUrl = "http://www4.ncsu.edu/~srgraham/crowdfarm/"; 
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
	
	class ProduceArrayGson {
		public List<ProduceGson> items;
		
	}
	
	class ProduceGson {
		
		@SerializedName("type")
		public String type;
		
		@SerializedName("amount")
		public String amount;
		
		@SerializedName("measure")
		public String measure;
		
		@SerializedName("shelfLife")
		public String shelfLife;
		
		@SerializedName("lifeUnit")
		public String lifeUnit;
		
		@SerializedName("cost")
		public String cost;
		
		@SerializedName("pickDate")
		public String pickDate;
		
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
		String url = baseUrl + "login.json"; //todo: use the correct url
		try{
			//Log.v("MyActivity", "11111");
			DefaultHttpClient client = new DefaultHttpClient(); 
			HttpPost httpPost = new HttpPost(url);
			//Log.v("asdf", url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", username));
			//Log.v("MyActivity", "2222");
			nameValuePairs.add(new BasicNameValuePair("password", password));
			//Log.v("MyActivity", "222-1");
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Log.v("MyActivity", "222-2");
			HttpResponse response = client.execute(httpPost);
			//Log.v("MyActivity", "3333");
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			InputStream responseStream = responseEntity.getContent();
			//Log.v("MyActivity", "4444");
			Reader reader = new InputStreamReader(responseStream);
			RegistrationGson registrationResponse = gson.fromJson(reader, RegistrationGson.class);
			//Log.v("MyActivity", "5555");
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
			//Log.v("MyActivity", "6666");
			return reg;
		}
		catch(ClientProtocolException e){
			//throw new Exception("protocol error");
		}
		catch(IOException e){
			//throw new Exception("io error");
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
	public void addNewProduce(Context c, String uniqueId, ProduceBean p) throws Exception {
		//Log.v("add", "11111");
		List<ProduceBean> ps = loadSavedProduceFromFS(c, uniqueId);
		//Log.v("add", "2222");
		ps.add(p);
		//Log.v("add", "3333");
		saveProduceToFS(c, uniqueId, ps);
		//Log.v("add", "444");
	}
	
	
	/**
	 * Loads all the produces that the past registration passed
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public List<ProduceBean> loadProduce(Context c, String uniqueId) throws Exception {
		return loadSavedProduceFromFS(c, uniqueId);
	}
	
	/**
	 * Loads the produce saved to the produceFile.txt cache
	 * Returns blank list if it doesn't exist
	 * @return
	 */
	private List<ProduceBean> loadSavedProduceFromFS(Context c, String uniqueId) throws Exception {
		//Log.v("load", "1111");
		String filename = uniqueId + "_produceFile.txt";
		//Log.v("load", "2222");
	    byte[] buffer = new byte[(int) new File(filename).length()];
	    //Log.v("load", "3333");
	    String contents = "";
	    try{
	    	FileInputStream f = c.openFileInput(filename);
			StringBuffer strBuf = new StringBuffer("");
			int ch;
			while((ch = f.read()) != -1){
				strBuf.append((char)ch);
			}
		    contents = new String(strBuf).toString();
	    }
	    catch(Exception e){
	    	contents = "";
	    }
	    //Log.v("load", "6666");
	    
	    if(contents == null || contents.equals("")) {
	    	contents = "{items:[]}";
	    	//Log.v("load", "q:"+contents);
	    }
	    //Log.v("load", "7777");
	    //Log.v("load", contents);
	    ProduceArrayGson produceArrayGson=null;
	    try{
	    	 produceArrayGson = gson.fromJson(contents, ProduceArrayGson.class);
	    }
	    catch(Exception e){
	    	Log.v("eee", e.toString());
	    }
	    //Log.v("load", "8888");
	    List<ProduceBean> out = new ArrayList<ProduceBean>();
	    //Log.v("load", "9999");
	    //Log.v("single",(produceArrayGson==null)?"null":"not null");
	    for(ProduceGson item : produceArrayGson.items) {
	    	ProduceBean i = new ProduceBean();
	    	i.setAmount(item.amount);
	    	i.setCost(item.cost);
			i.setLifeUnit(item.lifeUnit);
			i.setMeasure(item.measure);
			i.setPickDate(item.pickDate);
			i.setShelfLife(item.shelfLife);
			i.setType(item.type);
	    	
	    	out.add(i);
	    }
	    //Log.v("load", "11111000");
	    //throw new Error("finished loadign>>!>!");
	    return out;
	}
	
	private void saveProduceToFS(Context c, String uniqueId, List<ProduceBean> ps) throws Exception {
		String filename = uniqueId + "_produceFile.txt";
		//Log.v("save", "11111");
		String jsonStr = "{\"items\":"+gson.toJson(ps)+"}";
		//Log.v("save", "2222");
		FileOutputStream fout = c.openFileOutput(filename, Context.MODE_PRIVATE);
		//Log.v("save", "3333");
		//Log.v("savestr", jsonStr);
		new PrintStream(fout).println(jsonStr);
		//Log.v("save", "4444");
		fout.close();
		//Log.v("save", "5555");
	}
	
	/**
	 * edits the produce list by replacing an existing produce bean with an edited one
	 * 
	 * @param c = context object
	 * @param uniqueId = registration's uniqueId
	 * @param oldType = old type of produce
	 * @param oldPickDate = old pick date
	 * @param newP = produce bean we're replacing with
	 * @throws Exception
	 */
	private void editProduce(Context c, String uniqueId, String oldType, String oldPickDate, ProduceBean newP) throws Exception {
		List<ProduceBean> ps = loadSavedProduceFromFS(c, uniqueId);
		ProduceBean p = null;
		
		for(int a = 0; a < ps.size(); a+=1) {
			p = ps.get(a);
			if(p.getType().equals(oldType) && p.getPickDate().equals(oldPickDate)) {
				ps.set(a, newP);
				break;
			}
		}
		saveProduceToFS(c, uniqueId, ps);
	}
	
	/**
	 * deletes the producebean for a particular type and pick date
	 * 
	 * @param c = context object
	 * @param uniqueId = registration's uniqueId
	 * @param type = old type of produce
	 * @param pickDate = old pick date
	 * @throws Exception
	 */
	private void deleteProduce(Context c, String uniqueId, String type, String pickDate) throws Exception {
		List<ProduceBean> ps = loadSavedProduceFromFS(c, uniqueId);
		ProduceBean p = null;
		
		for(int a = 0; a < ps.size(); a+=1) {
			p = ps.get(a);
			if(p.getType().equals(type) && p.getPickDate().equals(pickDate)) {
				ps.remove(a);
				break;
			}
		}
		saveProduceToFS(c, uniqueId, ps);
	}
		
}