package com.example.qrandroidapp;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.CreateQRActivityAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OptionsQRActivity extends Activity {
	
	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	
	ArrayList<HashMap<String, String>> data = null;
	
	private ListView listView = null;
	private CreateQRActivityAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		
		data = new ArrayList<HashMap<String,String>>();
		
		createData();
		
		listView = (ListView)findViewById(R.id.list);
		
		adapter = new CreateQRActivityAdapter(this, data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener () {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				switch (position) {
				case 0:
					Intent intentOrganisation = new Intent(OptionsQRActivity.this, CreateOrganisationQRActivity.class);
					startActivity(intentOrganisation);
					break;
				case 1:
					Intent intentUser = new Intent(OptionsQRActivity.this, CreateUserActivity.class);
					startActivity(intentUser);
					break;
				default:
					break;
				}
			}
		});
	}
	
	private ArrayList<HashMap<String, String>> createData() {
		final String[] titles = getResources().getStringArray(R.array.qr_options_titles);
		final String[] descriptions = getResources().getStringArray(R.array.qr_options_descriptions);
		
		for(int i = 0; i < titles.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(OptionsQRActivity.KEY_TITLE, titles[i]);
			map.put(OptionsQRActivity.KEY_DESCRIPTION, descriptions[i]);
			data.add(map);
		}
		return data;
	}
}
