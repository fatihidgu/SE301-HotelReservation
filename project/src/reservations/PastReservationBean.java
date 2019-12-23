package reservations;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import user.User;

@ManagedBean (name="pastreservationssBean")
@SessionScoped
public class PastReservationBean {
	private List<Reservation> pastreservationss = new ArrayList<>();
	
	public PastReservationBean() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    
		    Date d = new Date();
		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
		   
		   
		    PreparedStatement preStatement = connection.prepareStatement(" SELECT h.namee ,res.startdate,res.enddate,res.numberofroom,res.roomtype,res.id ,res.cost     \r\n" + 
	                "FROM registereduser r ,reservation res,hotel h,users u\r\n" + 
	                "WHERE u.email=? and u.userid = r.rid and res.userid=r.rid and res.hotelid=h.hid and res.enddate <= ?  and res.iscancelld ='0'");


	preStatement.setString(1,User.email);
	preStatement.setString(2, Today);
	ResultSet rs = preStatement.executeQuery();
		    
		     while(rs.next()){ 
		
		    	 pastreservationss.add(new Reservation(rs.getString(1), rs.getDate(2), rs.getDate(3), rs.getInt(4), rs.getString(5),rs.getInt(6),rs.getInt(7)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public List<Reservation> getPastreservationss() {
		return pastreservationss;
	}


}