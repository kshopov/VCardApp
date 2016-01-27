package utils;

import model.Organisation;
import model.User;

public class CreateVCard {
	
	public static final String createOrganisationVCard(Organisation organisation) {
		String data = "BEGIN:VCARD\n"
				+ "VERSION:2.1\n"
				+ "ORG:" + organisation.getName() + "\n"
				+ "EMAIL:" + organisation.getEmail() + "\n"
				+ "ADR;WORK:" + organisation.getAddress() + "\n"
				+ "URL:" + organisation.getWebPage() + "\n"
				+ "TEL;WORK:" + organisation.getPhone() + "\n"
				+ "TEL;WORK;MOBILE:" + organisation.getMobile() + "\n"
				+ "TEL;WORK;FAX:" + organisation.getFax() + "\n"
				+ "COUNTRY:" + organisation.getCountry() + "\n"
				+ "CITY:" + organisation.getCity() + "\n"
				+ "BRANCH:" + organisation.getBranch() + "\n"
				+ "GEO;LAT:" + organisation.getGpsLatitude() + "\n"
				+ "GEO;LONG:" + organisation.getGpsLongtitude() + "\n"
				+ "END:VCARD";
		
		return data;
	}
	
	public static final String createUserVCard(User user) {
		String data = "BEGIN:VCARD\n"
				+ "VERSION:2.1\n"
				+ "FN:" + user.getFirstName() + "\n"
				+ "LN:" + user.getLastName() + "\n"
				+ "POS:" + user.getPostion() + "\n"
				+ "WORK:" + user.getBusinessPhone() + "\n"
				+ "HOME:" + user.getPersonalPhone() + "\n"
				+ "EMAIL:" + user.getEmail() + "\n"
				+ "SKYPE:" + user.getSkype() + "\n" 
				+ "FACEBOOK:" + user.getFacebook() + "\n"
				+ "END:VCARD";
		
		return data;
	}

}
