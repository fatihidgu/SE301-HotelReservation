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

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean {
	private String username;
	private String password;

	public LoginBean() {

	}

	public void setValues() {

		this.setUsername(username);
		this.setPassword(password);

		try {

			PreparedStatement preStatement = getConnectionDB()
					.prepareStatement("SELECT u.userid, u.email, u.passw, u.typee, u.isdeleted\r\n" + "FROM users u\r\n"
							+ "	WHERE u.email= ? AND u.passw = MD5(?) AND u.isdeleted=0");

			preStatement.setString(1, getUsername());
			preStatement.setString(2, getPassword());
			ResultSet rs = preStatement.executeQuery();

			while (rs.next()) {
				User.setUserid(Integer.parseInt(rs.getString(1)));
				User.setEmail(rs.getString(2));
				User.setTypee(rs.getString(4));

			}
			String typee = User.getTypee();
			if (typee.equals("r")) {
				PreparedStatement preStatement1 = getConnectionDB()
						.prepareStatement("SELECT r.namee\r\n" + "FROM registereduser r\r\n" + "	WHERE r.rid= ?");

				preStatement1.setInt(1, User.getUserid());
				ResultSet rs1 = preStatement1.executeQuery();
				while (rs1.next()) {
					User.setName(rs1.getString(1));
				}

			} else if (typee.equals("h")) {
				System.out.println("hotele girdi");
				PreparedStatement preStatement1 = getConnectionDB()
						.prepareStatement("SELECT h.namee\r\n" + "FROM hotelowner h\r\n" + "	WHERE h.hoid= ?");
				preStatement1.setInt(1, User.getUserid());
				ResultSet rs1 = preStatement1.executeQuery();
				while (rs1.next()) {
					User.setName(rs1.getString(1));
				}

			} else if (typee.equals("a")) {
				User.setName(User.getEmail());

			}

		} catch (Exception e) {
			

		}
	}

	public String userPage() {

		if (User.getUserid() == -1) {
			return "";
		}

		String typee = User.getTypee();

		try {
			System.out.println("type:" + typee);
			if (typee.equals("r")) {
				System.out.println("visitorpage");
				return "VisitorPage.xhtml";
			} else if (typee.equals("h")) {
				return "hotelOwnerMainPage.xhtml";
			} else if (typee.equals("a")) {
				return "admin.xhtml";
			} else {
				return "VisitorPage1.xhtml";
			}
		} catch (Exception e) {
			
			logout();
			
			return "";

		}

	}

	private Connection getConnectionDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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

	public void logout() {
		User.setUserid(-1);
		User.setEmail("");
		User.setTypee("");
	}

	public void setPassword(String password) {
		this.password = password;
	}
}