package registeredUser;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

public class RegisteredUser {
	private int id;
	private String name;
	private String lastName;
	private String email;
	private int pnumber;
	private Date birthDate;
	private String gender;
	private String isSendRequest;
	private int balance;
	
	
	
	//yeni oluþturmak için
	 public RegisteredUser(int id,String name, String lastName, String email, int pnumber, Date birthDate, String gender,int balance) {
		this.id=id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.pnumber = pnumber;
		this.birthDate = birthDate;
		this.gender = gender;
		this.balance=balance;
		this.isSendRequest = "0";
	}
	
	//ekrana basmak için
	public RegisteredUser(String name, String lastName, String email, int pnumber, Date birthDate, String gender) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.pnumber = pnumber;
		this.birthDate = birthDate;
		this.gender = gender;
		
	}
	
	//Admin kullanýyor
	public RegisteredUser(String name, String lastName, String email,int id) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
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
	

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}