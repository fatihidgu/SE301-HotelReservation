package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import registeredUser.RegisteredUser;
import user.User;

@ManagedBean(name = "RegistrationBean")
@ViewScoped
public class RegistrationBean {
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String pnumber;
	private Date birthDate;
	private String gender;
	private String typee;

	

	public String getTypee() {
		return typee;
	}

	public void setTypee(String typee) {
		this.typee = typee;
	}

	public RegistrationBean() {

	}

	public void insertR() {
		this.setTypee("r");
		this.insertUser();
	}
	public void insertHO() {
		this.setTypee("h");
		this.insertUser();
	}

	/*public void setValues() {
		// this.setUsername(username);
		this.setPassword(password);
		try {

			PreparedStatement preStatement = getConnectionDB().prepareStatement(
					" SELECT u.userid, u.email, u.passw,u.typee,u.isdeleted\r\n" + "	        FROM users u\r\n"
							+ "	            WHERE u.email=? AND u.passw =? AND u.isdeleted=0");

			// preStatement.setString(1, getUsername());
			preStatement.setString(2, getPassword());
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				User.add(new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4)));
			}
		} catch (Exception e) {
	
		}
	}*/

	public void insertUser() {

		try {

			int userid = 0;
			// user için id no atama
			try {
				PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT MAX(u.userid) AS countId FROM users u");
				ResultSet rs = preStatement.executeQuery();
				//userid = rs.getInt(1);
				while(rs.next()){ userid = rs.getInt(1);}
		
				userid++;

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			
			}
			try {
				//String query2 = "INSERT INTO hotelreservation.users(userid,email,passw,typee,isdeleted) VALUES (?,?,MD5(?),?,?);";
				PreparedStatement preparedStmt2 = getConnectionDB().prepareStatement("INSERT INTO hotelreservation.users(userid,email,passw,typee,isdeleted) VALUES (?,?,MD5(?),?,?);");
				preparedStmt2.setInt(1, userid);
				preparedStmt2.setString(2, email);
				preparedStmt2.setString(3,password);
				preparedStmt2.setString(4, typee);
				preparedStmt2.setInt(5, 0);
				preparedStmt2.executeUpdate();

			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println(e.toString());
			}
			if(typee=="r") {
			try {
				String bday = birthDate.getYear() + 1900 + "." + (birthDate.getMonth() + 1) + "." + birthDate.getDate();

				String query4 = "INSERT INTO hotelreservation.registereduser(rid,namee,lastname,pnumber,gender,bdate,balance,issendrequest) VALUES (?,?,?,?,?,?,?,?)";
				PreparedStatement preparedStmt4 = getConnectionDB().prepareStatement(query4);
				preparedStmt4.setInt(1, userid);
				preparedStmt4.setString(2, name);
				preparedStmt4.setString(3, lastName);
				preparedStmt4.setInt(4, Integer.parseInt(pnumber));
				System.out.println(pnumber);
				preparedStmt4.setString(5, gender);
				preparedStmt4.setString(6, bday);
				preparedStmt4.setInt(7, 500);
				preparedStmt4.setString(8, "0");
				System.out.println("executeden öncesiii");
				preparedStmt4.executeUpdate();
				System.out.println("executeee ????");
			} catch (Exception e) {
			System.out.println("exception1");
			System.out.println(e.toString());
			}
			}else if(typee=="h") {
				try {
					String bday = birthDate.getYear() + 1900 + "." + (birthDate.getMonth() + 1) + "." + birthDate.getDate();

					String query4 = "INSERT INTO hotelreservation.hotelowner(hoid,namee,lastname,pnumber,gender,bdate,issendrequest) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement preparedStmt4 = getConnectionDB().prepareStatement(query4);
					preparedStmt4.setInt(1, userid);
					preparedStmt4.setString(2, name);
					preparedStmt4.setString(3, lastName);
					preparedStmt4.setInt(4, Integer.parseInt(pnumber));
					preparedStmt4.setString(5, gender);
					preparedStmt4.setString(6, bday);
					preparedStmt4.setString(7, "0");
					preparedStmt4.executeUpdate();
					
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				
				}
			}

			

		} catch (Exception ex) {
		
		}
	}

	private Connection getConnectionDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		return connection;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
