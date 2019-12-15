package registeredUser;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import user.User;

@ManagedBean (name="EditInBean")
@SessionScoped
public class EditInfoBean {
	private String name;
	private String lastName;
	private String email;
	private String pnumber;
	private Date birthDate;
	private String gender;
	
	private List<RegisteredUser> regUser = new ArrayList<>();
	
	 @PostConstruct
	    public void init()
	    {	try {
		
		          PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.namee, r.lastname, u.email, r.pnumber,r.bdate,r.gender\r\n" + 
		        	"	        FROM registereduser r, users u\r\n" +
		      		"	        WHERE u.email=? AND u.userid = r.rid ");

                preStatement.setString(1, User.email); 
                ResultSet rs = preStatement.executeQuery();
		    
		    
		    while(rs.next()){
		    	setName(rs.getString(1));
		    	setLastName(rs.getString(2));
		    	setEmail(rs.getString(3));
		    	setPnumber(rs.getString(4));
		    	setBirthDate(rs.getDate(5));
		    	setGender(rs.getString(6));
		    	
	        }
		} catch (Exception e) {
			System.out.println(e.toString());
		}}
	 
	 
	 public void EditInfo() {

		 try {	
		 
				 //önce eposta deðiþtir user dan
			  String query = "UPDATE `hotelreservation`.`users` SET `email` = ? WHERE (`email` = ?);";
		      PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
		      preparedStmt.setString  (1, email);
		      preparedStmt.setString  (2, User.email);
		    
		      preparedStmt.executeUpdate();
			 
			 
			 
			   /* // mail ile user id yi buluyoruz
				 PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.rid \r\n" + 
			        		"	            FROM registereduser r, users u\r\n" +
			      		"	            WHERE u.email=? AND u.userid = r.rid ");

		       preStatement.setString(1, email); //buraya login olmuþ kullanýcýnýn emaili gelecek

		       ResultSet rs = preStatement.executeQuery();
			    int UserId=-1;
			    
			    while(rs.next()){
			    	UserId= rs.getInt(1);
		       }*/
		      
		      
			    
			    String bday = birthDate.getYear()+1900+"."+(birthDate.getMonth()+1)+"."+birthDate.getDate();
			    
			    //diðer bilgiler deðiþiyor
			    
			      String query2 = "UPDATE `hotelreservation`.`registereduser` SET `namee` = ?, `lastname` = ?, `pnumber` = ?, `gender` = ?, `bdate` = ? WHERE (`rid` = ?);";
			      PreparedStatement preparedStmt2 = getConnectionDB().prepareStatement(query2);
			      preparedStmt2.setString  (1,name );
			      preparedStmt2.setString  (2,lastName);
			      preparedStmt2.setString  (3,pnumber);
			      preparedStmt2.setString  (4,gender);
			      preparedStmt2.setString  (5,bday);
			      preparedStmt2.setInt     (6,User.userid);
			      
	
			      preparedStmt2.executeUpdate();
			
			    
				
			      
				}catch(Exception  ex) { System.out.println(ex);}
		 }
	

		private Connection getConnectionDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		    return connection;
			
		}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public EditInfoBean() {
	
	}
	
	public List<RegisteredUser> getRegUser() {
		return regUser;
	}
}