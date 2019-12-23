package hotelOwnerInfo;

import java.util.Date;

public class hoInfo {
	private String name;
	private String lastName;
	private String email;
	private int pnumber;
	private Date birthDate;
	private String gender;
	private int oldid;
	
	public hoInfo(String name, String lastName, String email, int pnumber, Date birthDate, String gender) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.pnumber = pnumber;
		this.birthDate = birthDate;
		this.gender = gender;
	}
	public hoInfo(String name, String lastName, String email,int id) {
		//admin özel
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.oldid=id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getPnumber() {
		return pnumber;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public String getGender() {
		return gender;
	}
	public int getOldid() {
		return oldid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPnumber(int pnumber) {
		this.pnumber = pnumber;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setOldid(int oldid) {
		this.oldid = oldid;
	}
}