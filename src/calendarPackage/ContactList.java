package calendarPackage;

import java.util.ArrayList;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.InternetAddress;


public class ContactList {
	private ArrayList<Contact> contactList;

	public ContactList() {
		this.contactList = new ArrayList<Contact>();
	}

	public Contact getContact(int id) {
		for (Contact c : this.contactList) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	public ArrayList<Contact> getContacts() {
		return contactList;
	}

	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}

	public void addContact(int id, String name, String surname, Phonenumber.PhoneNumber phoneNumber, InternetAddress mail) {
		for (Contact c : this.contactList) {
			if (c.getPhoneNumber().equals(phoneNumber)) {
				System.out.println("Kontakt o takim nr tel istnieje.");
				System.out.println(c);
				return;
			}
			if (c.getId() == (id)) {
				System.out.println("Kontakt o takim nr ID istnieje.");
				System.out.println(c);
				return;
			}
		}
		contactList.add(new Contact(id, name, surname, phoneNumber, mail));
	}

	public void addContact(String name, String surname, Phonenumber.PhoneNumber phoneNumber, InternetAddress mail) {
		for (Contact c : this.contactList) {
			if (c.getPhoneNumber().equals(phoneNumber)) {
				System.out.println("Kontakt o takim nr tel istnieje.");
				System.out.println(c);
				return;
			}
		}
		
		int newId = 1;
		for (Contact c : this.contactList) {
			if (c.getId() >= newId) {
				newId = c.getId() + 1;
			}
		}
		
		contactList.add(new Contact(newId, name, surname, phoneNumber, mail));
	}
	
	public void clearContacts() {
		this.contactList.clear();
	}

	public void printContacts() {
		for (Contact c : this.contactList) {
			System.out.println(c);
		}
	}

	public void addContactEvent(int contactId, Event event) {
		this.getContact(contactId).addContactEvent(event);
	}

	public void editContact(int id, String newName, String newSurname, Phonenumber.PhoneNumber newPhoneNumber, InternetAddress newMail) {
		for (Contact contact : this.contactList) {
			if (contact.getId() == id) {
				
				String oldName = contact.getName();
				String oldSurname = contact.getSurname();
				Phonenumber.PhoneNumber oldPhoneNumber = contact.getPhoneNumber();
	            InternetAddress oldMail = contact.getMail();

				for (Contact other : this.contactList) {
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

	public void deleteContactById(int id) {
		for (int i = 0; i < this.contactList.size(); i++) {
			if (this.contactList.get(i).getId() == id) {
				this.contactList.remove(i);
				System.out.println("Usunięto kontakt o id: " + id);
				return;
			}
		}
		System.out.println("Brak kontaktu o id: " + id);
	}

	public void addContactEvent(int contactId, EventList eventList, int eventId) {
		Contact contact = this.getContact(contactId);
		Event event = eventList.getEvent(eventId);

		if (contact == null || event == null) {
			return;
		}
		contact.addContactEvent(event);
		event.addEventContact(contact);
	}

	@Override
	public String toString() {
		String contactListString = "";

		for (Contact c : this.contactList) {
			contactListString = contactListString + c + "\n";
		}
		
		return "[ContactList]:\n" + contactListString;
	}
}
