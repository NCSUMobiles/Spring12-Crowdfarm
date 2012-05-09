package edu.ncsu.csc.crowdfarm.activities;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProduceActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CrowdFarmRest cfr = new CrowdFarmRest();
		SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		String uid = prefs.getString("loggedInUid", null);
		List<String> produceStringList = null;
		
		try {
			produceStringList = formatProduceString(cfr.loadProduce(getApplicationContext(), uid));
		} catch(Exception e) {
			String errorMessage = "The produce file could not be found.";
			Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
		}
		
		LayoutInflater li = getLayoutInflater();
		
		ListView lv = getListView();
		ViewGroup header = (ViewGroup)li.inflate(R.layout.produceheader, lv, false);
		lv.addHeaderView(header, null, false);
		setListAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.produceitem, produceStringList));
		
	}
	
	private List<String> formatProduceString(List<ProduceBean> produceList) {
		List<String> returnList = new ArrayList<String>();
		
		for(ProduceBean bean: produceList) {
			returnList.add(bean.getType() + " : " + bean.getPickDate());
		}
		
		return returnList;
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position - 1);  
        String itemString = o.toString();
        
        Intent i = new Intent(ViewProduceActivity.this, EditProduceActivity.class);
        i.putExtra("item", itemString);
        ViewProduceActivity.this.startActivity(i);
        
	}

}
