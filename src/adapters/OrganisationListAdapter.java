package adapters;

import java.util.List;

import model.Organisation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrandroidapp.OrganisationDetailsActivity;
import com.example.qrandroidapp.R;

public class OrganisationListAdapter extends BaseAdapter {
	
	private List<Organisation> data = null;
	private Context context = null;
	private static LayoutInflater inflater = null;
	private Organisation tempValues = null;
	
	public OrganisationListAdapter(Context context, List<Organisation> data) {
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
		public TextView organisationName;
		public TextView organisationAddress;
		public TextView phoneNumber;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder viewHolder;
		
		if (convertView == null) {
			vi = inflater.inflate(R.layout.organisations_listview, null);
			
			viewHolder = new ViewHolder();
			viewHolder.qrImage = (ImageView) vi.findViewById(R.drawable.qr_image);
			viewHolder.organisationName = (TextView) vi.findViewById(R.id.organisation_name);
			viewHolder.organisationAddress = (TextView) vi.findViewById(R.id.organisation_address);
			viewHolder.phoneNumber = (TextView) vi.findViewById(R.id.phone);
			
			vi.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) vi.getTag();
		}
		
		if (data.size() <= 0) {
			viewHolder.organisationName.setText(context.getText(R.string.no_organisations));
		} else {
			tempValues = (Organisation) data.get(position);
			viewHolder.organisationName.setText(tempValues.getName().replace("\\", ""));
			viewHolder.organisationAddress.setText(tempValues.getAddress().replace("\\", ""));
			viewHolder.phoneNumber.setText(tempValues.getPhone());
			viewHolder.qrImage = (ImageView) vi.findViewById(R.drawable.qr_image);
			vi.setOnClickListener(new OrganisationOnClickListener(tempValues));
		}
		
		return vi;
	}
	
	private class OrganisationOnClickListener implements OnClickListener {
		
		Organisation organisation = null;
		
		public OrganisationOnClickListener(Organisation organisation) {
			this.organisation = organisation;
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent(context, OrganisationDetailsActivity.class);
			Bundle b = new Bundle();
			b.putParcelable("organisation", organisation);
			i.putExtras(b);
			context.startActivity(i);
		}
		
	}
	
}
