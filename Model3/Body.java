package Model;

public class Body {
	private String studentId;
	private String name;
	private int age;
	private double height;
	private double weight;
	private double muscle;
	private double fat;
	private String measureDate;

	public Body(String studentId, String name, int age, double height, double weight, double muscle, double fat,
			String measureDate) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.muscle = muscle;
		this.fat = fat;
		this.measureDate = measureDate;
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getMuscle() {
		return muscle;
	}

	public void setMuscle(double muscle) {
		this.muscle = muscle;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public String getMeasureDate() {
		return measureDate;
	}

	public void setMeasureDate(String measureDate) {
		this.measureDate = measureDate;
	}

}
