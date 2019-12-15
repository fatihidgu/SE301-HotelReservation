package registeredUser;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

public class RegisteredUser {
	private String name;
	private String lastName;
	private String email;
	private int pnumber;
	private Date birthDate;
	private String gender;
	private int oldid;
	private Double balance;
	
	
	public RegisteredUser(String name, String lastName, String email, int pnumber, Date birthDate, String gender,Double balance) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.pnumber = pnumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.balance=balance;
	}
	
	//Admin kullanýyor
	public RegisteredUser(String name, String lastName, String email,int id) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.oldid = id;
	}
	
	public RegisteredUser() {}
	
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}