package user;

public class User {
	public static int userid;//3
	public static String email;//furki@gmail.com
	public static String typee;//r
	
	 public User(int userid, String email, String typee) {
	    User.userid = userid;
		User.email = email;
		User.typee = typee;
		System.out.println(userid+email+typee);	
	}
	 public static int getUserid() {
		return userid;
	}
	public static void setUserid(int userid) {
		User.userid = userid;
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
