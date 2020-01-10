package VisitorPage;



import java.util.List;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import hotelOwnerHotels.hoHotels;
import reservations.ReservationInfo;
import user.User;

@ManagedBean (name="VisitorPageBean")
@RequestScoped
public class VisitorPageBean {
	private List<hoHotels> hoHotels = new ArrayList<>();
	private String hotelName="";
	private List<Integer> quality=new ArrayList<>();
	private List<String> location=new ArrayList<>();
	public String userna=User.getName();
	
	
	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
	public void setHoHotels(List<hoHotels> hoHotels) {
		this.hoHotels = hoHotels;
	}
	public void filter() {
	
		this.getQuality().clear();
		this.getLocation().clear();
		this.getHoHotels().clear();
		this.setQuality(quality);
		this.setLocation(location);
		String first = " ";
		String second= " ";
	

		for( int i = 0 ; i < this.getQuality().size(); i++ ) {
		    first=first+("?,");
		 
		 
		}
		first=first.substring(0, first.length()-2);
		for( int i = 0 ; i < this.getLocation().size(); i++ ) {
		    second=second+("?,");
		    
		}
		second=second.substring(0, first.length()-2);
		String and="";
		if(!this.getQuality().isEmpty()) {
			
		}
		if(!this.getQuality().isEmpty()||!this.getLocation().isEmpty()) {
			and=" and ";
		}
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    String query="select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.quality IN ("+first+")"+and+" h.location IN ("+second+");";
		    PreparedStatement preStatement = connection.prepareStatement(query);
		 
		    int index = 1;
		    for( Object o : quality ) {
		       preStatement.setObject(  index++, o );
		    }
		    for( Object o : location ) {
		       preStatement.setObject(  index++, o );
		    }
		    
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11).charAt(0),rs.getString(12).charAt(0)));
	        }
		} catch (Exception e) {
			
		}
		
	}
	public void search() {
		this.getHoHotels().clear();
		this.setHotelName(hotelName);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.namee LIKE ? ESCAPE '!';");
		    preStatement.setString(1, "%" + hotelName + "%");
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11).charAt(0),rs.getString(12).charAt(0)));
	        }
		    hotelName="";
		} catch (Exception e) {
			
		}
	}
	public VisitorPageBean() throws IOException {
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.isactive='1' and h.isaccepted='1';");
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11).charAt(0),rs.getString(12).charAt(0)));
	        }
		   
		} catch (Exception e) {
			
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
			
		}
		return hoHotelsLocation;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public List<Integer> getQuality() {
		return quality;
	}
	public void setQuality(List<Integer> quality) {
		this.quality = quality;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public void HotelInfo(String hotelName, int hotelId) {
		ReservationInfo.setHotelId(hotelId);
		ReservationInfo.setHotelName(hotelName);
	}
	
<<<<<<< HEAD
	public String getUserna() {
		return userna;
	}
	public void setUserna(String userna) {
		this.userna = userna;
	}
	
=======
>>>>>>> master

}
