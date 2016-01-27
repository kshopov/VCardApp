package com.example.qrandroidapp;

import utils.Validator;
import model.Organisation;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateOrganisationQRActivity extends Activity implements LocationListener {
	
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
	
	private QRApp app = null;
	
	private Organisation organisation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organisation_activity);
		
		organisationName = (EditText) findViewById(R.id.organisation_name);
		organisationEmail = (EditText) findViewById(R.id.organisation_email);
		organisationAddress = (EditText) findViewById(R.id.organisation_address);
		organisationWeb = (EditText) findViewById(R.id.organisation_web_address);
		organisationPhone = (EditText) findViewById(R.id.organisation_phone);
		organisationMobile = (EditText) findViewById(R.id.organisation_mobile);
		organisationFax = (EditText) findViewById(R.id.organisation_fax);
		organisationCity = (EditText) findViewById(R.id.organisation_city);
		
		organisation = new Organisation();
		app = (QRApp) getApplication();
		
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
		
		branchesSpinner = (Spinner) findViewById(R.id.branches_spinner);
		ArrayAdapter<CharSequence> branchesAdapter = 
				ArrayAdapter.createFromResource(this, R.array.branches, android.R.layout.simple_spinner_item);
		branchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		branchesSpinner.setAdapter(branchesAdapter);
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

	@Override
	public void onLocationChanged(Location location) {
		longtitudeValue = location.getLongitude();
		latitudeValue = location.getLatitude();
		gpsLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
		gpsLongtitude.setText("Longtitude: " + String.valueOf(location.getLongitude()));
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	public void createQR(View v) {
		organisation.setCountry(countriesSpinner.getSelectedItem().toString());
		organisation.setBranch(branchesSpinner.getSelectedItem().toString());
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
		
		if (validate() & (app.getDataManager().saveOrganisation(organisation) > 0) ) {
			Toast.makeText(this, "Data successfully saved", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean validate() {
		if(!Validator.isValidEmail(organisationEmail)) {
			organisationEmail.setError(getString(R.string.not_valid_email));
			organisationEmail.requestFocus();
			return false;
		}
		return true;
	}
}
