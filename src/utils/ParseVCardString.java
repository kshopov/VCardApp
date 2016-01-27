package utils;

import model.Organisation;
import model.User;

public class ParseVCardString {
	
	public static Organisation parseOrganisation(String result) {
		Organisation organisation = new Organisation();
		String[] lines = result.split("\n");
		for (String line : lines) {
			System.out.println("LINE " + line);
			if (line.contains("ORG")) {
				organisation.setName(parseLine(line));
			} else if (line.contains("EMAIL")) {
				organisation.setEmail(parseLine(line));
			} else if (line.contains("ADR")) {
				organisation.setAddress(parseLine(line));
			} else if (line.contains("URL")) {
				organisation.setWebPage(parseLine(line));
			} else if (line.contains("TEL;WORK")) {
				organisation.setPhone(parseLine(line));
			} else if (line.contains("MOBILE")) {
				organisation.setMobile(parseLine(line));
			} else if (line.contains("FAX")) {
				organisation.setFax(parseLine(line));
			} else if (line.contains("COUNTRY")) {
				organisation.setCountry(parseLine(line));
			} else if (line.contains("CITY")) {
				organisation.setCity(parseLine(line));
			} else if (line.contains("BRANCH")) {
				organisation.setBranch(parseLine(line));
			} else if (line.contains("LONG")) {
				organisation.setGpsLongtitude(Double.parseDouble(parseLine(line)));
			} else if (line.contains("LAT")) {
				organisation.setGpsLatitude(Double.parseDouble(parseLine(line)));
			}
		}
		
		return organisation;
	}
	
	public static User parseUser(String result) {
		User user = new User();
		String[] lines = result.split("\n");
		for (String line : lines) {
			if (line.contains("FN")) {
				user.setFirstName(parseLine(line));
			} else if (line.contains("LN")) {
				user.setLastName(parseLine(line));
			} else if (line.contains("POS")) {
				user.setPostion(parseLine(line));
			} else if (line.contains("WORK")) {
				user.setBusinessPhone(parseLine(line));
			} else if (line.contains("HOME")) {
				user.setPersonalPhone(parseLine(line));
			} else if (line.contains("EMAIL")) {
				user.setEmail(parseLine(line));
			} else if (line.contains("SKYPE")) {
				user.setSkype(parseLine(line));
			} else if (line.contains("FACEBOOK")) {
				user.setFacebook(parseLine(line));
			}
		}
		return user;
	}
	
	private static String parseLine(String line) {
		String parsedLine = "";
		parsedLine = line.substring(line.lastIndexOf(":")+ 1);

		return parsedLine;
	}

}
