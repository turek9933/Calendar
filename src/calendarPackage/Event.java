package calendarPackage;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements Comparable<Event> {
	private int id;
	private LocalDateTime date;
	private String description;
	private URI mapUri;
	private ArrayList<Contact> eventContacts = new ArrayList<>();

	public Event(int id, LocalDateTime date, String description, URI mapUri) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.mapUri = mapUri;
	}

	public Event(int id, LocalDateTime date, String description, URI mapUri, ArrayList<Contact> eventContacts) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.mapUri = mapUri;
		this.eventContacts = eventContacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public String getDateString() {
		return date.toString();
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	// Przekształca String na klasę daty - otrzymuje datę bez stref czasowych w formacie "YYYY-MM-DD"
	// The ISO date formatter that formats or parses a date without an offset, such as '2011-12-03'
	public void setDate(String date) {
		this.date = LocalDateTime.parse(date);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URI getMapUri() {
		return mapUri;
	}

	public void setMapUri(URI mapUri) {
		this.mapUri = mapUri;
	}

	public void setMapUri(String mapUriString) {
		try {
			this.mapUri = new URI(mapUriString);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid URI string: " + mapUriString);
		}
	}

	public ArrayList<Contact> getEventContacts() {
		return eventContacts;
	}

	public void setEventContacts(ArrayList<Contact> eventContacts) {
		this.eventContacts = eventContacts;
	}

	public void addEventContact(Contact contact) {
		this.eventContacts.add(contact);
	}

	public static Event getEvent(ArrayList<Event> events, int id) {
		for (Event e : events) {
			if (e.getId() == id) {
				return e;
			}
		}
		return null;
	}

	public static void addEventContact(ArrayList<Event> events, int eventId, Contact contact) {
		Event.getEvent(events, eventId).addEventContact(contact);
	}
	public static void addEventContact(ArrayList<Event> events, int eventId, ArrayList<Contact> contacts, int contactId) {
		Event event = Event.getEvent(events, eventId);
		Contact contact = Contact.getContact(contacts, contactId);

		if (contact == null || event == null) {
			return;
		}
		event.addEventContact(contact);
		contact.addContactEvent(event);
	}

	public static void printEvents(ArrayList<Event> events) {
		for (Event e : events) {
			System.out.println(e);
		}
	}

	public static void addEvent(ArrayList<Event> events, LocalDateTime date, String description, URI mapUri) {
		int newId = 1;
		for (Event e : events) {
			if (e.getId() >= newId) {
				newId = e.getId() + 1;
			}
		}
		events.add(new Event(newId, date, description, mapUri));
	}

	public static void deleteEventById(ArrayList<Event> events, int id) {
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getId() == id) {
				events.remove(i);
				System.out.println("Usunięto zdarzenie o id: " + id);
				return;
			}
		}
		System.out.println("Brak zdarzenia o id: " + id);
	}
	
	public static void editEvent(ArrayList<Event> events, int id, LocalDateTime newDate, String newDescription, URI newMapUri) {
		for (Event event : events) {
			if (event.getId() == id) {

				LocalDateTime oldDate = event.getDate();
				String oldDescription = event.getDescription();
				URI oldMapUri = event.getMapUri();

				System.out.println("Event before edit:");
				System.out.println("[Event] Id: " + event.getId() + " Date: " + oldDate + " Description: " + oldDescription  + " Adres: " + oldMapUri);

				event.setDate(newDate);
				event.setDescription(newDescription);
				event.setMapUri(newMapUri);

				System.out.println("Event after edit:");
				System.out.println(event);
				return;
			}
		}

		System.out.println("Event with id " + id + " not found.");
	}
	
	@Override
	public String toString() {
		return "[Event] Id: " + this.id + " Date: " + this.date + " Description: " + this.description + " Adres: " + this.mapUri;
	}

	@Override
	public int compareTo(Event anotherEvent) {
		return this.date.compareTo(anotherEvent.date);
	}
}