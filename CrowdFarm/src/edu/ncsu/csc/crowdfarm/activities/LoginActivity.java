package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.RegistrationBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText usernameField;
	private EditText passwordField;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		usernameField = (EditText)findViewById(R.id.usernameField);
		passwordField = (EditText)findViewById(R.id.passwordField);
	}
	
	public void handleLogin(View v) {		
		Editable username = usernameField.getText();
		String usernameString = username.toString();
		Editable password = passwordField.getText();
		String passwordString = password.toString();
		
		CrowdFarmRest cfr = new CrowdFarmRest();
		try {
			RegistrationBean returnBean = cfr.login(usernameString, passwordString);
			if(returnBean == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			String errorMessage = "An error has occurred.  Ensure you have a network connections and try again.";
			Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
		}
		
		SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString("loggedInEmail", usernameString);
		prefsEditor.commit();
		
		Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
		LoginActivity.this.startActivity(loginIntent);
	}
	
	public void handleRegister(View v) {
		Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
		LoginActivity.this.startActivity(registerIntent);
		
	}
}
