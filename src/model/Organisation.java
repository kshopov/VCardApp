package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Organisation implements Parcelable {

	private int id;
	private long accountId;
	private String name;
	private String email;
	private String address;
	private String webPage;
	private String phone;
	private String mobile;
	private String fax;
	private String branch;
	private String country;
	private int countryId;
	private String city;
	private int branchId;
	private double gpsLongtitude;
	private double gpsLatitude;

	public Organisation() {
		this.id = 0;
		this.name = "";
		this.email = "";
		this.address = "";
		this.webPage = "";
		this.phone = "";
		this.mobile = "";
		this.fax = "";
		this.countryId = 0;
		this.city = "";
		this.branchId = 0;
		this.gpsLongtitude = 0.0;
		this.gpsLatitude = 0.0;
	}

	public Organisation(Parcel in) {
		readFromParcel(in);
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getCountry_id() {
		return countryId;
	}

	public void setCountry_id(int country_id) {
		this.countryId = country_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getBranch_id() {
		return branchId;
	}

	public void setBranch_id(int branch) {
		this.branchId = branch;
	}

	public double getGpsLongtitude() {
		return gpsLongtitude;
	}

	public void setGpsLongtitude(double gpsLongtitude) {
		this.gpsLongtitude = gpsLongtitude;
	}

	public double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(name);
		out.writeString(email);
		out.writeString(address);
		out.writeString(webPage);
		out.writeString(phone);
		out.writeString(mobile);
		out.writeString(fax);
		out.writeString(branch);
		out.writeString(country);
		out.writeInt(countryId);
		out.writeString(city);
		out.writeInt(branchId);
		out.writeDouble(gpsLongtitude);
		out.writeDouble(gpsLatitude);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		name = in.readString();
		email = in.readString();
		address = in.readString();
		webPage = in.readString();
		phone = in.readString();
		mobile = in.readString();
		fax = in.readString();
		branch = in.readString();
		country = in.readString();
		countryId = in.readInt();
		city = in.readString();
		branchId = in.readInt();
		gpsLongtitude = in.readDouble();
		gpsLatitude = in.readDouble();
	}

	public static final Parcelable.Creator<Organisation> CREATOR = new Parcelable.Creator<Organisation>() {
		public Organisation createFromParcel(Parcel in) {
			return new Organisation(in);
		}

		public Organisation[] newArray(int size) {
			return new Organisation[size];
		}
	};

}