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
import user.User;

@ManagedBean (name="reservationssBean")
@RequestScoped
public class ReservationBean {
	private List<Reservation> reservationss = new ArrayList<>();
	
	public ReservationBean() {
		try {
			
		    
            Date d = new Date();
		    
		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
		   
		   PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT h.namee ,res.startdate,res.enddate,res.numberofroom,res.roomtype,res.id ,res.cost   \r\n" + 
	                "FROM registereduser r ,reservation res,hotel h,users u\r\n" + 
	                "WHERE u.email=? and u.userid = r.rid and res.userid=r.rid and res.hotelid=h.hid and res.enddate >= ? and res.iscancelld ='0' ");


	preStatement.setString(1, User.email);
	preStatement.setString(2, Today);
	ResultSet rs = preStatement.executeQuery();
		    
		    while(rs.next()){ 
		    	
		    	reservationss.add(new Reservation(rs.getString(1), rs.getDate(2), rs.getDate(3), rs.getInt(4), rs.getString(5),rs.getInt(6),rs.getInt(7)));
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
	
	public void delete(int id,Double cost) {
		
		try {	
			//iptal et
		String query = "UPDATE `hotelreservation`.`reservation` SET `iscancelld` = '1' WHERE (`id` = ?);";
	      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
	      preparedStmt.setInt   (1, id);
	    
	      preparedStmt.executeUpdate();
	      
	      //yeni balance hesapla
	         PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.balance FROM registereduser r, users u WHERE u.email=? AND u.userid = r.rid ");
             preStatement.setString(1, User.email); 
             ResultSet rs = preStatement.executeQuery();
		     
		     Double balance= 1.0;
		     while(rs.next()){
		    	balance = rs.getDouble(1);
	         }
		     
		     balance = balance+cost;
		    
		     //para iadesi
		      String query2 = "UPDATE `hotelreservation`.`registereduser` SET `balance` = ? WHERE (`rid` = ?);";
		      PreparedStatement preparedStmt2 = getConnectionDB().prepareStatement(query2);
		      preparedStmt2.setDouble  (1,balance );
		      preparedStmt2.setInt     (2,User.userid);
		      
              preparedStmt2.executeUpdate();
              getConnectionDB().close();
              changeRoomNumber(id);
		
		  }catch(Exception  ex) {}
	
		
	}
	
public void changeRoomNumber(int resid){
		
	try{
				int hotelid =0;
			String roomtype ="";
			int canceledRoomNo = 0;
			
			  PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT res.hotelid , res.roomtype , res.numberofroom \r\n" + 
		              "FROM reservation res \r\n" + 
		              "WHERE res.id = ? ");


		     preStatement.setInt(1, resid);
		     ResultSet rs = preStatement.executeQuery();
			 while(rs.next()){hotelid =rs.getInt(1);
			                  roomtype =rs.getString(2);
			                  canceledRoomNo = rs.getInt(3);}
					
					
					int RoomNo=0;
					
					//oda sayýný bul
					if(roomtype.equals("s")) {
						PreparedStatement preStatement1 = getConnectionDB().prepareStatement(" SELECT h.vrooms  \r\n" + 
				                "FROM hotel h\r\n" + 
				                "WHERE h.hid= ?   ");
						
						preStatement1.setInt(1,hotelid);
						ResultSet rs1 = preStatement1.executeQuery();
						while(rs1.next()){  RoomNo =	rs1.getInt(1);}
						
					
					}
					
					else if(roomtype.equals("e")) {
						PreparedStatement preStatement2 = getConnectionDB().prepareStatement(" SELECT h.vroome  \r\n" + 
				                "FROM hotel h\r\n" + 
				                "WHERE h.hid= ?   ");
						
						preStatement2.setInt(1,hotelid);
						ResultSet rs2 = preStatement2.executeQuery();
						while(rs2.next()){  RoomNo =	rs2.getInt(1);}
						
						System.out.println("e de kalan oda:"+RoomNo);
					}
					
					else if(roomtype.equals("p")) {
						PreparedStatement preStatement3 = getConnectionDB().prepareStatement(" SELECT h.vroomp  \r\n" + 
				                "FROM hotel h\r\n" + 
				                "WHERE h.hid= ?   ");
						
						preStatement3.setInt(1,hotelid);
						ResultSet rs3 = preStatement3.executeQuery();
						while(rs3.next()){  RoomNo =	rs3.getInt(1);}
						
						
					}
					
					
					/////////////////////////////////////////////oda ekle
					RoomNo = RoomNo + canceledRoomNo;
					
					
					if(roomtype.equals("s"))
					
					{ String query = "UPDATE `hotelreservation`.`hotel` SET `vrooms` = ? WHERE (`hid` = ?);";
				      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				      preparedStmt.setInt  (1, RoomNo);
				      preparedStmt.setInt  (2, hotelid);
				      preparedStmt.executeUpdate();
				     
					}
					
					else if(roomtype.equals("e"))
					
					{ String query = "UPDATE `hotelreservation`.`hotel` SET `vroome` = ? WHERE (`hid` = ?);";
				      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				      preparedStmt.setInt  (1, RoomNo);
				      preparedStmt.setInt  (2, hotelid);
				      preparedStmt.executeUpdate();
				     
					}
					
					else if(roomtype.equals("p"))
					
					{ String query = "UPDATE `hotelreservation`.`hotel` SET `vroomp` = ? WHERE (`hid` = ?);";
				      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				      preparedStmt.setInt  (1, RoomNo);
				      preparedStmt.setInt  (2, hotelid);
				      preparedStmt.executeUpdate();
				    
					}
					
					
					getConnectionDB().close();}catch(Exception e) {}
		
}
	
	
	
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	

	
	 
}