package reservations;

import java.util.Date;

public abstract class ReservationInfo {
	private static int hotelId;
	private static String hotelName;
	private static int cost;
	private static int noRoom;
	private static String roomType;
	private static Date start = new Date();
	private static Date end = new Date();
	
	
	public static Date getStart() {
		return start;
	}
	public static void setStart(Date start) {
		ReservationInfo.start = start;
	}
	public static Date getEnd() {
		return end;
	}
	public static void setEnd(Date end) {
		ReservationInfo.end = end;
	}
	public static String getRoomType() {
		return roomType;
	}
	public static void setRoomType(String roomType) {
		ReservationInfo.roomType = roomType;
	}
	public static int getNoRoom() {
		return noRoom;
	}
	public static void setNoRoom(int noRoom) {
		ReservationInfo.noRoom = noRoom;
	}
	public static int getCost() {
		return cost;
	}
	public static void setCost(int cost) {
		ReservationInfo.cost = cost;
	}
	public static int getHotelId() {
		return hotelId;
	}
	public static void setHotelId(int hotelId) {
		ReservationInfo.hotelId = hotelId;
	}
	public static String getHotelName() {
		return hotelName;
	}
	public static void setHotelName(String hotelName) {
		ReservationInfo.hotelName = hotelName;
	}
	
	

}
