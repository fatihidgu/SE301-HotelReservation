package hotelOwnerHotels;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import user.User;

@ManagedBean (name="hoHotelsBean")
@RequestScoped
public class hoHotelsBean {
	private List<hoHotels> hoHotels = new ArrayList<>();
	
	public hoHotelsBean() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select h.hid, h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.hownerid = ? and h.isaccepted = '1';");
		    preStatement.setInt(1, User.userid);
		    
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void ActiveHotel(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set isactive= '1' where hownerid= ? and hid= ?;");
			
			preparedStmt2.setInt(1, User.userid);
			preparedStmt2.setInt(2,id);
			
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
			System.out.println(ex.toString());
		}
	}
	
	public void DeactiveHotel(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set isactive= '0' where hownerid= ? and hid= ?;");
			
			preparedStmt2.setInt(1, User.userid);
			preparedStmt2.setInt(2,id);
			
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
			System.out.println(ex.toString());
		}
	}
	
	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
}