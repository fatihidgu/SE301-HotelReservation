package hotelOwnerHotels;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import user.User;

@ManagedBean (name="hoHotelsBean")
@RequestScoped
public class hoHotelsBean {
	private List<hoHotels> hoHotels = new ArrayList<>();
	private List<String> comments = new ArrayList<>();
	
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
		    
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set isactive= '0' where hid= ?;");
			preparedStmt2.setInt(1,id);
			
			preparedStmt2.executeUpdate();
			
			
			Date d = new Date();
		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
			PreparedStatement preparedStmt4 = connection.prepareStatement("select userid, cost from reservation where hotelid = ? and iscancelld = '0' and enddate >= ?;");
			preparedStmt4.setInt(1, id);
			preparedStmt4.setString(2, Today);
			
			ResultSet rset = preparedStmt4.executeQuery();
			
			while(rset.next()) {
				PreparedStatement preparedStmt5 = connection.prepareStatement("select balance from registereduser where rid=?;");
				preparedStmt5.setInt(1, rset.getInt(1));
				ResultSet rset2 = preparedStmt5.executeQuery();
				
				while(rset2.next()) {
					int balance = rset2.getInt(1) + rset.getInt(2);
					PreparedStatement preparedStmt6 = connection.prepareStatement("update registereduser set balance = ? where rid = ?;");
					preparedStmt6.setInt(1,balance);
					preparedStmt6.setInt(2, rset.getInt(1));
					
					preparedStmt6.executeUpdate();
				}
			}
			
			PreparedStatement preparedStmt3 = connection.prepareStatement("update reservation set iscancelld = '1' where hotelid = ? and iscancelld = '0' and enddate >=?;");
			
			preparedStmt3.setInt(1,id);
			preparedStmt3.setString(2, Today);
			preparedStmt3.executeUpdate();
			
		}catch(Exception  ex) { 
			System.out.println(ex.toString());
		}
	}
	
	public void CommentOfHotel(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    PreparedStatement preparedStmt = connection.prepareStatement("select textt from commentt where id = ?");
		    preparedStmt.setInt(1, id);
		    
		    ResultSet rs = preparedStmt.executeQuery();
		    
		    while (rs.next()) {
				rs.getInt(1); // xhtml hazırlanacak comment arrayi
			}
		} catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
}