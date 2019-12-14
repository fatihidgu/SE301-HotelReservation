package registeredUser;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name="RegUserBean")
@SessionScoped
public class RegisteredUserBean {
	private List<RegisteredUser> regUser = new ArrayList<>();
	
	public RegisteredUserBean() {
		try {
			
			 PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.namee, r.lastname, u.email, r.pnumber,r.bdate,r.gender\r\n" + 
		        		"	            FROM registereduser r, users u\r\n" +
		      		"	            WHERE u.email=? AND u.userid = r.rid ");
 
          preStatement.setString(1, "g"); //buraya login olmuþ kullanýcýnýn emaili gelecek
          ResultSet rs = preStatement.executeQuery();
		    
		    
		    while(rs.next()){
		    	regUser.add(new RegisteredUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getString(6)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public List<RegisteredUser> getRegUser() {
		return regUser;
	}
	
public void SendDeleteRequest() {
		
		try {	
	    
	    //elimizdeki mail ile user id yi buluyoruz
		 PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.rid \r\n" + 
	        		"	            FROM registereduser r, users u\r\n" +
	      		"	            WHERE u.email=? AND u.userid = r.rid ");

       preStatement.setString(1, "g"); //buraya login olmuþ kullanýcýnýn emaili gelecek

       ResultSet rs = preStatement.executeQuery();
	    int UserId=-1;
	    
	    while(rs.next()){
	    	UserId= rs.getInt(1);
       }
	    
	    if(UserId !=-1) { 
	      String query = "UPDATE `hotelreservation`.`registereduser` SET `issendrequest` = '1' WHERE (`rid` = ?);";
	      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
	      preparedStmt.setInt  (1, UserId);
	    
	      preparedStmt.executeUpdate();
	    }
		
	      
		}catch(Exception  ex) {}
	
		
	}


private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
    return connection;
	
}
}