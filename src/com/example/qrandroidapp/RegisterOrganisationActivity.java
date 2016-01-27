package com.example.qrandroidapp;

import http.HttpClient;
import model.Organisation;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Validator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterOrganisationActivity extends Activity implements LocationListener {
	
	private String username = null;
	private String password = null;
	private String email = null;
	
	private Spinner countriesSpinner = null;
	private Spinner branchesSpinner = null;
	private EditText organisationName = null;
	private EditText organisationEmail = null;
	private EditText organisationAddress = null;
	private EditText organisationWeb = null;
	private EditText organisationPhone = null;
	private EditText organisationMobile = null;
	private EditText organisationFax = null;
	private EditText organisationCity = null;
	private TextView gpsLatitude = null;
	private TextView gpsLongtitude = null;
	
	private double longtitudeValue = 0.0;
	private double latitudeValue = 0.0;
	
	private LocationManager locationManager = null;
	private String provider = null;
	
	private Organisation organisation = null;
	
	private ProgressDialog dl = null;
	
	private static final String URL = "http://parite.bg/mobile_registration_organisation.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_organisation_activity);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.username = extras.getString("username");
			this.password = extras.getString("password");
			this.email = extras.getString("email");
		}
		
		organisationName = (EditText) findViewById(R.id.organisation_name);
		organisationEmail = (EditText) findViewById(R.id.organisation_email);
		organisationAddress = (EditText) findViewById(R.id.organisation_address);
		organisationWeb = (EditText) findViewById(R.id.organisation_web_address);
		organisationPhone = (EditText) findViewById(R.id.organisation_phone);
		organisationMobile = (EditText) findViewById(R.id.organisation_mobile);
		organisationFax = (EditText) findViewById(R.id.organisation_fax);
		organisationCity = (EditText) findViewById(R.id.organisation_city);
		
		organisation = new Organisation();
		
		gpsLongtitude = (TextView) findViewById(R.id.gps_longtitude);
		gpsLatitude = (TextView) findViewById(R.id.gps_latitude);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			gpsLatitude.setText(getText(R.string.location_not_available));
			gpsLongtitude.setText(getText(R.string.location_not_available));
		}
		
		countriesSpinner = (Spinner) findViewById(R.id.countries_spinner);
		ArrayAdapter<CharSequence> countriesAdapter = 
				ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
		countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countriesSpinner.setAdapter(countriesAdapter);
		
		countriesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				organisation.setCountry_id(position + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		branchesSpinner = (Spinner) findViewById(R.id.branches_spinner);
		ArrayAdapter<CharSequence> branchesAdapter = 
				ArrayAdapter.createFromResource(this, R.array.branches, android.R.layout.simple_spinner_item);
		branchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		branchesSpinner.setAdapter(branchesAdapter);
		
		branchesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				organisation.setBranch_id(position + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}
	
	public void registerOrganisation(View v) {
		if (collectData()) {
			dl = ProgressDialog.show(this, "Sendig data", "");
			SendData sendData = new SendData();
			sendData.execute(URL);
		}
	}
	
	private class SendData extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = new JSONObject();
			JSONObject results = null;
			String url = params[0];
			try {
				jsonObject.put("username", username);
				jsonObject.put("email", email);
				jsonObject.put("password", password);
				jsonObject.put("companyEmail", organisation.getEmail());
				jsonObject.put("companyName", organisation.getName());
				jsonObject.put("companyAddress", organisation.getAddress());
				jsonObject.put("webPage", organisation.getWebPage());
				jsonObject.put("companyPhone", organisation.getPhone());
				jsonObject.put("companyMobile", organisation.getMobile());
				jsonObject.put("companyFax", organisation.getFax());
				jsonObject.put("countryId", organisation.getCountry_id());
				jsonObject.put("city", organisation.getCity());
				jsonObject.put("branchId", organisation.getBranch_id());
				jsonObject.put("gpsLongtitude", organisation.getGpsLongtitude());
				jsonObject.put("gpsLatitude", organisation.getGpsLatitude());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			results = HttpClient.SendHttpPost(url, jsonObject);
			
			return results;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(RegisterOrganisationActivity.this, "Server error", Toast.LENGTH_SHORT).show();
			} else {
				try {
					switch (result.getInt("serverCode")) {
						case 1:
							break;
						case 2:
							AlertDialog.Builder alert = new AlertDialog.Builder(RegisterOrganisationActivity.this);
							alert.setTitle("Successful registration");
							alert.setMessage("Your Registration was successful");
							alert.setPositiveButton(getString(R.string.login),
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent loginIntent = new Intent(RegisterOrganisationActivity.this, LoginActivity.class);
											startActivity(loginIntent);
										}
									});
							alert.setNeutralButton(getString(R.string.main), new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent loginIntent = new Intent(RegisterOrganisationActivity.this, MainActivity.class);
											startActivity(loginIntent);
										}
									});
							alert.show();
						break;
						default:
							//default
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dl.hide();
		}
		
	}

	@Override
	public void onLocationChanged(Location location) {
		longtitudeValue = location.getLongitude();
		latitudeValue = location.getLatitude();
		gpsLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
		gpsLongtitude.setText("Longtitude: " + String.valueOf(location.getLongitude()));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean collectData() {
		if(!Validator.isValidEmail(organisationEmail)) {
			organisationEmail.setError(getString(R.string.not_valid_email));
			organisationEmail.requestFocus();
			return false;
		}
		organisation.setName(organisationName.getText().toString());
		organisation.setEmail(organisationEmail.getText().toString());
		organisation.setAddress(organisationAddress.getText().toString());
		organisation.setWebPage(organisationWeb.getText().toString());
		organisation.setPhone(organisationPhone.getText().toString());
		organisation.setMobile(organisationMobile.getText().toString());
		organisation.setFax(organisationFax.getText().toString());
		organisation.setCity(organisationCity.getText().toString());
		organisation.setGpsLongtitude(longtitudeValue);
		organisation.setGpsLatitude(latitudeValue);
		
		return true;
	}

}
