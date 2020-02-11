package reservations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import registeredUser.RegisteredUser;
import user.User;

@ManagedBean(name = "MakeResBean")
@ViewScoped
public class MakeReservationBean {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<Integer, Integer>> data = new HashMap<>();
	private Map<String, String> roommap;
	private Map<Integer, Integer> numbermap;

	private int hotelid;
	private String hotelname = ReservationInfo.getHotelName();
	private Date startdate = ReservationInfo.getStart();
	private Date enddate = ReservationInfo.getEnd();
	private int numberofroom = ReservationInfo.getNoRoom();
	private String roomtype = ReservationInfo.getRoomType();
	private int cost = ReservationInfo.getCost();

	private int balance = User.getBalance();

	@PostConstruct
	public void init() {
		try {
			setHotelid(ReservationInfo.getHotelId());
			setHotelname(ReservationInfo.getHotelName());
			getBalaanceDB();

		} catch (Exception e) {

		}
	}

	public void getBalaanceDB() {
		try {

			PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT r.balance \r\n"
					+ "	        FROM registereduser r, users u\r\n" + "	        WHERE u.email=? AND u.userid = r.rid ");

			preStatement.setString(1, User.getEmail());
			ResultSet rs = preStatement.executeQuery();

			while (rs.next()) {
				User.setBalance(rs.getInt(1));
				// setBalance(rs.getInt(1));
			}
		} catch (Exception e) {

		}
	}

	// make reservationdan alýcak/güncellenecekler
	public void takeReservationInfo(int numberofroom, String roomtype, Date startdate, Date enddate) {
		ReservationInfo.setRoomType(roomtype);
		ReservationInfo.setNoRoom(numberofroom);
		ReservationInfo.setStart(startdate);
		ReservationInfo.setEnd(enddate);

		////
		try {
			calculatecost(ReservationInfo.getRoomType());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}

	// ücret hesaplama
	private void calculatecost(String roomtype)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int costOfRoom = 0;

		if (roomtype.equals("standart")) {
			PreparedStatement preStatement = getConnectionDB()
					.prepareStatement(" SELECT h.costs  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

			preStatement.setInt(1, hotelid);
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				costOfRoom = rs.getInt(1);
			}
		}

		else if (roomtype.equals("executive")) {
			PreparedStatement preStatement = getConnectionDB()
					.prepareStatement(" SELECT h.coste  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

			preStatement.setInt(1, hotelid);
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				costOfRoom = rs.getInt(1);
			}
		}

		else if (roomtype.equals("presidential")) {
			PreparedStatement preStatement = getConnectionDB()
					.prepareStatement(" SELECT h.costp  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

			preStatement.setInt(1, hotelid);
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				costOfRoom = rs.getInt(1);
			}
		}

		cost = costOfRoom * numberofroom;
		ReservationInfo.setCost(cost);
		getConnectionDB().close();

	}

	private Connection getConnectionDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hotelreservation", "root", "");
		return connection;

	}

	private void createReservation() {

		int idNo = 0;
		// reservation için id no atama
		try {
			PreparedStatement preStatement = getConnectionDB().prepareStatement(" SELECT COUNT(id) FROM reservation");
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				idNo = rs.getInt(1);
			}
			idNo++;

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

		}

		// reservation create
		Reservation res = new Reservation(idNo, User.getUserid(), ReservationInfo.getHotelId(),
				ReservationInfo.getStart(), ReservationInfo.getEnd(), ReservationInfo.getNoRoom(),
				ReservationInfo.getRoomType(), ReservationInfo.getCost());

		String startD = res.getStartdate().getYear() + 1900 + "." + (res.getStartdate().getMonth() + 1) + "."
				+ res.getStartdate().getDate();
		String endD = res.getEnddate().getYear() + 1900 + "." + (res.getEnddate().getMonth() + 1) + "."
				+ res.getEnddate().getDate();
		
	
		// reservation ý database e at
		try {
			String query = "INSERT INTO `hotelreservation`.`reservation` (`id`, `userid`, `hotelid`, `iscancelld`, `startdate`, `enddate`, `numberofroom`, `roomtype`, `cost`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);\r\n"
					+ "";

			PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
			preparedStmt.setInt(1, res.getId());
			preparedStmt.setInt(2, res.getUserid());
			preparedStmt.setInt(3, res.getHotelid());
			preparedStmt.setString(4, res.getIscancelld());
			preparedStmt.setString(5, startD);
			preparedStmt.setString(6, endD);
			preparedStmt.setInt(7, res.getNumberofroom());
			preparedStmt.setString(8, String.valueOf(res.getRoomtype().charAt(0)));
			preparedStmt.setInt(9, res.getCost());
			preparedStmt.execute();

			getConnectionDB().close();

		} catch (Exception ex) {
		}

		reset();

	}

	private void reset() {
		ReservationInfo.setHotelName("");
		ReservationInfo.setStart(new Date());
		ReservationInfo.setEnd(new Date());
		ReservationInfo.setNoRoom(0);
		ReservationInfo.setRoomType("");
		ReservationInfo.setCost(0);
	}

	public void payment() {
		int checkBalance = User.getBalance();
		checkBalance = checkBalance - cost;

		if (checkBalance >= 0) {
			User.setBalance(User.getBalance() - cost);

			String query = "UPDATE `hotelreservation`.`registereduser` SET `balance` = ? WHERE (`rid` = ?);\r\n" + "";
			PreparedStatement preparedStmt;
			try {
				preparedStmt = getConnectionDB().prepareStatement(query);
				preparedStmt.setInt(1, User.getBalance());
				preparedStmt.setInt(2, User.getUserid());
				preparedStmt.executeUpdate();
				createReservation();
				// changeRoomNumber();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			reset();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Reservation failed: insufficient balance"));

		}
	}


	//////////////////////////////////////////////////////////////////////////

	public void onDateChange() {
		data.clear();
		// private Map<String, String> roommap;
		// private Map<Integer, Integer> numbermap;
		ReservationInfo.setStart(startdate);
		ReservationInfo.setEnd(enddate);
	
		roomTypeNo();
		onChange();
	}

	public void onChange() {
		if (roomtype != null && !roomtype.equals(""))
			setNumbermap(data.get(roomtype));
		else
			setNumbermap(new HashMap<>());
	}

	public void roomTypeNo() {

		String startD = ReservationInfo.getStart().getYear() + 1900 + "." + (ReservationInfo.getStart().getMonth() + 1)
				+ "." + ReservationInfo.getStart().getDate();
		String endD = ReservationInfo.getEnd().getYear() + 1900 + "." + (ReservationInfo.getEnd().getMonth() + 1) + "."
				+ ReservationInfo.getEnd().getDate();

		roommap = new HashMap<>();

		Map<Integer, Integer> map = new HashMap<>();

		/////// STANDART
		try {
			// rezervasyonlu oda sayýsý
			int reservedRoomsS = 0;
			PreparedStatement sroomRes = getConnectionDB().prepareStatement(
					"SELECT numberofroom FROM reservation WHERE (`hotelid` = ?) and enddate <= ? and startdate >= ? and iscancelld = '0' and roomtype = 's';");
			sroomRes.setInt(1, this.hotelid);
			sroomRes.setString(3, startD);
			sroomRes.setString(2, endD);
			ResultSet b = sroomRes.executeQuery();
			while (b.next()) {
				reservedRoomsS += b.getInt(1);
			}

			// genel oda sayýsý
			int SnoRooms = 0;
			int Savailable = 0;
			PreparedStatement sroom = getConnectionDB()
					.prepareStatement(" SELECT vrooms FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			sroom.setInt(1, this.hotelid);
			ResultSet rs = sroom.executeQuery();

			while (rs.next()) {
				SnoRooms = rs.getInt(1);
			}
			Savailable = SnoRooms - reservedRoomsS;

			if (Savailable != 0) {
				roommap.put("standart", "standart");
				for (int i = 1; i <= Savailable; i++) {
					map.put(i, i);
				}
			}
			data.put("standart", map);
			getConnectionDB().close();

			/////////// EXECUTIVE
			// rezervasyonlu oda sayýsý
			int reservedRooms = 0;
			PreparedStatement eroomRes = getConnectionDB().prepareStatement(
					"SELECT numberofroom FROM reservation WHERE (`hotelid` = ?) and enddate <= ? and startdate >= ? and iscancelld = '0' and roomtype = 'e';");
			eroomRes.setInt(1, this.hotelid);
			eroomRes.setString(3, startD);
			eroomRes.setString(2, endD);
			ResultSet a = eroomRes.executeQuery();
			while (a.next()) {
				reservedRooms += a.getInt(1);
			}
			
			// genel oda sayýsý
			int noRooms = 0;
			int available = 0;
			PreparedStatement eroom = getConnectionDB()
					.prepareStatement(" SELECT vroome FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			eroom.setInt(1, this.hotelid);
			ResultSet rs1 = eroom.executeQuery();
			map = new HashMap<>();
			while (rs1.next()) {
				noRooms = rs1.getInt(1);
			}
			available = noRooms - reservedRooms;

			if (available != 0) {
				roommap.put("executive", "executive");
				for (int i = 1; i <= available; i++) {
					map.put(i, i);
				}
			}
			data.put("executive", map);
			getConnectionDB().close();

			///////////PRESIDENTAL
			// rezervasyonlu oda sayýsý
						int PreservedRooms = 0;
						PreparedStatement ProomRes = getConnectionDB().prepareStatement(
								"SELECT numberofroom FROM reservation WHERE (`hotelid` = ?) and enddate <= ? and startdate >= ? and iscancelld = '0' and roomtype = 'p';");
						ProomRes.setInt(1, this.hotelid);
						ProomRes.setString(3, startD);
						ProomRes.setString(2, endD);
						ResultSet c = ProomRes.executeQuery();
						while (c.next()) {
							PreservedRooms += c.getInt(1);
						}
			
			//genel oda sayýsý
			int PnoRooms = 0;
			int Pavailable = 0;
			PreparedStatement proom = getConnectionDB()
					.prepareStatement(" SELECT vroomp FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			proom.setInt(1, this.hotelid);
			ResultSet rs2 = proom.executeQuery();

			map = new HashMap<>();
			while (rs2.next()) {
				PnoRooms = rs2.getInt(1);
				
				getConnectionDB().close();
			}
			
			Pavailable = PnoRooms - PreservedRooms;
			
			if (Pavailable != 0) {
					roommap.put("presidential", "presidential");

					for (int i = 1; i <= Pavailable; i++) {
						map.put(i, i);
					}
				}
				data.put("presidential", map);

		} catch (Exception ex) {

		}

	}

	public Map<Integer, Integer> getNumbermap() {
		return numbermap;
	}

	public void setNumbermap(Map<Integer, Integer> numbermap) {
		this.numbermap = numbermap;
	}

	public Map<String, String> getRoommap() {
		return roommap;
	}

	public void setRoommap(Map<String, String> roommap) {
		this.roommap = roommap;
	}

///////////////////////////////////////////////////////////////////////////
	public int getHotelid() {
		return hotelid;
	}

	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
