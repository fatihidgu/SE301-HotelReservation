package hotelOwnerHotels;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import user.User;

@ManagedBean (name="hoHotelsAddBean")
@RequestScoped
public class hoHotelsAddBean {
	private int hid;
	private int hownerid;
	private String name;
	private String location;
	private int quality;
	private int costs;
	private int vrooms;
	private int coste;
	private int vroome;
	private int costp;
	private int vroomp;
	private String isactive;
	private String isaccepted;
	
	private List<hoHotels> hoHotels = new ArrayList<>();
		
	public void AddHotel() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    PreparedStatement preStatement = connection.prepareStatement("select * from hotel h;");
		    ResultSet rs = preStatement.executeQuery();
		    rs.last();
		    int size = rs.getRow();
		    
		    PreparedStatement preparedStmt2 = connection.prepareStatement("insert into hotel (hid, hownerid, namee, location, quality, costs, vrooms, coste, vroome, costp, vroomp, isactive, isaccepted) values (?,?,?,?,?,?,?,?,?,?,?,?,?);");
			
		    preparedStmt2.setInt(1, (size+1));
		    preparedStmt2.setInt(2, User.getUserid());
			preparedStmt2.setString(3,name);
			preparedStmt2.setString(4,location);
			preparedStmt2.setInt(5,quality);
			preparedStmt2.setInt(6,costs);
			preparedStmt2.setInt(7,vrooms);
			preparedStmt2.setInt(8,coste);
			preparedStmt2.setInt(9,vroome);
			preparedStmt2.setInt(10,costp);
			preparedStmt2.setInt(11,vroomp);
			preparedStmt2.setString(12,"1");
			preparedStmt2.setString(13, "0");
			
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
			System.out.println(ex.toString());
		}
	}
		
	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public int getHownerid() {
		return hownerid;
	}

	public void setHownerid(int hownerid) {
		this.hownerid = hownerid;
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

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getIsaccepted() {
		return isaccepted;
	}

	public void setIsaccepted(String isaccepted) {
		this.isaccepted = isaccepted;
	}

	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
}