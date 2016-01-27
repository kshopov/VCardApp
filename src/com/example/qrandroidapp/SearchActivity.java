package com.example.qrandroidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchActivity extends Activity {
	
	private static String URL = "http://parite.bg/mobile_branch_search.php";

	private Spinner branchesSpinner = null;
	private Spinner countriesSpinner = null;
	private ProgressDialog pd = null;
	
	private long branchId;
	private long countryId;
	private List<String> countries = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);

		branchesSpinner = (Spinner) findViewById(R.id.branches_spinner);
		ArrayAdapter<CharSequence> branchesAdapter = ArrayAdapter
				.createFromResource(this, R.array.branches,
						android.R.layout.simple_spinner_item);
		branchesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		branchesSpinner.setAdapter(branchesAdapter);
		branchesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						branchId = id + 1;
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
		});
		
		Resources res = getResources();
		String[] tempCountries = res.getStringArray(R.array.countries);
		createSpinnedData(tempCountries);
		
		countriesSpinner = (Spinner) findViewById(R.id.countries_spinner);
		ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
		countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countriesSpinner.setAdapter(countriesAdapter);
		countriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				countryId = id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	public void search(View v) {
		pd = ProgressDialog.show(this, getText(R.string.sending_data), "");
		SendData sendData = new SendData();
		sendData.execute(URL);
	}
	
	private void createSpinnedData(String[] tempCountries) {
		countries = new ArrayList<String>();
		countries.add("All");
		for (int i = 0; i < tempCountries.length; i++) {
			countries.add(tempCountries[i]);
		}
	}
	
	private class SendData extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			JSONArray result = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("branchId", branchId);
				jsonObject.put("countryId", countryId);
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			result = httpClient(jsonObject);
			
			return result;
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			pd.hide();
			if (result == null) {
				Toast.makeText(SearchActivity.this, "No results", Toast.LENGTH_LONG).show();
			} else {
				Intent resultsIntent = new Intent(SearchActivity.this, SearchResultsActivity.class);
				resultsIntent.putExtra("results", result.toString());
				startActivity(resultsIntent);
			}
		}
		
		private JSONArray httpClient(JSONObject jsonObjSend) {
			JSONArray result = null;
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPostRequest = new HttpPost(URL);
				String resultString;
				StringEntity se;
				se = new StringEntity(jsonObjSend.toString(), "UTF-8");
	
				httpPostRequest.setEntity(se);
				httpPostRequest.setHeader("Accept", "application/json");
				httpPostRequest.setHeader("Content-type", "application/json");
				httpPostRequest.setHeader("Accept-Encoding", "gzip"); 
				
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpPostRequest);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					resultString = convertStreamToString(instream);
					instream.close();
					result = new JSONArray(resultString);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		private String convertStreamToString(InputStream is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}
}
