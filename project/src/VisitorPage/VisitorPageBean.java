package VisitorPage;



import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import hotelOwnerHotels.hoHotels;
import user.User;

@ManagedBean (name="VisitorPageBean")
@SessionScoped
public class VisitorPageBean {
	private List<hoHotels> hoHotels = new ArrayList<>();
	
	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
	public void setHoHotels(List<hoHotels> hoHotels) {
		this.hoHotels = hoHotels;
	}
	public VisitorPageBean() {
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
	public List<String> HotelsLocations() {
		List<String> hoHotelsLocation = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select distinct h.location from hotel h");
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotelsLocation.add(rs.getString(1));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return hoHotelsLocation;
	}
	

}
