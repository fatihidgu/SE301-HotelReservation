package hotelOwnerInfo;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import user.User;

@ManagedBean (name="hoInfoBean")
@RequestScoped
public class hoInfoBean {
	private List<hoInfo> hoInfo = new ArrayList<>();
	
	public hoInfoBean() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select ho.namee, ho.lastname, u.email, ho.pnumber, ho.bdate, ho.gender from hotelowner ho, users u where u.userid = ho.hoid and ho.hoid = ?;");
		    preStatement.setInt(1, User.getUserid());
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoInfo.add(new hoInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getString(6)));
	        }
		} catch (Exception e) {
		
		}
	}
	
	public List<hoInfo> getHoInfo() {
		return hoInfo;
	}
	
	public void SendDeleteRequest() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preparedStmt = connection.prepareStatement("update hotelowner set issendrequest = '1' where hoid = ?");
		    preparedStmt.setInt  (1, User.getUserid());
		    preparedStmt.executeUpdate();
		    addMessage("Send Request","A request to admin to delete your account sent");
		}catch(Exception  ex) {
		
		}
	}

	public void addMessage(String summary, String detail) {  
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);  
		FacesContext.getCurrentInstance().addMessage(null, message);  
	} 
}