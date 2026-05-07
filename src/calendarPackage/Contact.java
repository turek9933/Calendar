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
	
	public void setPhoneNumber(String phoneNumberString) {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		try {
			this.phoneNumber = util.parse(phoneNumberString, null);
		} catch (NumberParseException e) {
	    	throw new IllegalArgumentException("Invalid phone number: " + phoneNumberString);
		}
	}

	public InternetAddress getMail() {
	    return mail;
	}

	public void setMail(InternetAddress mail) {
	    this.mail = mail;
	}

	public void setMail(String mailString) {
	    try {
	    	this.mail = new InternetAddress(mailString);
	    } catch (AddressException e) {
	    	throw new IllegalArgumentException("Invalid mail: " + mailString);
	    }
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
	public static void addContactEvent(ArrayList<Contact> contacts, int contactId, Event event) {
		Contact.getContact(contacts, contactId).addContactEvent(event);
	}

	public static void addContactEvent(ArrayList<Contact> contacts, int contactId, ArrayList<Event> events, int eventId) {
		Contact contact = Contact.getContact(contacts, contactId);
		Event event = Event.getEvent(events, eventId);

		if (contact == null || event == null) {
			return;
		}
		contact.addContactEvent(event);
		event.addEventContact(contact);
	}
	
	public static Contact getContact(ArrayList<Contact> contacts, int id) {
		for (Contact c : contacts) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}
	
	public static void printContacts(ArrayList<Contact> contacts) {
		for (Contact c : contacts) {
			System.out.println(c);
		}
	}
	public static void addContact(ArrayList<Contact> contacts, String name, String surname, Phonenumber.PhoneNumber phoneNumber, InternetAddress mail) {
		for (Contact c : contacts) {
			if (c.getPhoneNumber().equals(phoneNumber)) {
				System.out.println("Kontakt o takim nr tel istnieje.");
				System.out.println(c);
				return;
			}
		}
		
		int newId = 1;
		for (Contact c : contacts) {
			if (c.getId() >= newId) {
				newId = c.getId() + 1;
			}
		}
		
		contacts.add(new Contact(newId, name, surname, phoneNumber, mail));
	}

	public static void deleteContactById(ArrayList<Contact> contacts, int id) {
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getId() == id) {
				contacts.remove(i);
				System.out.println("Usunięto kontakt o id: " + id);
				return;
			}
		}
		System.out.println("Brak kontaktu o id: " + id);
	}
	
	public static void editContact(ArrayList<Contact> contacts, int id, String newName, String newSurname, Phonenumber.PhoneNumber newPhoneNumber, InternetAddress newMail) {
		for (Contact contact : contacts) {
			if (contact.getId() == id) {
				
				String oldName = contact.getName();
				String oldSurname = contact.getSurname();
				Phonenumber.PhoneNumber oldPhoneNumber = contact.getPhoneNumber();
	            InternetAddress oldMail = contact.getMail();

				for (Contact other : contacts) {
					if (other.getPhoneNumber().equals(newPhoneNumber) && other.getId() != id) {
						System.out.println("A contact with this phone number already exists.");
						System.out.println("Existing contact:");
						System.out.println(other);
						return;
					}
				}
				
				System.out.println("Contact before edit:");
				System.out.println("[Contact] Id: " + contact.getId() + " Name: " + oldName + " Surname: " + oldSurname + " Phone number: " + oldPhoneNumber + " Mail: " + oldMail);
				
				contact.setName(newName);
				contact.setSurname(newSurname);
				contact.setPhoneNumber(newPhoneNumber);
			    contact.setMail(newMail);

				System.out.println("Contact after edit:");
				System.out.println(contact);
				return;
			}
		}

		System.out.println("Contact with id " + id + " not found.");
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