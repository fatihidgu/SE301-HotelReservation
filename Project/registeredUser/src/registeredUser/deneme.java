package registeredUser;


import java.sql.*;
import java.util.Date;

public class deneme {
	
	String name, lastname, email, passw,pnumber, gender, bdate;
	String denemeEmail ="g";
 public deneme() {}
	
	   public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
	       deneme u = new deneme();
		  u.GetUserInfo();
		 //    System.out.println("miiiiiiiiiiiiiiiii");
		//  u.UpdateUserInfo();
		  // u.GetUserReservations();
		  // u.GetUserPastReservations();
		   
	        
	     }
	    public Connection ConnectDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	        	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	 	        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey", "root", "");
                 return connection;
	        }
	    
	    
	    
	    public void GetUserInfo() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	    	Connection co = ConnectDB();  
		    
	    	PreparedStatement preStatement = co.prepareStatement(" SELECT r.namee, r.lastname, u.email, r.pnumber,r.bdate,r.gender\r\n" + 
	        		"	            FROM registereduser r, users u\r\n" +
	      		"	            WHERE u.email=? AND u.userid = r.rid ");
	    	    
	    	 preStatement.setString(1, denemeEmail); //buraya login olmuþ kullanýcýnýn emaili gelecek
	    	    ResultSet rs2 = preStatement.executeQuery();
	    	
	    	
		        while(rs2.next()){
		            System.out.println("name: "+rs2.getString(1));
		            System.out.println("lastname: "+rs2.getString(2));
		            System.out.println("email: "+rs2.getString(3));
		            System.out.println("pnumber: "+rs2.getString(4));
		            System.out.println("bate: "+rs2.getString(5));
		            System.out.println("gender: "+rs2.getString(6));

		        }
       } 
	    
	    
	    
	    public void UpdateUserInfo() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	    	Connection co = ConnectDB();  
	    
	    	//infolaralýp textboxlara koyabileriz ordan tekrar bilgi çektiðimizde deðiþmeyen kýsýmlar ayný olur 
		    
	    	PreparedStatement preStatement = co.prepareStatement(
	                "UPDATE registereduser\r\n" + 
	                "SET  namee=?, lastname=?, email=?, pnumber=?, gender=?, bdate=?\r\n" + 
	                "WHERE email = ?");
	
	    	//bu soru iþaretleri websitesine girilem bilgilerden güncellenecek
	    	preStatement.setString(1, "furki");
	    	preStatement.setString(2, "konuk");
	    	preStatement.setString(3, "g");
	    	preStatement.setInt(4, 454);
	    	preStatement.setString(5, "e");
	    	preStatement.setString(6, "2017-01-01");
	    	preStatement.setString(7, denemeEmail);
	    	
	        preStatement.executeUpdate();
	    		
	        
	        GetUserInfo(); //deeme için sil
	    	
		      
       } 
        
	    
	    public void GetUserReservations() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	    	Connection co = ConnectDB();  
		    
	    	 PreparedStatement preStatement = co.prepareStatement(" SELECT h.namee ,res.startdate,res.enddate,res.numberofroom,res.roomtype     \r\n" + 
	    			        		                              "FROM registereduser r ,reservation res,hotel h \r\n" + 
	    			      	            	                      "WHERE r.email=? and res.userid=r.rid and res.hotelid=h.hid");
	    	 
	    	    
	    	    preStatement.setString(1, denemeEmail); //buraya login olmuþ kullanýcýnýn emaili gelecek
	    	    ResultSet rs2 = preStatement.executeQuery();
	    	
	    	
		        while(rs2.next()){
		        	 System.out.println("hotel name: "+rs2.getString(1));
		            System.out.println("date1: 11111111111 "+rs2.getDate(2));
		            System.out.println("date2: "+rs2.getString(3));
		            System.out.println("no room : "+rs2.getString(4));
		            System.out.println("room type : "+rs2.getString(5));

		        }
       }

	    public void GetUserPastReservations() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
	    	Connection co = ConnectDB();  
		    
	    Date d = new Date();
	    
	    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
	    System.out.println(Today);
	   
	    PreparedStatement preStatement = co.prepareStatement(" SELECT h.namee ,res.startdate,res.enddate,res.numberofroom,res.roomtype     \r\n" + 
                "FROM registereduser r ,reservation res,hotel h \r\n" + 
                "WHERE r.email=? and res.userid=r.rid and res.hotelid=h.hid and res.enddate <= ?");


preStatement.setString(1, denemeEmail);//buraya login olmuþ kullanýcýnýn emaili gelecek
preStatement.setString(2, Today);
ResultSet rs2 = preStatement.executeQuery();


while(rs2.next()){
	 System.out.println("hotel name: "+rs2.getString(1));
     System.out.println("date1: "+rs2.getString(2));
     System.out.println("date2: "+rs2.getString(3));
     System.out.println("no room : "+rs2.getString(4));
     System.out.println("room type : "+rs2.getString(5));;

}




       }
}
