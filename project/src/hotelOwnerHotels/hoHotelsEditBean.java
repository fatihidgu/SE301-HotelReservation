package hotelOwnerHotels;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import user.User;

@ManagedBean (name="hoHotelsEditBean")
@RequestScoped
public class hoHotelsEditBean {
	private String name;
	private String location;
	private int quality;
	private int costs;
	private int vrooms;
	private int coste;
	private int vroome;
	private int costp;
	private int vroomp;
	private static int id;
	
	private List<hoHotels> hoHotels = new ArrayList<>();
	
	public void EditHotel() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set namee= ?, location= ?, quality= ?, costs= ?, vrooms= ?, coste= ?, vroome= ?, costp= ?, vroomp= ? where hownerid= ? and hid= ?;");
			
			preparedStmt2.setString(1,name);
			preparedStmt2.setString(2,location);
			preparedStmt2.setInt(3,quality);
			preparedStmt2.setInt(4,costs);
			preparedStmt2.setInt(5,vrooms);
			preparedStmt2.setInt(6,coste);
			preparedStmt2.setInt(7,vroome);
			preparedStmt2.setInt(8,costp);
			preparedStmt2.setInt(9,vroomp);
			preparedStmt2.setInt(10,User.userid);
			preparedStmt2.setInt(11,id);
			
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
		
		}
	}
	
	public void takeHotelId(int hid) {
		setId(hid);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp from hotel h, users u where h.hownerid = ? and h.hid = ?;");
		    preStatement.setInt(1, User.userid);
		    preStatement.setInt(2, hid);
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	setName(rs.getString(1));
		    	setLocation(rs.getString(2));
		    	setQuality(rs.getInt(3));
		    	setCosts(rs.getInt(4));
		    	setVrooms(rs.getInt(5));
		    	setCoste(rs.getInt(6));
		    	setVroome(rs.getInt(7));
		    	setCostp(rs.getInt(8));
		    	setVroomp(rs.getInt(9));
		    }
		} catch (Exception e) {
			
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getCosts() {
		return costs;
	}

	public void setCosts(int costs) {
		this.costs = costs;
	}

	public int getVrooms() {
		return vrooms;
	}

	public void setVrooms(int vrooms) {
		this.vrooms = vrooms;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public int getVroome() {
		return vroome;
	}

	public void setVroome(int vroome) {
		this.vroome = vroome;
	}

	public int getCostp() {
		return costp;
	}

	public void setCostp(int costp) {
		this.costp = costp;
	}

	public int getVroomp() {
		return vroomp;
	}

	public void setVroomp(int vroomp) {
		this.vroomp = vroomp;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
}