package message;


import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import user.User;

@ManagedBean (name="msgBean")
@RequestScoped

public class MessageBean {
private List<Message> messages = new ArrayList<>();

	public MessageBean() {
			try {
				 PreparedStatement Mesggs = getConnectionDB().prepareStatement(" SELECT message FROM message WHERE rid=?");
				 Mesggs.setInt(1, User.getUserid());
				 ResultSet rs = Mesggs.executeQuery();
		    while(rs.next()){
		    	messages.add(new Message(rs.getString(1)));
		    }
		    
		} catch (Exception e) {
	System.out.println(e);
		}
	}
	
	
	
	
	private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
	    return connection;
		
	}
	public List<Message> getMessages() {
		return messages;
	}

}
