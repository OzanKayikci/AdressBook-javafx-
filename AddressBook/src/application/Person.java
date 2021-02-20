package application;

public class Person {

	private int Id;
	private String Name;
	private String street;
	private String city;
	private String zip;
	private String gender;
	
	public Person(int id, String name, String street, String city, String zip, String gender) {
		super();
		Id = id;
		Name = name;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.gender = gender;
	}
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getstreet() {
		return street;
	}
	public void setstreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}


	@Override
	public String toString() {
		return  getId() + " " + getName() + " " + getstreet()
				+ " " + getCity() + " " + getZip() + " " + getGender();
	}



	
	
}
