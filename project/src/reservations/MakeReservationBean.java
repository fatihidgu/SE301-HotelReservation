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
	private final Map<String, Map<Integer, Integer>> data = new HashMap<>();
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
			//roomTypeNo();

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
		String endD = res.getStartdate().getYear() + 1900 + "." + (res.getStartdate().getMonth() + 1) + "."
				+ res.getStartdate().getDate();

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
				changeRoomNumber();
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

	public void changeRoomNumber() {

		try {
			int availableRoomNo = 0;

			// ayýrttýðýn odanýn uygun olanlarýnýn sayýsýný bul
			if (roomtype.equals("standart")) {
				PreparedStatement preStatement = getConnectionDB()
						.prepareStatement(" SELECT h.vrooms  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

				preStatement.setInt(1, ReservationInfo.getHotelId());
				ResultSet rs = preStatement.executeQuery();
				while (rs.next()) {
					availableRoomNo = rs.getInt(1);
				}
			}

			else if (roomtype.equals("executive")) {
				PreparedStatement preStatement = getConnectionDB()
						.prepareStatement(" SELECT h.vroome  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

				preStatement.setInt(1, ReservationInfo.getHotelId());
				ResultSet rs = preStatement.executeQuery();
				while (rs.next()) {
					availableRoomNo = rs.getInt(1);
				}
			}

			else if (roomtype.equals("presidential")) {
				PreparedStatement preStatement = getConnectionDB()
						.prepareStatement(" SELECT h.vroomp  \r\n" + "FROM hotel h\r\n" + "WHERE h.hid= ?   ");

				preStatement.setInt(1, ReservationInfo.getHotelId());
				ResultSet rs = preStatement.executeQuery();
				while (rs.next()) {
					availableRoomNo = rs.getInt(1);
				}
			}

			availableRoomNo = availableRoomNo - ReservationInfo.getNoRoom();

			// kalan oda sayýsý update
			String roomt = "";
			if (ReservationInfo.getRoomType().equals("standart"))

			{
				String query = "UPDATE `hotelreservation`.`hotel` SET `vrooms` = ? WHERE (`hid` = ?);";
				PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				preparedStmt.setInt(1, availableRoomNo);
				preparedStmt.setInt(2, hotelid);
				preparedStmt.executeUpdate();
			}

			else if (ReservationInfo.getRoomType().equals("executive"))

			{
				String query = "UPDATE `hotelreservation`.`hotel` SET `vroome` = ? WHERE (`hid` = ?);";
				PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				preparedStmt.setInt(1, availableRoomNo);
				preparedStmt.setInt(2, hotelid);
				preparedStmt.executeUpdate();
			}

			else if (ReservationInfo.getRoomType().equals("presidential"))

			{
				String query = "UPDATE `hotelreservation`.`hotel` SET `vroomp` = ? WHERE (`hid` = ?);";
				PreparedStatement preparedStmt = getConnectionDB().prepareStatement(query);
				preparedStmt.setInt(1, availableRoomNo);
				preparedStmt.setInt(2, hotelid);
				preparedStmt.executeUpdate();
			}

			getConnectionDB().close();

		} catch (Exception e) {
		}

	}

	//////////////////////////////////////////////////////////////////////////
	

	public void onDateChange() {
	roomTypeNo();
	System.out.println("on date changý no");
	}
	
	public void onChange() {
		if (roomtype != null && !roomtype.equals(""))
			setNumbermap(data.get(roomtype));
		else
			setNumbermap(new HashMap<>());
	}

	public void roomTypeNo() {
		System.out.println("roomtyoeno()");

		roommap = new HashMap<>();

		Map<Integer, Integer> map = new HashMap<>();

		try {
			PreparedStatement sroom = getConnectionDB()
					.prepareStatement(" SELECT vrooms FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			sroom.setInt(1, this.hotelid);
			ResultSet rs = sroom.executeQuery();

			while (rs.next()) {

				int x = rs.getInt(1);
				if (x != 0) {
					roommap.put("standart", "standart");
					for (int i = 1; i <= x; i++) {
						map.put(i, i);
					}
				}
				data.put("standart", map);
			}
			getConnectionDB().close();
			////////
			PreparedStatement eroom = getConnectionDB()
					.prepareStatement(" SELECT vroome FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			eroom.setInt(1, this.hotelid);
			ResultSet rs1 = eroom.executeQuery();
			map = new HashMap<>();
			while (rs1.next()) {

				int x1 = rs1.getInt(1);
				if (x1 != 0) {
					roommap.put("executive", "executive");
					for (int i = 1; i <= x1; i++) {
						map.put(i, i);
					}
				}
				data.put("executive", map);
			}
			getConnectionDB().close();

			//////
			PreparedStatement proom = getConnectionDB()
					.prepareStatement(" SELECT vroomp FROM hotelreservation.hotel\r\n" + "WHERE (`hid` = ?);");
			proom.setInt(1, this.hotelid);
			ResultSet rs2 = proom.executeQuery();

			map = new HashMap<>();
			while (rs2.next()) {
				int x2 = rs2.getInt(1);
				if (x2 != 0) {
					roommap.put("presidential", "presidential");

					for (int i = 1; i <= x2; i++) {
						map.put(i, i);
					}
				}
				data.put("presidential", map);
				getConnectionDB().close();
			}

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
