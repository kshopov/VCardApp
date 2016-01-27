package adapters;

import java.util.List;

import model.User;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qrandroidapp.R;
import com.example.qrandroidapp.UserDetailsActivity;

public class UsersListAdapter extends BaseAdapter {
	
	private List<User> data = null;
	private Context context = null;
	private static LayoutInflater inflater = null;
	private User tempUser = null;
	
	public UsersListAdapter(Context context, List<User> data) {
		this.data = data;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static class ViewHolder {
		public ImageView qrImage;
		public TextView firstName;
		public TextView lastName;
		public TextView phone;
		public TextView position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder viewHolder;
		
		if (convertView == null) {
			vi = inflater.inflate(R.layout.user_listview, null);
			
			viewHolder = new ViewHolder();
			viewHolder.qrImage = (ImageView) vi.findViewById(R.drawable.qr_image);
			viewHolder.firstName = (TextView) vi.findViewById(R.id.first_name);
			viewHolder.lastName = (TextView) vi.findViewById(R.id.last_name);
			viewHolder.phone = (TextView) vi.findViewById(R.id.phone);
			viewHolder.position = (TextView) vi.findViewById(R.id.position);
			
			vi.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) vi.getTag();
		}
		
		if (data.size() <= 0) {
			viewHolder.firstName.setText(context.getText(R.string.no_organisations));
		} else {
			tempUser = (User) data.get(position);
			viewHolder.firstName.setText(tempUser.getFirstName());
			viewHolder.lastName.setText(tempUser.getLastName());
			viewHolder.phone.setText(tempUser.getPersonalPhone());
			viewHolder.position.setText(tempUser.getPostion());
			vi.setOnClickListener(new UserClickListener(tempUser));
		}
		
		return vi;
	}
	
	private class UserClickListener implements OnClickListener {
		
		private User user = null;
		
		public UserClickListener(User user) {
			this.user = user;
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent(context, UserDetailsActivity.class);
			Bundle b = new Bundle();
			b.putParcelable("user", user);
			i.putExtras(b);
			context.startActivity(i);
		}
		
	}

}
