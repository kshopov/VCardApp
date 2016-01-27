package com.example.qrandroidapp;

import com.example.qrandroidapp.R;

import adapters.MenuAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new MenuAdapter(this));
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            switch (position) {
				case 0:
					Intent intent = new Intent(MenuActivity.this, LoginRegisterActivity.class);
					startActivity(intent);
					break;
				case 1:
					Intent search = new Intent(MenuActivity.this, SearchActivity.class);
					startActivity(search);
					break;
				case 2:
					Intent options = new Intent(MenuActivity.this, OptionsQRActivity.class);
					startActivity(options);
					break;
				case 3:
					Intent historyActivityIntent = new Intent(MenuActivity.this, HistoryActivity.class);
					startActivity(historyActivityIntent);
					break;
				case 4:
					Intent settingsIntent = new Intent(MenuActivity.this, SettingsActivity.class);
					startActivity(settingsIntent);
					break;
				case 5:
					Toast.makeText(MenuActivity.this, "Info selected", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
	        }
	    	
		});
	}
	
}