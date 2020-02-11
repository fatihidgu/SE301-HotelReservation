package hotelOwnerHotels;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import comments.comment;
import user.User;

@ManagedBean (name="hoHotelsBean")
@RequestScoped
public class hoHotelsBean {
	private List<hoHotels> hoHotels = new ArrayList<>();
	private List<comment> comments = new ArrayList<>();
	
	private String name;
	private String lastname;
	private String text;
	//yeni
    private String startDate;
	private String endDate;
    private int numberOfVrooms = 0;
    private int numberOfVroome = 0;
    private int numberOfVroomp = 0;

	
	public hoHotelsBean() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement preStatement = connection.prepareStatement("select h.hid, h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.hownerid = ? and h.isaccepted = '1';");
		    preStatement.setInt(1, User.getUserid());
		    
		    ResultSet rs = preStatement.executeQuery();
		    while(rs.next()){
		    	hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
	        }
		} catch (Exception e) {
		
		}
	}
	

	
	public void ActiveHotel(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement prestatement = connection.prepareStatement("select isactive from hotel where hownerid = ? and hid = ?");
            prestatement.setInt(1, User.getUserid());
            prestatement.setInt(2, id);
            ResultSet resultset = prestatement.executeQuery();
            while(resultset.next()) {
                int current = resultset.getInt(1);
                if (current == 1) {
                    addMessage("Attention!","The hotel is already active!");
                } else {
                    addMessage("","The hotel becomes active!");
                }
            }
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set isactive= '1' where hownerid= ? and hid= ?;");
			
			preparedStmt2.setInt(1, User.getUserid());
			preparedStmt2.setInt(2,id);
			
			preparedStmt2.executeUpdate();
			
		}catch(Exception  ex) { 
		
		}
	}
	
	public void DeactiveHotel(int id) {
		try {
			Date d = new Date();
		    String Today = d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
		    
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    PreparedStatement prestatement = connection.prepareStatement("select isactive from hotel where hownerid = ? and hid = ?");
            prestatement.setInt(1, User.getUserid());
            prestatement.setInt(2, id);
            ResultSet resultset = prestatement.executeQuery();
            while(resultset.next()) {
                int current = resultset.getInt(1);
                if (current == 0) {
                    addMessage("Attention!","The hotel is already deactive!");
                } else {
                    addMessage("","The hotel becomes deactive!");
                }
            }
		    PreparedStatement preparedStmt2 = connection.prepareStatement("update hotel set isactive= '0' where hid= ?;");
			preparedStmt2.setInt(1,id);
			
			preparedStmt2.executeUpdate();
		
			
			PreparedStatement sendMsg = connection.prepareStatement("select distinct userid from reservation r where hotelid =? and iscancelld = 0 and enddate >= ?");
			sendMsg.setInt(1,id);
			sendMsg.setString(2, Today);
			ResultSet rsert = sendMsg.executeQuery();
		
			
			while (rsert.next()) {
				int kimse = rsert.getInt(1);
				int msgid=0;//queryden max çekilecek
				String hot="";//Hotel name mesaj içinde kullanýlacak
				PreparedStatement xcvb = connection.prepareStatement(" SELECT MAX(idmessage) FROM message");
				ResultSet rs = xcvb.executeQuery();
				
				while(rs.next()){ msgid = rs.getInt(1);}
		
				msgid++;
				PreparedStatement xs = connection.prepareStatement("SELECT h.namee FROM hotel h,message m where m.hotelid=? and m.hotelid = h.hid ");
				xs.setInt(1, id);
				ResultSet xd = xs.executeQuery();
				while(xd.next()) {
					hot=xd.getString(1);
					System.out.println("hot "+hot);
					System.out.println(xd.getString(1));
				}

				hot+= " is deactivated, your reservation is cancelled | "+Today;
				PreparedStatement preparedStmt9 = connection.prepareStatement("INSERT INTO `hotelreservation`.`message` (`idmessage`, `message`, `hotelid`, `rid`) VALUES (?,?,?,?);\r\n" + 
						"");
				preparedStmt9.setInt(4,kimse);
				preparedStmt9.setInt(1, msgid);
				preparedStmt9.setString(2,hot);
				preparedStmt9.setInt(3, id);
				preparedStmt9.executeUpdate();
				
			}
			
			
			PreparedStatement preparedStmt4 = connection.prepareStatement("select userid, cost from reservation where hotelid = ? and iscancelld = '0' and enddate >= ?;");
			preparedStmt4.setInt(1, id);
			preparedStmt4.setString(2, Today);
			
			ResultSet rset = preparedStmt4.executeQuery();
			
			while(rset.next()) {
				PreparedStatement preparedStmt5 = connection.prepareStatement("select balance from registereduser where rid=?;");
				preparedStmt5.setInt(1, rset.getInt(1));
				ResultSet rset2 = preparedStmt5.executeQuery();
				
				while(rset2.next()) {
					int balance = rset2.getInt(1) + rset.getInt(2);
					PreparedStatement preparedStmt6 = connection.prepareStatement("update registereduser set balance = ? where rid = ?;");
					preparedStmt6.setInt(1,balance);
					preparedStmt6.setInt(2, rset.getInt(1));
					
					preparedStmt6.executeUpdate();
				}
			}
			
			PreparedStatement preparedStmt3 = connection.prepareStatement("update reservation set iscancelld = '1' where hotelid = ? and iscancelld = '0' and enddate >=?;");
			
			preparedStmt3.setInt(1,id);
			preparedStmt3.setString(2, Today);
			preparedStmt3.executeUpdate();
			
		}catch(Exception  ex) { 
		System.out.println(ex);
		}
	}
	
	public void CommentOfHotel(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    	    	    
		    PreparedStatement preparedStmt = connection.prepareStatement("select r.namee, r.lastname, c.textt from registereduser r, commentt c where c.hotelid = ? and r.rid in (select userid from commentt where hotelid = ?);");
		    preparedStmt.setInt(1, id);
		    preparedStmt.setInt(2, id);
		    
		    ResultSet rs = preparedStmt.executeQuery();
		    
		    while (rs.next()) {
		    	name = rs.getString(1);
		    	lastname = rs.getString(2);
		    	text = rs.getString(3);
		    	comments.add(new comment(name, lastname, text));
			}
		    
		} catch(Exception ex) {
		System.out.println(ex);
		}
	}
	
	public List<hoHotels> getHoHotels() {
		return hoHotels;
	}
	
	public List<comment> getComments(){
		return comments;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
    
    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfVrooms() {
		return numberOfVrooms;
	}

	public void setNumberOfVrooms(int numberOfVrooms) {
		this.numberOfVrooms = numberOfVrooms;
	}

	public int getNumberOfVroome() {
		return numberOfVroome;
	}

	public void setNumberOfVroome(int numberOfVroome) {
		this.numberOfVroome = numberOfVroome;
	}

	public int getNumberOfVroomp() {
		return numberOfVroomp;
	}

	public void setNumberOfVroomp(int numberOfVroomp) {
		this.numberOfVroomp = numberOfVroomp;
	}

}