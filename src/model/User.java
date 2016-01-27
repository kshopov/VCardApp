package model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	
	private int id;
	private String firstName;
	private String lastName;
	private String postion;
	private String businessPhone;
	private String personalPhone;
	private String email;
	private String skype;
	private String facebook;
	
	public User() {
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.postion = "";
		this.businessPhone = "";
		this.personalPhone = "";
		this.email = "";
		this.skype = "";
		this.facebook = "";
	}

	public User(Parcel in) {
		readFromParcel(in);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostion() {
		return postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(firstName);
		out.writeString(lastName);
		out.writeString(postion);
		out.writeString(businessPhone);
		out.writeString(personalPhone);
		out.writeString(email);
		out.writeString(skype);
		out.writeString(facebook);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		firstName = in.readString();
		lastName = in.readString();
		postion = in.readString();
		businessPhone = in.readString();
		personalPhone = in.readString();
		email = in.readString();
		skype = in.readString();
		facebook = in.readString();
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
}
