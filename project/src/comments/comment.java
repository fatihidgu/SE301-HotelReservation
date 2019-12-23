package comments;

public class comment {
private int id;
private int hotelid;
private String text;
private int userid;
private boolean isdeleted;
private String rname;
private String hname;
private int oldid;
public comment(String text, int hotelid,  int userid) {
	this.hotelid = hotelid;
	this.text = text;
	this.userid = userid;
	this.isdeleted = false;
}
public comment(String text) {
	this.text = text;
}


public comment(String rname, String hname,  String text, int oldid) {
	//admin özel
	this.rname=rname;
	this.hname=hname;
	this.text=text;
	this.oldid=oldid;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getHotelid() {
	return hotelid;
}
public void setHotelid(int hotelid) {
	this.hotelid = hotelid;
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
public boolean isIsdeleted() {
	return isdeleted;
}
public void setIsdeleted(boolean isdeleted) {
	this.isdeleted = isdeleted;
}
public String getRname() {
	return rname;
}
public void setRname(String rname) {
	this.rname = rname;
}
public int getOldid() {
	return oldid;
}
public void setOldid(int oldid) {
	this.oldid = oldid;
}
public String getHname() {
	return hname;
}
public void setHname(String hname) {
	this.hname = hname;
}







}
