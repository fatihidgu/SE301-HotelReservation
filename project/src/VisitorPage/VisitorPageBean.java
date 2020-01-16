package VisitorPage;

import java.util.List;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import hotelOwnerHotels.hoHotels;
import reservations.ReservationInfo;
import user.User;

@ManagedBean(name = "VisitorPageBean")
@RequestScoped
public class VisitorPageBean {
public String[] favNumber2;

public String[] locationsO;
public String[] qualitysO;
private List<hoHotels> hoHotels = new ArrayList<>();
private String hotelName = "";
private List<Integer> quality = new ArrayList<>();
private List<String> location = new ArrayList<>();
public String userna = User.getName();
private List<hoHotels> filteredHotels = new ArrayList<>();

public String[] getFavNumber2Value() {
//favNumber2=new String[5];

List<String> a = new ArrayList<>();


try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select DISTINCT h.quality from hotel h where h.isactive='1' and h.isaccepted='1' ORDER BY h.quality DESC;");
ResultSet rs = preStatement.executeQuery();

while (rs.next()) {
a.add(rs.getString(1));
}
qualitysO=new String[a.size()];
for(int i=0;i<a.size();i++) {
qualitysO[i]=a.get(i);
}
} catch (Exception e) {
}
return qualitysO;
}
public String[] getFavNumber2Value1() {
//favNumber2=new String[5];

List<String> a = new ArrayList<>();


try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select DISTINCT h.location from hotel h where h.isactive='1' and h.isaccepted='1'ORDER BY h.location ASC;");
ResultSet rs = preStatement.executeQuery();

while (rs.next()) {
a.add(rs.getString(1));
}
locationsO=new String[a.size()];
for(int i=0;i<a.size();i++) {
String A=a.get(i);
locationsO[i]=A;
}
} catch (Exception e) {
}
return locationsO;
}
public String getFavNumber2InString() {
return Arrays.toString(favNumber2);
}


public List<hoHotels> getHoHotels() {
return hoHotels;
}

public void setHoHotels(List<hoHotels> hoHotels) {
this.hoHotels = hoHotels;
}

public void filter1() {

this.getQuality().clear();
this.getLocation().clear();
this.getHoHotels().clear();
this.setQuality(quality);
this.setLocation(location);
String first = " ";
String second = " ";

for (int i = 0; i < this.getQuality().size(); i++) {
first = first + ("?,");

}
first = first.substring(0, first.length() - 2);
for (int i = 0; i < this.getLocation().size(); i++) {
second = second + ("?,");

}
second = second.substring(0, first.length() - 2);
String and = "";
if (!this.getQuality().isEmpty()) {

}
if (!this.getQuality().isEmpty() || !this.getLocation().isEmpty()) {
and = " and ";
}
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
String query = "select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.quality IN ("
+ first + ")" + and + " h.location IN (" + second + ") and h.isactive='1' and h.isaccepted='1';";
PreparedStatement preStatement = connection.prepareStatement(query);

int index = 1;
for (Object o : quality) {
preStatement.setObject(index++, o);
}
for (Object o : location) {
preStatement.setObject(index++, o);
}

ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
}
} catch (Exception e) {

}

}

public void filter() {
this.getHoHotels().clear();
this.setLocation(location);
this.setQuality(quality);
String qual = "";
String loca = "";
if (!this.quality.isEmpty()) {
for (int i = 0; i < this.quality.size() - 1; i++) {
qual = qual + this.quality.get(i)+",";
}
qual="IN ("+qual+this.quality.get(this.quality.size()-1)+")";

}else {
qual="=h.quality";
}
if (!this.location.isEmpty()) {
for (int i = 0; i < this.location.size()-1; i++) {
loca = loca + "'" + this.location.get(i)+"',";
}
loca="IN ("+loca+"'"+this.location.get(this.location.size()-1)+"')";
}else {
loca="=h.location";
}

try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.quality "+qual+" and h.location "+loca+" and h.isactive='1' and h.isaccepted='1';");
//preStatement.setString(1, qual);
//preStatement.setString(2, "(" + loca + ")");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
}

} catch (Exception e) {

}
}

public void search() {
this.getHoHotels().clear();
this.setHotelName(hotelName);
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.namee LIKE ? ESCAPE '!' and h.isactive='1' and h.isaccepted='1';");
preStatement.setString(1, "%" + hotelName + "%");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
}
hotelName = "";
} catch (Exception e) {

}

}

public VisitorPageBean() throws IOException {

try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select h.hid,h.namee, h.location, h.quality, h.costs, h.vrooms, h.coste, h.vroome, h.costp, h.vroomp, h.isactive, h.isaccepted from hotel h where h.isactive='1' and h.isaccepted='1';");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
hoHotels.add(new hoHotels(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
rs.getString(11).charAt(0), rs.getString(12).charAt(0)));
}

} catch (Exception e) {

}
try {
List<String> a =null;
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select DISTINCT h.location from hotel h where h.isactive='1' and h.isaccepted='1';");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
a.add(rs.getString(1));
}
locationsO = (String[]) a.toArray();

} catch (Exception e) {

}

try {
List<Integer> b = null;
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement(
"select DISTINCT h.quality from hotel h where h.isactive='1' and h.isaccepted='1';");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
b.add(rs.getInt(1));
}
qualitysO = (String[]) b.toArray();

} catch (Exception e) {

}
}

public List<String> HotelsLocations() {
List<String> hoHotelsLocation = new ArrayList<>();
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
PreparedStatement preStatement = connection.prepareStatement("select distinct h.location from hotel h");
ResultSet rs = preStatement.executeQuery();
while (rs.next()) {
hoHotelsLocation.add(rs.getString(1));
}
} catch (Exception e) {

}
return hoHotelsLocation;
}

public String getHotelName() {
return hotelName;
}

public void setHotelName(String hotelName) {
this.hotelName = hotelName;
}

public List<Integer> getQuality() {
return quality;
}

public void setQuality(List<Integer> quality) {
this.quality = quality;
}

public List<String> getLocation() {
return location;
}

public void setLocation(List<String> location) {
this.location = location;
}

public void reload() throws IOException {
ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}

public void HotelInfo(String hotelName, int hotelId) {
ReservationInfo.setHotelId(hotelId);
ReservationInfo.setHotelName(hotelName);
}

public String getUserna() {
return userna;
}

public void setUserna(String userna) {
this.userna = userna;
}

public String[] getLocationsO() {
return locationsO;
}

public void setLocationsO(String[] locationsO) {
this.locationsO = locationsO;
}

public String[] getQualitysO() {
return qualitysO;
}

public void setQualitysO(String[] qualitysO) {
this.qualitysO = qualitysO;
}

public List<hoHotels> getFilteredHotels() {
return filteredHotels;
}

public void setFilteredHotels(List<hoHotels> filteredHotels) {
this.filteredHotels = filteredHotels;
}
public String[] getFavNumber2() {
return favNumber2;
}
public void setFavNumber2(String[] favNumber2) {
this.favNumber2 = favNumber2;
}

}
