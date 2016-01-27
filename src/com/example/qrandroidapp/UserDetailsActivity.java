package com.example.qrandroidapp;

import java.util.EnumMap;
import java.util.Map;

import utils.CreateVCard;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import model.User;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailsActivity extends Activity {
	
	private ImageView qrImage = null;
	private TextView name = null;
	private TextView position = null;
	private TextView skype = null;
	private TextView facebook = null;
	private Button phoneButton = null;
	private Button businessPhoneButton = null;
	private Button emailButton = null;
	
	QRApp app = null;
	
	private User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details_activity);
		
		user = new User();
		
		name = (TextView) findViewById(R.id.name);
		position = (TextView) findViewById(R.id.position);
		skype = (TextView) findViewById(R.id.skype);
		facebook = (TextView) findViewById(R.id.facebook);
		
		phoneButton = (Button) findViewById(R.id.personal_phone_button);
		businessPhoneButton = (Button) findViewById(R.id.business_phone_button);
		emailButton = (Button) findViewById(R.id.email_button);
		
		qrImage = (ImageView) findViewById(R.id.user_qr_image);
		
		app = (QRApp) getApplication();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			user = extras.getParcelable("user");
		}
		
		String qrData = CreateVCard.createUserVCard(user);
		
		Log.i("QR DATA", "DATA: " + qrData);
		
		name.setText(user.getFirstName() + " " + user.getLastName());
		position.setText(user.getPostion());
		skype.setText(user.getSkype());
		facebook.setText(user.getFacebook());
		
		if (user.getPersonalPhone().equals("")) {
			phoneButton.setText(getText(R.string.phone_not_available));
		} else {
			phoneButton.setText(user.getPersonalPhone());
		}
		
		if (user.getEmail().equals("")) {
			emailButton.setText(getText(R.string.email_not_available));
		} else {
			emailButton.setText(user.getEmail());
		}
		
		if (user.getBusinessPhone().equals("")) {
			businessPhoneButton.setText(getText(R.string.phone_not_available));
		} else {
			businessPhoneButton.setText(user.getBusinessPhone());
		}
		
		Bitmap bitmap = null;
		try {
			bitmap = encodeAsBitmap(qrData, BarcodeFormat.QR_CODE, 300, 300);
			qrImage.setImageBitmap(bitmap);
		} catch (WriterException e) {
	        e.printStackTrace();
	    }
	}
	
	public void callPhone(View v) {
		if (user.getPersonalPhone().equals("")) {
			Toast.makeText(this, getText(R.string.phone_not_available), Toast.LENGTH_SHORT).show();
			return;
		}
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + user.getPersonalPhone()));
		startActivity(callIntent);
	}
	
	public void callBusinessPhone(View v) {
		if (user.getPersonalPhone().equals("")) {
			Toast.makeText(this, getText(R.string.phone_not_available), Toast.LENGTH_SHORT).show();
			return;
		}
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + user.getBusinessPhone()));
		startActivity(callIntent);
	}
	
	public void sendEmail(View v) {
		if (user.getEmail().equals("")) {
			Toast.makeText(this, getText(R.string.email_not_available), Toast.LENGTH_SHORT).show();
			return;
		}
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
		startActivity(emailIntent);
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
