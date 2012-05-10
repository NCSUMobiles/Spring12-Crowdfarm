package edu.ncsu.csc.crowdfarm.activities;

import java.util.List;

import javax.xml.datatype.Duration;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import edu.ncsu.csc.crowdfarm.validate.ProduceValidator;
import edu.ncsu.csc.crowdfarm.validate.RegistrationValidator;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class RegisterActivity extends Activity implements AdapterView.OnItemSelectedListener{
	
	private Account[] accounts;
	private EditText emailInput;
	private EditText passwordInput;
	private EditText firstNameInput;
	private EditText miInput;
	private EditText lastNameInput;
	private EditText locationInput;
	private EditText phoneInput;
	private EditText descriptionInput;
	private ViewFlipper regFlipper;
	private LocationManager lm;
	private LocationListener ll;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		emailInput = (EditText)findViewById(R.id.emailInput);
		passwordInput = (EditText)findViewById(R.id.passwordInput);
		firstNameInput = (EditText)findViewById(R.id.firstNameInput);
		miInput = (EditText)findViewById(R.id.miInput);
		lastNameInput = (EditText)findViewById(R.id.lastNameInput);
		locationInput = (EditText)findViewById(R.id.locationInput);
		phoneInput = (EditText)findViewById(R.id.phoneInput);
		descriptionInput = (EditText)findViewById(R.id.descriptionInput);
		regFlipper = (ViewFlipper)findViewById(R.id.registerFlipper);
		
		this.accounts = AccountManager.get(getApplicationContext()).getAccountsByType("com.google");
		ArrayAdapter<CharSequence> accountAdapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item );
		accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for(Account account : this.accounts) {
			accountAdapter.add(account.name);
		}
		Spinner accountSpinner = (Spinner)findViewById(R.id.accountChooser);
		accountSpinner.setAdapter(accountAdapter);
		accountSpinner.setOnItemSelectedListener(this);
		
	}
	
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		emailInput.setText(accounts[position].name);
	}
	
	public void onNothingSelected(AdapterView<?> parent) {
		emailInput.setText("");
	  }
	
	public void next(View v) {
		regFlipper.showNext();
	}
	
	public void back(View v) {
		regFlipper.showPrevious();
	}
	
	public void backToLogin(View view) {
		Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
		RegisterActivity.this.startActivity(loginIntent);
	}
	
	public void getLocation(View v) {
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		ll = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      RegisterActivity.this.locationInput.setText(location.getLatitude() + ", " + location.getLongitude());
		      lm.removeUpdates(ll);
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };
		  
		  lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 600000, 0, ll);
	}
	
	public void submit(View v) {
		
		RegistrationBean regBean = new RegistrationBean();
		
		regBean.setEmail(emailInput.getText().toString());
		regBean.setPassword(passwordInput.getText().toString());
		regBean.setFirstName(firstNameInput.getText().toString());
		regBean.setMiddleInitial(miInput.getText().toString());
		regBean.setLastName(lastNameInput.getText().toString());
		regBean.setLocation(locationInput.getText().toString());
		regBean.setPhone(phoneInput.getText().toString());
		regBean.setDescription(descriptionInput.getText().toString());
		
		RegistrationValidator rv = new RegistrationValidator();
		List<String> vList = rv.validate(regBean);
		if(vList.size() != 0) {
			String vString = "";
			for(int i = 0; i < vList.size(); i++) {
				vString = vString + "\n" + vList.get(i);
			}
			Toast.makeText(getApplicationContext(), vString, Toast.LENGTH_LONG).show();
		} else {
			try {
				//regBean.validate();
				CrowdFarmRest cfr = new CrowdFarmRest();
				String regReturn = cfr.addRegistration(regBean);
				if(regReturn == null) {
					throw new Exception();
				} else {
					String successMessage = "Registration successful.";
					Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();
				}
			} catch(Exception e) {
				String errorMessage = "An error has occurred.  Ensure you have a network connections and try again.";
				Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
			}
			
			Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
			RegisterActivity.this.startActivity(loginIntent);
		}
		
		
	}

}
