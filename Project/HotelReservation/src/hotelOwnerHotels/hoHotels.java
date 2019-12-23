package hotelOwnerHotels;

public class hoHotels {
	private int id;
	private String name;
	private String location;
	private int quality;
	private int costs;
	private int vrooms;
	private int coste;
	private int vroome;
	private int costp;
	private int vroomp;
	private char isactive;
	private char isaccepted;
	private int oldid;
	private String hemail;
	
	public hoHotels(int id, String name, String location, int quality, int costs, int vrooms, int coste, int vroome, int costp, int vroomp, char isactive, char isaccepted) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.quality = quality;
		this.costs = costs;
		this.vrooms = vrooms;
		this.coste = coste;
		this.vroome = vroome;
		this.costp = costp;
		this.vroomp = vroomp;
		this.isactive = isactive;
		this.isaccepted = isaccepted;
	}
	public hoHotels(String name, String location,String email, int oldid) {
		//admin özel
		this.name = name;
		this.location = location;
		this.oldid=oldid;
		this.hemail=email;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public int getQuality() {
		return quality;
	}
	
	public int getCosts() {
		return costs;
	}
	
	public int getVrooms() {
		return vrooms;
	}
	
	public int getCoste() {
		return coste;
	}
	
	public int getVroome() {
		return vroome;
	}
	
	public int getCostp() {
		return costp;
	}
	
	public int getVroomp() {
		return vroomp;
	}
	
	public char getIsactive() {
		return isactive;
	}
	
	public char getIsaccepted() {
		return isaccepted;
	}
	public int getOldid() {
		return oldid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public void setCosts(int costs) {
		this.costs = costs;
	}
	public void setVrooms(int vrooms) {
		this.vrooms = vrooms;
	}
	public void setCoste(int coste) {
		this.coste = coste;
	}
	public void setVroome(int vroome) {
		this.vroome = vroome;
	}
	public void setCostp(int costp) {
		this.costp = costp;
	}
	public void setVroomp(int vroomp) {
		this.vroomp = vroomp;
	}
	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}
	public void setIsaccepted(char isaccepted) {
		this.isaccepted = isaccepted;
	}
	public void setOldid(int oldid) {
		this.oldid = oldid;
	}
	public String getHemail() {
		return hemail;
	}
	public void setHemail(String hemail) {
		this.hemail = hemail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}