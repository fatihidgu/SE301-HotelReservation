package comments;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.sun.faces.facelets.tag.jsf.core.ViewHandler;
import com.sun.glass.ui.Application;

import hotelOwnerHotels.hoHotels;
import user.User;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
@ManagedBean(name="commentBean")
@SessionScoped
public class commentBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hotelid;
	private String text;
	private int userid;
	private String hotelname;
	private String location;
	private int quality;
	private int costs;
	private int vrooms;
	private int coste;
	private int vroome;
	private int costp;
	private int vroomp;
	private  List<comment> commentt = new ArrayList<>();
	
	
	
	public commentBean() {
		
	}
	
	
	
    public void takeHotelId(int hid, String name) {
    	setHotelid(hid);
    	setHotelname(name);
    	System.out.println("geldi miiii   "+hid);
    	System.out.println("gelmedi mi "+hotelid);
    	System.out.println("çalýþýyor muyuz peki"+ hotelname);
		try {
		    PreparedStatement preStatement = getConnectionDB().prepareStatement("select h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp from hotel h where h.hid=? and h.namee=? ");
		    preStatement.setInt(1, hotelid);
		    preStatement.setString(2, hotelname);
		    ResultSet rs = preStatement.executeQuery();
		    PreparedStatement preStatement1 = getConnectionDB().prepareStatement("select c.textt from hotel h,commentt c where h.hid=? and c.hotelid=h.hid;");
		    preStatement1.setInt(1, hotelid);
		    ResultSet rs2 = preStatement1.executeQuery();
		  
		    while(rs.next()){
		    	  
		    	setHotelname(rs.getString(1));
		    	setLocation(rs.getString(2));
		    	setQuality(rs.getInt(3));
		    	
		    	setCosts(rs.getInt(4));
		    	setVrooms(rs.getInt(5));
		    	
		    	setCoste(rs.getInt(6));
		    	setVroome(rs.getInt(7));
		    	
		    	setCostp(rs.getInt(8));
		    	setVroomp(rs.getInt(9));
		
		    }
		    commentt = new ArrayList<>();
		    while(rs2.next()) {
		    	
		    	commentt.add(new comment(rs2.getString(1)));
		    	System.out.println("yorumlaaaar"+rs2.getString(1));
		    }
		    
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	
    	
    	
    }
    
    public void writecomment() {
    	try {
    		System.out.println(getHotelid());
    		int cid=0;
		    PreparedStatement preStatement = getConnectionDB().prepareStatement("SELECT count(*) FROM hotelreservation.commentt;");
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	cid=rs.getInt(1);
		    	}
		    cid++;
		
		   PreparedStatement res = getConnectionDB().prepareStatement("INSERT INTO `hotelreservation`.`commentt` (`id`, `hotelid`, `textt`, `userid`, `isdeleted`) VALUES (?, ?, ?, ?, '0');");
		 
		   res.setInt(1,cid);
		 
		      res.setInt(2, getHotelid());
		
		      res.setString(3, getText());
		     
		      res.setInt(4, User.userid);
		      setText("");
	    res.executeUpdate();
	    

		    
    } catch (Exception e) {
		System.out.println(e.toString());
	}
    	
    }
    
	public commentBean(int hotelname, String text, int userid) {
		super();
		this.hotelid = hotelname;
		this.text = text;
		this.userid = userid;
	}
	public void reload() throws IOException {
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}


	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getHotelname() {
		return hotelname;
	}



	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public int getQuality() {
		return quality;
	}



	public void setQuality(int quality) {
		this.quality = quality;
	}



	public int getCosts() {
		return costs;
	}



	public void setCosts(int costs) {
		this.costs = costs;
	}



	public int getVrooms() {
		return vrooms;
	}



	public void setVrooms(int vrooms) {
		this.vrooms = vrooms;
	}



	public int getCoste() {
		return coste;
	}



	public void setCoste(int coste) {
		this.coste = coste;
	}



	public int getVroome() {
		return vroome;
	}



	public void setVroome(int vroome) {
		this.vroome = vroome;
	}



	public int getCostp() {
		return costp;
	}



	public void setCostp(int costp) {
		this.costp = costp;
	}



	public int getVroomp() {
		return vroomp;
	}



	public void setVroomp(int vroomp) {
		this.vroomp = vroomp;
	}



	private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
	    return connection;
	}
	public int getHotelid() {
		return hotelid;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}




	public List<comment> getCommentt() {
		return commentt;
	}




	public void setCommentt(List<comment> commentt) {
		this.commentt = commentt;
	}

}
