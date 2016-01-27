package com.example.qrandroidapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends ListActivity {
	
	private String[] options = {
			"Organisations",
			"Users"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		switch (position) {
		case 0:
			Intent viewOrganisationsActivity = new Intent(this, ViewOrganisationsActivity.class);
			startActivity(viewOrganisationsActivity);
			break;
		case 1:
			Intent viewUsersActivity = new Intent(this, ViewUsersActivity.class);
			startActivity(viewUsersActivity);
			break;
		default:
			break;
		}
	}

}
