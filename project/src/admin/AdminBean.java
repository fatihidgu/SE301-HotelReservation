package admin;

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

import comments.comment;
import hotelOwnerHotels.hoHotels;
import hotelOwnerInfo.hoInfo;
import registeredUser.RegisteredUser;

@ManagedBean (name="AdminBean")
@RequestScoped

public class AdminBean {
private List<RegisteredUser> regUser = new ArrayList<>();
private List<hoInfo> hoOwner = new ArrayList<>();
private List<hoHotels> hotelss = new ArrayList<>();
private List<comment> comments = new ArrayList<>();
	public AdminBean() {
			try {
				 PreparedStatement regUsers = getConnectionDB().prepareStatement(" SELECT r.namee, r.lastname, u.email,  r.rid  FROM registereduser r, users u\r\n" +
			      		"	            WHERE  u.userid = r.rid and r.issendrequest = 1 and u.isdeleted = 0 ");
	          ResultSet rs = regUsers.executeQuery();
          
          
          PreparedStatement hoOwners = getConnectionDB().prepareStatement(" SELECT o.namee, o.lastname, u.email,  o.hoid  \r\n" + 
          		" FROM hotelowner o, users u        \r\n" + 
          		" WHERE  u.userid = o.hoid and o.issendrequest = 1 and u.isdeleted = 0 ");
        ResultSet rs1 = hoOwners.executeQuery();
        
        
        
       PreparedStatement hotelss1 = getConnectionDB().prepareStatement(" SELECT h.namee , h.location, u.email, h.hid FROM hotel h, users u\r\n" + 
       		"						where h.isaccepted=0 and h.hownerid=u.userid and u.isdeleted=0");
        ResultSet rs2 = hotelss1.executeQuery();
        
        PreparedStatement commentss = getConnectionDB().prepareStatement(" SELECT r.namee, h.namee, c.textt, c.id FROM commentt c, hotel h, registereduser r\r\n" + 
        		"where c.hotelid=h.hid and c.userid=r.rid and c.isdeleted=0");
      ResultSet rs3 = commentss.executeQuery();
        
		    while(rs.next()){
		    	regUser.add(new RegisteredUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		    }
		    while(rs1.next()){
		    	hoOwner.add(new hoInfo(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getInt(4)));
		    }
		    
		    while(rs2.next()){
		    	hotelss.add(new hoHotels(rs2.getString(1), rs2.getString(2),rs2.getString(3), rs2.getInt(4)));
		   
		    }
		    while(rs3.next()){
		    	comments.add(new comment(rs3.getString(1), rs3.getString(2),rs3.getString(3), rs3.getInt(4)));
		   
		    }
		   
		    
		} catch (Exception e) {
		System.out.println("olmadý amaaaa olmaz kiii");
		}
	}
	
	public List<RegisteredUser> getRegUser() {
		return regUser;
}
	public void delete(int id) {
		  
		try {
			//silindiðini iþaretle
		 String query = "UPDATE `hotelreservation`.`users` SET `isdeleted` = '1' WHERE (`userid` = ?);";
	      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
	      preparedStmt.setInt   (1, id);
	      preparedStmt.executeUpdate();
			
			
	      
		//typený getir
		      PreparedStatement regUsers = getConnectionDB().prepareStatement(" SELECT u.typee  FROM users u\r\n" +
			      		"	            WHERE u.userid = ? ");
	      regUsers.setInt(1, id);
        ResultSet rs = regUsers.executeQuery();
     // hangi useri sildiðini bul hotel owner mu registered user mý
        while(rs.next()){
        	if(rs.getString(1).equals("r")) {
        		 Date d = new Date();
        		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
        		//registered userin current reservasyonlarýný yaptýðý hoteller idsi
        		
PreparedStatement hot = getConnectionDB().prepareStatement(" SELECT r.hotelid,r.numberofroom,r.roomtype FROM reservation r\r\n" + 
		"where r.iscancelld=0 and r.enddate >= ? and r.userid=? ");
hot.setString(1, Today);
hot.setInt(2, id);
ResultSet hot1 = hot.executeQuery();
while(hot1.next()) {
	System.out.println("kardþeim þuraya gelir misin");
System.out.println("hotel id"+hot1.getString(1));//hotel id
System.out.println("oda sayýsý"+hot1.getString(2));//oda sayýsý
System.out.println("oda tipi "+hot1.getString(3));// oda tipi

//tüm rezervasyon odalarýný boþalt
if(hot1.getString(3).equals("s")) {
	System.out.println("oldu lan oldu olduuu");
	PreparedStatement hot2 = getConnectionDB().prepareStatement(" SELECT vrooms FROM hotelreservation.hotel\r\n" + 
			"where hid=?");
	hot2.setInt(1, hot1.getInt(1));
	ResultSet hot3 = hot2.executeQuery();
	int bosoda=0;
	while(hot3.next()) {
		bosoda=hot3.getInt(1);
	}
	bosoda=bosoda+hot1.getInt(2);
PreparedStatement clearroom = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET vrooms = ?  WHERE (hid = ?)");
clearroom.setInt   (1,bosoda);
clearroom.setString(2, hot1.getString(1));
clearroom.executeUpdate();
}

if(hot1.getString(3).equals("e")) {
	PreparedStatement hot2 = getConnectionDB().prepareStatement(" SELECT vroome FROM hotelreservation.hotel\r\n" + 
			"where hid=?");
	hot2.setInt(1, hot1.getInt(1));
	ResultSet hot3 = hot2.executeQuery();
	int bosoda=0;
	while(hot3.next()) {
		bosoda=hot3.getInt(1);
	}
	bosoda=bosoda+hot1.getInt(2);
	System.out.println("oldu lan oldu olduuu2");
PreparedStatement clearroom = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET vroome = ?  WHERE (hid = ?)");
clearroom.setInt   (1,bosoda);
clearroom.setString(2, hot1.getString(1));
clearroom.executeUpdate();
}

if(hot1.getString(3).equals("p")) {
	PreparedStatement hot2 = getConnectionDB().prepareStatement(" SELECT vroomp FROM hotelreservation.hotel\r\n" + 
			"where hid=?");
	hot2.setInt(1, hot1.getInt(1));
	ResultSet hot3 = hot2.executeQuery();
	int bosoda=0;
	while(hot3.next()) {
		bosoda=hot3.getInt(1);
	}
	bosoda=bosoda+hot1.getInt(2);
	System.out.println("oldu lan oldu olduuu1");
PreparedStatement clearroom = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET vroomp = ?  WHERE (hid = ?)");
clearroom.setInt   (1,bosoda);
clearroom.setString(2, hot1.getString(1));
clearroom.executeUpdate();
}


}
        		
        		
        		//registered userin tüm rezervasyonlarýný iptal et
        		 PreparedStatement res = getConnectionDB().prepareStatement("UPDATE hotelreservation.reservation SET iscancelld = 1 WHERE userid = ?\r\n" + 
        		 		"");
 	      res.setInt(1, id);
          res.executeUpdate();
          
          
        		
            }
           else if(rs.getString(1).equals("h"))   {
        	   Date d = new Date();
   		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
   		   
        	   //açýk hoteller listesi
        	   PreparedStatement hotels = getConnectionDB().prepareStatement("SELECT hid FROM hotelreservation.hotel\r\n" + 
           	  		"WHERE hownerid= ? and isactive=1 ");
           	  hotels.setInt(1, id);
         ResultSet hrs = hotels.executeQuery();
            	//hotel ownerin tüm otellerini kapat
            	 PreparedStatement res = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET isactive = 0 WHERE (hownerid = ?)");
  	      res.setInt   (1, id);
           res.executeUpdate();
            	//kapatýlan tüm hotellerin rezervasyonlarýný iptal et
           while(hrs.next()) {
        	   // cancel edilmeyen current reservations
        	   PreparedStatement reservations = getConnectionDB().prepareStatement("SELECT userid,cost FROM hotelreservation.reservation\r\n" + 
        	   		"where hotelid= ? and enddate >= ? and iscancelld  =0 ");
        	  
        	   reservations.setInt(1,hrs.getInt(1));
        	   reservations.setString(2, Today);
         ResultSet rsv = reservations.executeQuery();
         //cancel edilmeyen currentleri cancel et
         PreparedStatement cancelres = getConnectionDB().prepareStatement("UPDATE hotelreservation.reservation SET iscancelld = 1 WHERE hotelid = ?\r\n" + 
 		 		"and enddate >= ? and iscancelld  =0 ");
        
         cancelres.setInt(1,hrs.getInt(1));
         cancelres.setString(2, Today);
         cancelres.executeUpdate();
       
         int balance=0;
         int userid=-1;
       
        	   while(rsv.next()) {
        		  
        		    userid=rsv.getInt(1);
        		    balance=rsv.getInt(2);
        		    if(userid!=-1) {
            		   	int cost=0;
            		   	// userin parasý
            		    PreparedStatement costq = getConnectionDB().prepareStatement("SELECT balance FROM registereduser\r\n" + 
            		    		"where rid=?");
            		    costq.setInt(1, userid);
                  ResultSet para = costq.executeQuery();
            		   	while(para.next()) {
            		   		cost=para.getInt(1);
            		   	}
            		   	
            		   balance=balance+cost;
            		 
            		   //userin para iadesi
                  	 PreparedStatement upbalance = getConnectionDB().prepareStatement("UPDATE hotelreservation.registereduser SET balance = ? WHERE (rid = ?)");
                  	upbalance.setInt   (1, balance);
                  	upbalance.setInt   (2, userid);
                  	upbalance.executeUpdate();
                  
            		   
            	   }
        		    
        		    
        		
        	   }
        	  
        	   
        	   
        	   
           }
           
           
            	
            }
        	
        }
		}catch(Exception  ex) {
			System.out.println("something wrong exception");
		}
	
		
	}
	
	public void accept(int id,String name) {
	System.out.println("broo name burda "+name);
	      try {
	    	  PreparedStatement res = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET isaccepted = 1 WHERE (hid = ?) and (namee = ?);\r\n" + 
	    	  		"");
	      res.setInt(1, id);
	      res.setString(2, name);
        res.executeUpdate();
		} catch (Exception e) {
			System.out.println("Hata ");
		}
	      
		
		
	}
	public void decline(int id,String name) {
		System.out.println("broo name burda "+name);
	      try {
	    	  PreparedStatement res = getConnectionDB().prepareStatement("UPDATE hotelreservation.hotel SET isaccepted = 2 WHERE (hid = ?) and (namee = ?);\r\n" + 
	    	  		"");
	      res.setInt(1, id);
	      res.setString(2, name);
      res.executeUpdate();
		} catch (Exception e) {
			System.out.println("Hata ");
		}
	      
		
		
	}
	public void deleteComment(int id) {
		//UPDATE hotelreservation.commentt SET isdeleted = 1 WHERE (id = 2)
	      try {
	    	  PreparedStatement res = getConnectionDB().prepareStatement("UPDATE hotelreservation.commentt SET isdeleted = 1 WHERE (id = ?)");
	      res.setInt(1, id);
    res.executeUpdate();
		} catch (Exception e) {
			System.out.println("Hata ");
		}
	}
	
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	
	
	private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
	    return connection;
		
	}

	public List<hoInfo> getHoOwner() {
		return hoOwner;
	}

	public List<hoHotels> getHotelss() {
		return hotelss;
	}
	public List<comment> getComments() {
		return comments;
	}

}