package Model;

public class Student {
	private String studentId;
	private String name;
	private int age;
	private String phone;
	private String address;
	private String party;
	private String image;

	public Student(String studentId, String name, int age, String phone, String address, String party, String image) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.party = party;
		this.image = image;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
