public class Contact implements Comparable<Contact> {
	private String id;
	private String name;
	private int phoneNumber;
	
	public Contact(String name, int phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "[Contact] Name: " + this.name + " Phone number: " + this.phoneNumber;
	}

	@Override
	public int compareTo(Contact anotherContact) {
		return this.name.compareTo(anotherContact.name);
	}
}