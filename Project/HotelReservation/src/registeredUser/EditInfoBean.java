package registeredUser;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name="EditInBean")
@SessionScoped
public class EditInfoBean {
	private String name;
	private String lastName;
	private String email;
	private String pnumber;
	private String birthDate;
	private String gender;
	
	private List<RegisteredUser> regUser = new ArrayList<>();
	
	 @PostConstruct
	    public void init()
	    {	try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
			 PreparedStatement preStatement = connection.prepareStatement(" SELECT r.namee, r.lastname, u.email, r.pnumber,r.bdate,r.gender\r\n" + 
		        		"	            FROM registereduser r, users u\r\n" +
		      		"	            WHERE u.email=? AND u.userid = r.rid ");

preStatement.setString(1, "g"); //buraya login olmuþ kullanýcýnýn emaili gelecek
ResultSet rs = preStatement.executeQuery();
		    
		    
		    while(rs.next()){
		    	setName(rs.getString(1));
		    	setLastName(rs.getString(2));
		    	setEmail(rs.getString(3));
		    	setPnumber(rs.getString(4));
		    	setBirthDate(rs.getString(5));
		    	setGender(rs.getString(6));
		    	
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	       
	    }
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	
	public EditInfoBean() {
	
	}
	
	public List<RegisteredUser> getRegUser() {
		return regUser;
	}
}