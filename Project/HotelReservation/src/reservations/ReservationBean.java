package reservations;

import java.util.List;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import registeredUser.RegisteredUser;

@ManagedBean (name="reservationssBean")
@RequestScoped
public class ReservationBean {
	private List<Reservation> reservationss = new ArrayList<>();
	
	public ReservationBean() {
		try {
			
		    
  Date d = new Date();
		    
		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
		   
		   PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT h.namee ,res.startdate,res.enddate,res.numberofroom,res.roomtype,res.id    \r\n" + 
	                "FROM registereduser r ,reservation res,hotel h,users u\r\n" + 
	                "WHERE u.email=? and u.userid = r.rid and res.userid=r.rid and res.hotelid=h.hid and res.enddate >= ? and res.iscancelld ='0' ");


	preStatement.setString(1, "furki@gmail.com");//buraya login olmuþ kullanýcýnýn emaili gelecek
	preStatement.setString(2, Today);
	ResultSet rs = preStatement.executeQuery();
		    
		    while(rs.next()){ 
		    	//String HotelName,Date startdate, Date enddate,int numberofroom, char roomtype
		    	reservationss.add(new Reservation(rs.getString(1), rs.getDate(2), rs.getDate(3), rs.getInt(4), rs.getString(5),rs.getInt(6)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public List<Reservation> getReservationss() {
		return reservationss;
	}
	
	private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
	    return connection;
		
	}
	
	public void delete(int id) {
		
		try {	
		String query = "UPDATE `hotelreservation`.`reservation` SET `iscancelld` = '1' WHERE (`id` = ?);";
	      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
	      preparedStmt.setInt   (1, id);
	    
	      preparedStmt.executeUpdate();
	     
	      
		}catch(Exception  ex) {}
	
		
	}
	
	
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	

	
	 
}