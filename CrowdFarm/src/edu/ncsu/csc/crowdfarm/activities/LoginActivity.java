package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
	
	public void handleLogin(View view) {
		final EditText usernameField = (EditText)findViewById(R.id.usernameField);
		final EditText passwordField = (EditText)findViewById(R.id.passwordField);
		
		Editable username = usernameField.getText();
		String usernameString = username.toString();
		Editable password = passwordField.getText();
		String passwordString = password.toString();
		/*
		 * hash password if needed
		 * call login method passing username and password
		 * if login is successful do the following, otherwise toast an error message
		 */
		SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();
		prefsEditor.putString("loggedInEmail", usernameString);
		prefsEditor.commit();
		
		Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
		LoginActivity.this.startActivity(loginIntent);
	}
	
	public void handleRegister(View view) {
		Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
		LoginActivity.this.startActivity(registerIntent);
		
	}
}
