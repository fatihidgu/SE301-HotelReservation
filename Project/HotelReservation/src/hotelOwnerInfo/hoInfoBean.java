package hotelOwnerInfo;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name="hoInfoBean")
@SessionScoped
public class hoInfoBean {
	private List<hoInfo> hoInfo = new ArrayList<>();
	
	public hoInfoBean() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select ho.namee, ho.lastname, u.email, ho.pnumber, ho.bdate, ho.gender from hotelowner ho, users u where u.userid = ho.hoid");
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoInfo.add(new hoInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5), rs.getString(6)));
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public List<hoInfo> getHoInfo() {
		return hoInfo;
	}
}