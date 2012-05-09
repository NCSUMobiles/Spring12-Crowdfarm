package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProduceActivity extends Activity {
	
	private EditText produceType;
	private EditText amount;
	private EditText pickDate;
	private EditText shelfLife;
	private EditText cost;
	private Spinner amountSpinner;
	private Spinner shelfLifeSpinner;
	private ProduceBean currentBean;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editproduce);
        
        //Get the current user id
        
        //Get references to the EditText objects in the UI
        produceType = (EditText) findViewById(R.id.produceInput);
        amount = (EditText) findViewById(R.id.amountInput);
        pickDate = (EditText) findViewById(R.id.dateInput);
        shelfLife = (EditText) findViewById(R.id.shelfInput);
        cost = (EditText) findViewById(R.id.costInput);
        
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.quantityTypes, android.R.layout.simple_spinner_item );
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner = (Spinner)findViewById(R.id.quantitySpinner);
        amountSpinner.setAdapter(quantityAdapter);
        
        ArrayAdapter<CharSequence> shelfAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shelfLifeTypes, android.R.layout.simple_spinner_item );
        shelfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shelfLifeSpinner = (Spinner)findViewById(R.id.shelfSpinner);
        shelfLifeSpinner.setAdapter(shelfAdapter);
        
        Bundle extra = getIntent().getExtras();
        String itemString = extra.getString("item");
        Toast.makeText(getApplicationContext(), "You selected: " + itemString, Toast.LENGTH_SHORT).show(); 
        
	}
	
	public void delete(View v) {
		
	}
	
	public void submit(View v) {
		
	}

}
