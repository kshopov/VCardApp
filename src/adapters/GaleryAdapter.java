package adapters;

import java.util.EnumMap;
import java.util.Map;

import model.Organisation;
import model.User;
import utils.CreateVCard;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GaleryAdapter extends BaseAdapter {

	private Context context;
	private Organisation organisation = null;
	private User user = null;

	private Bitmap[] images = new Bitmap[2];

	public GaleryAdapter(Context context, Organisation organisation, User user) {
		this.context = context;
		this.organisation = organisation;
		this.user = user;
		
		
		if (organisation != null) {
			images[0] = createOrganisationQr(this.organisation);
			Canvas c = new Canvas(images[0]);
			c.drawColor(0, Mode.CLEAR);
		}
		
		if (user != null) {
			images[1] = createUserQr(user);
		}
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);
		i.setImageBitmap(images[position]);
		i.setLayoutParams(new Gallery.LayoutParams(300, 300));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		return i;
	}

	private Bitmap createOrganisationQr(Organisation organisation) {
		Bitmap qrImage = null;
		try {
			qrImage = encodeAsBitmap(
					CreateVCard.createOrganisationVCard(this.organisation),
					BarcodeFormat.QR_CODE, 300, 300);
		} catch (WriterException ex) {
			ex.printStackTrace();
		}

		return qrImage;
	}

	private Bitmap createUserQr(User user) {
		Bitmap qrImage = null;
		try {
			qrImage = encodeAsBitmap(
					CreateVCard.createUserVCard(this.user),
					BarcodeFormat.QR_CODE, 300, 300);
		} catch (WriterException ex) {
			ex.printStackTrace();
		}

		return qrImage;
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
