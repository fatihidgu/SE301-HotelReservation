package reservations;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


public class Reservation {

		
private static int id=100;
private int oldId;
private	 int userid;
private	int hotelid;
private	String iscancelld;
private	Date startdate; //
private	Date enddate; //
private	int numberofroom; //
private	String roomtype;//
private String hotelname;//
	
	
    public Reservation() {}
	//normal reservation oluþtururken
	public Reservation(int userid,int hotelid,Date startdate, Date enddate, int numberofroom, String roomtype) {
		id++;
		this.userid=userid ;
		this.hotelid=hotelid ;
		this.iscancelld="0" ;
		this.startdate =startdate;
		this.enddate =enddate ;
		this.numberofroom =numberofroom ;
		this.roomtype=roomtype;
	}
	
	//databaseden çekileni göstermek için
	public Reservation( String Hotelname,Date startdate, Date enddate,int numberofroom, String roomtype,int id) {
	
	    this.hotelname=Hotelname;
		this.startdate =startdate;
		this.enddate =enddate ;
		this.numberofroom =numberofroom ;
		this.roomtype=roomtype;
		this.oldId=id;
	}
	
	
	
	public static int getId() {
		return id;
	}
	
	public int getOldId() {
		return oldId;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getHotelid() {
		return hotelid;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}
	public String getIscancelld() {
		return iscancelld;
	}
	public void setIscancelld(String iscancelld) {
		this.iscancelld = iscancelld;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getNumberofroom() {
		return numberofroom;
	}
	public void setNumberofroom(int numberofroom) {
		this.numberofroom = numberofroom;
	}
	public String getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	public String getHotelname() {
		return hotelname;
	}
	public void setHotelName(String hotelname) {
		this.hotelname = hotelname;
	}
	
	
	
	
}