package adapters;

import com.example.qrandroidapp.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MenuAdapter extends BaseAdapter {
	
	private Context context = null;
	private Integer[] imageThumbs = {
			R.drawable.profile, R.drawable.inbox,
			R.drawable.create_qr, R.drawable.dc_history_icon,
			R.drawable.settings, R.drawable.info
	};
	
	public MenuAdapter(Context context) {
		this.context = context;
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageThumbs[position]);
        return imageView;
    }

}
