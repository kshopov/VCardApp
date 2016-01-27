package adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.qrandroidapp.OptionsQRActivity;
import com.example.qrandroidapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateQRActivityAdapter extends BaseAdapter {
	
	private Context context = null;
	private LayoutInflater inflater = null;
	private ArrayList<HashMap<String, String>> data = null;

	private Integer[] imageThumbs = {
			R.drawable.qr_image, 
			R.drawable.qr_image
	};
	
	public CreateQRActivityAdapter(Context context, ArrayList<HashMap<String, String>> data) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
	}

	@Override
	public int getCount() {
		return imageThumbs.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null) {
			vi = inflater.inflate(R.layout.create_activity_custom_row, null);
		}
		
		HashMap<String, String> test = new HashMap<String, String>();
		test = data.get(position);
		
		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView description = (TextView) vi.findViewById(R.id.description);
		ImageView thumb = (ImageView) vi.findViewById(R.id.list_image);
		
		title.setText(test.get(OptionsQRActivity.KEY_TITLE));
		description.setText(test.get(OptionsQRActivity.KEY_DESCRIPTION));
		thumb.setImageResource(imageThumbs[0]);
		
		return vi;
	}

}
