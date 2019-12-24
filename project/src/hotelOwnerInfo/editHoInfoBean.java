package hotelOwnerInfo;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import user.User;

@ManagedBean (name="editHoInfoBean")
@RequestScoped
public class editHoInfoBean {
	private String name;
	private String lastName;
	private int pnumber;
	private Date birthDate;
	private String gender;
	
	private List<hoInfo> hoInfo = new ArrayList<>();
	
	@PostConstruct
	public void init(){	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("SELECT ho.namee, ho.lastname, ho.pnumber, ho.bdate, ho.gender FROM hotelowner ho, users u WHERE u.userid = ho.hoid and u.userid = ?;");
		    preStatement.setInt(1, User.userid);
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	setName(rs.getString(1));
		    	setLastName(rs.getString(2));
		    	setPnumber(rs.getInt(3));
		    	setBirthDate(rs.getDate(4));
		    	setGender(rs.getString(5));
		    }
		} catch (Exception e) {
		
		}
	}
	
	public void EditInfo() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
			PreparedStatement preparedStmt2 = connection.prepareStatement("UPDATE hotelowner SET namee = ?, lastname = ?, pnumber = ?, bdate = ?, gender = ? WHERE (hoid = ?);");
			String bday = birthDate.getYear()+1900+"."+(birthDate.getMonth()+1)+"."+birthDate.getDate();
			preparedStmt2.setString(1,name);
			preparedStmt2.setString(2,lastName);
			preparedStmt2.setInt(3,pnumber);
			preparedStmt2.setString(4, bday);
			preparedStmt2.setString(5,gender);
			preparedStmt2.setInt(6,User.userid);
	
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
		
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

	public int getPnumber() {
		return pnumber;
	}

	public void setPnumber(int pnumber) {
		this.pnumber = pnumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public List<hoInfo> getHoInfo() {
		return hoInfo;
	}
}