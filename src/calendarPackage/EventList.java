package calendarPackage;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventList {

    private ArrayList<Event> events;

    public EventList() {
        this.events = new ArrayList<Event>();
    }

    public ArrayList<Event> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	public Event getEvent(int id) {
        for (Event e : this.events) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public void addEventContact(int eventId, Contact contact) {
        this.getEvent(eventId).addEventContact(contact);
    }

    public void addEventContact(int eventId, ContactList contactList, int contactId) {
        Event event = this.getEvent(eventId);
        Contact contact = contactList.getContact(contactId);

        if (contact == null || event == null) {
            return;
        }

        event.addEventContact(contact);
        contact.addContactEvent(event);
    }

    public void printEvents() {
        for (Event e : this.events) {
            System.out.println(e);
        }
    }
    
    public void clearEvents( ) {
    	this.events.clear();
    }

    public void addEvent(int id, LocalDateTime date, String description, URI mapUri) {
        for (Event e : this.events) {
            if (e.getId() == id) {
				System.out.println("Zdarzenie o takim nr ID istnieje.");
				System.out.println(e);
				return;
            }
        }

        this.events.add(new Event(id, date, description, mapUri));
    }

    public void addEvent(LocalDateTime date, String description, URI mapUri) {
        int newId = 1;

        for (Event e : this.events) {
            if (e.getId() >= newId) {
                newId = e.getId() + 1;
            }
        }

        this.events.add(new Event(newId, date, description, mapUri));
    }

    public void deleteEventById(int id) {
        for (int i = 0; i < this.events.size(); i++) {
            if (this.events.get(i).getId() == id) {
                this.events.remove(i);
                System.out.println("Usunięto zdarzenie o id: " + id);
                return;
            }
        }

        System.out.println("Brak zdarzenia o id: " + id);
    }

    public void editEvent(int id, LocalDateTime newDate, String newDescription, URI newMapUri) {
        for (Event event : this.events) {
            if (event.getId() == id) {

                LocalDateTime oldDate = event.getDate();
                String oldDescription = event.getDescription();
                URI oldMapUri = event.getMapUri();

                System.out.println("Event before edit:");
                System.out.println("[Event] Id: " + event.getId()
                        + " Date: " + oldDate
                        + " Description: " + oldDescription
                        + " Adres: " + oldMapUri);

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
        String eventListString = "";

        for (Event e : this.events) {
            eventListString = eventListString + e + "\n";
        }

        return "EventList:\n" + eventListString;
    }
}