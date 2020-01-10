package user;

public abstract class User {
	private static int userid;// = 3;
	private static String email; //= "furki@gmail.com";
	private static String typee;
	private static int balance;
<<<<<<< HEAD
	private static String name;
	
	public static void setName(String name) {
		User.name = name;
	}
	public static String getName() {
		return name;
	}
=======
	
>>>>>>> master
	 public static int getUserid() {
		return userid;
	}
	public static void setUserid(int userid) {
		User.userid = userid;
	}
	public static int getBalance() {
		return balance;
	}
	public static void setBalance(int balance) {
		User.balance = balance;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		User.email = email;
	}
	public static String getTypee() {
		return typee;
	}
	public static void setTypee(String typee) {
		User.typee = typee;
	}
	@Override
	public String toString() {
		String user=this.userid+","+this.email+","+"this.typee";
		return user;
	}

}
