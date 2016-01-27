package com.example.qrandroidapp;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import model.Organisation;
import model.User;
import utils.ParseVCardString;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanQRResultsActivity extends Activity {

	private String result;

	private TextView organisationDetails = null;
	private ImageView qrImage = null;
	private Button locationButton = null;
	
	private Organisation organisation = null;
	private User user = null;
	
	private QRApp app = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_scan);
		Bundle extras = getIntent().getExtras();
		
		locationButton = (Button) findViewById(R.id.location);
		
		app = (QRApp) getApplication();
		
		if (extras != null) {
			result = extras.getString("results");
		}
		if (result.contains("ORG")) {
			organisation = new Organisation();
			organisation = ParseVCardString.parseOrganisation(result);
			qrImage = (ImageView) findViewById(R.id.organisation_qr_img);
			organisationDetails = (TextView) findViewById(R.id.organisation_name);
			organisationDetails.setText(getText(R.string.organisation_name) + ": " + organisation.getName() + "\n"
				+ getText(R.string.organisation_email) + ": " + organisation.getEmail() + "\n"
				+ getText(R.string.organisation_address) + ": " + organisation.getAddress() + "\n"
				+ getText(R.string.organisation_web_address) + ": " + organisation.getWebPage() + "\n"
				+ getText(R.string.organisation_phone) + ": " + organisation.getPhone() + "\n"
				+ getText(R.string.organisation_mobile) + ": " + organisation.getMobile() + "\n"
				+ getText(R.string.organisation_fax) + ": " + organisation.getFax() + "\n"
				+ getText(R.string.organisation_country) + ": " + organisation.getCountry() + "\n"
				+ "Branch: " + organisation.getBranch() + "\n"
				+ "Longtitude: " + organisation.getGpsLongtitude() + ", Latitude: " + organisation.getGpsLatitude());
		} else {
			user = new User();
			locationButton.setVisibility(View.INVISIBLE);
			user = ParseVCardString.parseUser(result);
			qrImage = (ImageView) findViewById(R.id.organisation_qr_img);
			organisationDetails = (TextView) findViewById(R.id.organisation_name);
			organisationDetails.setText("Name: " + user.getFirstName() + " " + user.getLastName() + "\n"
					+ getText(R.string.position_hint) + ": " + user.getPostion() + "\n"
					+ getText(R.string.business_phone_hint) + ": " + user.getBusinessPhone() + "\n"
					+ getText(R.string.personal_phone_hint) + ": " + user.getPersonalPhone() + "\n"
					+ getText(R.string.email_hint) + " :" + user.getEmail() + "\n"
					+ getText(R.string.skype_hint) + " :" +user.getSkype() + "\n"
					+ getText(R.string.facebook_hint) + ": " + user.getFacebook());
		}
		
		Bitmap bitmap = null;
		try {
			bitmap = encodeAsBitmap(result, BarcodeFormat.QR_CODE, 300, 300);
			qrImage.setImageBitmap(bitmap);
		} catch (WriterException e) {
	        e.printStackTrace();
	    }
		
	}
	
	public void createQR(View v) {
		if (organisation != null) {
			if (app.getDataManager().saveOrganisation(organisation) > 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle(getText(R.string.successful_qr_save));
				dialog.setNeutralButton(getString(R.string.main), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent mainIntent = new Intent(ScanQRResultsActivity.this, MainActivity.class);
						startActivity(mainIntent);
					}
				});
				dialog.show();
			}
		} else if (user != null) {
			if (app.getDataManager().saveUser(user) > 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle(getText(R.string.successful_qr_save));
				dialog.setNeutralButton(getString(R.string.main), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent mainIntent = new Intent(ScanQRResultsActivity.this, MainActivity.class);
						startActivity(mainIntent);
					}
				});
				dialog.show();
			}
		}
		
	}
	
	public void findLocation(View v) {
		String uri = String.format(Locale.ENGLISH, "geo:0,0?q="+organisation.getGpsLatitude()+","+organisation.getGpsLongtitude()+" (" + organisation.getAddress() + ")");
		Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		startActivity(googleMapIntent);
	}

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width,
			int img_height) throws WriterException {
		String contentsToEncode = contents;
		if (contentsToEncode == null) {
			return null;
		}
		Map<EncodeHintType, Object> hints = null;
		String encoding = guessAppropriateEncoding(contentsToEncode);
		if (encoding != null) {
			hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, encoding);
		}
		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix result;
		try {
			result = writer.encode(contentsToEncode, format, img_width,
					img_height, hints);
		} catch (IllegalArgumentException iae) {
			// Unsupported format
			return null;
		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	private static String guessAppropriateEncoding(CharSequence contents) {
		// Very crude at the moment
		for (int i = 0; i < contents.length(); i++) {
			if (contents.charAt(i) > 0xFF) {
				return "UTF-8";
			}
		}
		return null;
	}
}
