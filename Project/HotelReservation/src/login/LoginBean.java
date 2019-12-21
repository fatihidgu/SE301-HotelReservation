/**
 * 
 */
/**
 * @author acer
 *
 */
package login;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import user.User;

@ManagedBean (name="LoginBean")
@RequestScoped
public class LoginBean {
	private String username;
	private String password;
	
	private List<User> User = new ArrayList<>();
	
	public LoginBean() {
		
	}
	
	public List<User> getUser() {
		return User;
	}
	public void setValues() {
		this.setUsername(username);
		this.setPassword(password);
		System.out.println(username+" "+password);
		try {
			
			 PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT u.userid, u.email, u.passw,u.typee,u.isdeleted\r\n" + 
		        		"	        FROM users u\r\n" +
		      		"	            WHERE u.email=? AND u.passw =? AND u.isdeleted=0");

         preStatement.setString(1, getUsername()); 
         preStatement.setString(2, getPassword());
         ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	User.add(new User(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public String userPage() {
		String typee=User.get(0).getTypee();
		System.out.println(typee+"hello");
		if(typee.equals("r")) {
			return "registerUser.xhtml";
		}else if(typee.equals("h")){
			return "hotelOwnerMainPage.xhtml";
		}else if(typee=="a"){
			return "registerUser.xhtml";
		}else {
			return "editUserInfo.xhtml";
		}

	}
	


private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
    return connection;
	
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}
}