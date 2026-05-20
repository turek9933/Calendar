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

	public void setMapUri(String mapUriString) throws Exception {
        this.mapUri = new URI(mapUriString);
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
	
	@Override
	public String toString() {
		return "[Event] Id: " + this.id + " Date: " + this.date + " Description: " + this.description + " Adres: " + this.mapUri;
	}

	@Override
	public int compareTo(Event anotherEvent) {
		return this.date.compareTo(anotherEvent.date);
	}
}