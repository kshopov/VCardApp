package com.example.qrandroidapp;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import model.Organisation;
import utils.CreateVCard;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class OrganisationDetailsActivity extends Activity {
	
	private ImageView qrImage = null;
	private TextView organisationName = null;
	private TextView organisationAddress = null;
	private TextView organisationCountry = null;
	private TextView organisationBranch = null;
	private Button emailButton = null;
	private Button webPageButton = null;
	private Button phoneButton = null;
	private Button mobileButton = null;
	private Button saveButton = null;
	private Button findLocation = null;
	private LinearLayout organisationDetailsLinearLayout = null;
	
	private QRApp app = null;

	private Organisation organisation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.organisation_details_activity);
		
		qrImage = (ImageView) findViewById(R.id.organisation_qr_img);
		organisationName = (TextView) findViewById(R.id.organisation_name);
		organisationAddress = (TextView) findViewById(R.id.organisation_address);
		organisationCountry = (TextView) findViewById(R.id.country);
		organisationBranch = (TextView) findViewById(R.id.branch);
		organisationDetailsLinearLayout = (LinearLayout) findViewById(R.id.organisation_details_linearLayout);
		
		app = (QRApp) getApplication();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			organisation = extras.getParcelable("organisation");
		}
		
		String qrData = CreateVCard.createOrganisationVCard(organisation);
		
		if (organisation.getBranch() == null) {
			organisation.setBranch(getOrganisationBranch(organisation.getBranch_id()));
		}
		
		if (organisation.getCountry() == null) {
			organisation.setCountry(getOrganisationCountry(organisation.getCountry_id()));
		}
		
		organisationName.setText(organisation.getName().replace("\\", ""));
		organisationAddress.setText(organisation.getAddress().replace("\\", "") + " " + organisation.getCity());
		organisationCountry.setText(organisation.getCountry());
		organisationBranch.setText(organisation.getBranch());
		
		emailButton = new Button(this);
		emailButton.setText(organisation.getEmail());
		emailButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{organisation.getEmail()});
				startActivity(emailIntent);
			}
		});
		organisationDetailsLinearLayout.addView(emailButton);
		
		if (!organisation.getWebPage().equals("")) {
			webPageButton = new Button(this);
			webPageButton.setText(organisation.getWebPage());
			webPageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String url = organisation.getWebPage();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW);
					browserIntent.setData(Uri.parse(url));
					startActivity(browserIntent);
				}
			});
			organisationDetailsLinearLayout.addView(webPageButton);
		} 
		
		if (!organisation.getPhone().equals("")) {
			phoneButton = new Button(this);
			phoneButton.setText(organisation.getPhone());
			phoneButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + organisation.getPhone()));
					startActivity(callIntent);
				}
			});
			organisationDetailsLinearLayout.addView(phoneButton);
		}
		
		if (!organisation.getMobile().equals("")) {
			mobileButton = new Button(this);
			mobileButton.setText(organisation.getMobile());
			mobileButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + organisation.getMobile()));
					startActivity(callIntent);
				}
			});
			organisationDetailsLinearLayout.addView(mobileButton);
		}
		
		saveButton = new Button(this);
		saveButton.setText(getText(R.string.save));
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (app.getDataManager().saveOrganisation(organisation) > 0) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(OrganisationDetailsActivity.this);
					dialog.setTitle(getText(R.string.successful_qr_save));
					dialog.setNeutralButton(getString(R.string.main), new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent mainIntent = new Intent(OrganisationDetailsActivity.this, MainActivity.class);
							startActivity(mainIntent);
						}
					});
					dialog.show();
				}
			}
		});
		organisationDetailsLinearLayout.addView(saveButton);
		
		
		if (organisation.getGpsLongtitude() != 0.0 && organisation.getGpsLatitude() != 0.0) {
			findLocation = new Button(this);
			findLocation.setText(getText(R.string.find_location));
			findLocation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String uri = String.format(Locale.ENGLISH, 
							"geo:0,0?q="+organisation.getGpsLatitude()
							+","+organisation.getGpsLongtitude()
							+" (" + organisation.getAddress() + ")");
					Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
					startActivity(googleMapIntent);
				}
			});
			organisationDetailsLinearLayout.addView(findLocation);
		}
		
		Bitmap bitmap = null;
		try {
			bitmap = encodeAsBitmap(qrData, BarcodeFormat.QR_CODE, 300, 300);
			qrImage.setImageBitmap(bitmap);
		} catch (WriterException e) {
	        e.printStackTrace();
	    }
	}
	
	public void findLocation(View v) {
		
		
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
	
	private String getOrganisationBranch(int id) {
		String branch = null;
		Resources res = getResources();
		String[] branches = res.getStringArray(R.array.branches);
		
		for (int i = 0; i < branches.length; i++) {
			if (i + 1 == id) {
				branch = branches[i];
			}
		}
		return branch;
	}
	
	private String getOrganisationCountry(int id) {
		String country = null;
		Resources res = getResources();
		String countries[] = res.getStringArray(R.array.countries);
		
		for(int i = 0; i < countries.length; i++) {
			if (i + 1 == id) {
				country = countries[i];
			}
		}
		return country;
	}
	
}
