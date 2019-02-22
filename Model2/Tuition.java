package Model;

public class Tuition {
	private String studentId;
	private String name;
	private String party;
	private String individual;
	private String opus;
	private int pprice;
	private int iprice;
	private int oprice;
	private int total;
	private String payDate;

	public Tuition(String studentId, String name, String party, String individual, String opus, int pprice, int iprice,
			int oprice, int total, String payDate) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.party = party;
		this.individual = individual;
		this.opus = opus;
		this.pprice = pprice;
		this.iprice = iprice;
		this.oprice = oprice;
		this.total = total;
		this.payDate = payDate;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public String getOpus() {
		return opus;
	}

	public void setOpus(String opus) {
		this.opus = opus;
	}

	public int getPprice() {
		return pprice;
	}

	public void setPprice(int pprice) {
		this.pprice = pprice;
	}

	public int getIprice() {
		return iprice;
	}

	public void setIprice(int iprice) {
		this.iprice = iprice;
	}

	public int getOprice() {
		return oprice;
	}

	public void setOprice(int oprice) {
		this.oprice = oprice;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

}
