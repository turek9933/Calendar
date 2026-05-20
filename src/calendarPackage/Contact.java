package calendarPackage;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.ArrayList;

public class Contact implements Comparable<Contact> {
	private int id;
	private String name;
	private String surname;
	private Phonenumber.PhoneNumber phoneNumber;
	private InternetAddress mail;
	private ArrayList<Event> contactEvents = new ArrayList<>();
	
	public Contact(int id, String name, String surname, Phonenumber.PhoneNumber phoneNumber, InternetAddress mail) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.mail = mail;
	}

	public Contact(int id, String name, String surname, Phonenumber.PhoneNumber phoneNumber, InternetAddress mail, ArrayList<Event> contactEvents) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.mail = mail;
		this.contactEvents = contactEvents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Phonenumber.PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Phonenumber.PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumberString) throws NumberParseException {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		this.phoneNumber = util.parse(phoneNumberString, null);
	}

	public InternetAddress getMail() {
	    return mail;
	}

	public void setMail(InternetAddress mail) {
	    this.mail = mail;
	}

	public void setMail(String mailString) throws AddressException {
		this.mail = new InternetAddress(mailString);
	}

	public ArrayList<Event> getContactEvents() {
		return contactEvents;
	}

	public void setContactEvents(ArrayList<Event> contactEvents) {
		this.contactEvents = contactEvents;
	}

	public void addContactEvent(Event event) {
		this.contactEvents.add(event);
	}
	
	@Override
	public String toString() {
		return "[Contact] Id: " + this.id + " Name: " + this.name + " Surname: " + this.surname + " Phone number: " + this.phoneNumber + " Mail: " + this.mail;
	}

	@Override
	public int compareTo(Contact anotherContact) {
		return this.surname.compareTo(anotherContact.surname);
	}
}