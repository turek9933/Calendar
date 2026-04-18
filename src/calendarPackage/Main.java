package calendarPackage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

	public static void main(String[] args) throws Exception {
		dataInit();
		ArrayList<Contact> contacts = new ArrayList<>();
		ArrayList<Event> events = new ArrayList<>();
		XmlManager.load(contacts, events, "calendar.xml");

		System.out.println("Dane startowe:");
		Contact.printContacts(contacts);
		Event.printEvents(events);

		System.out.println();
		System.out.println("Porównanie pierwszych dwóch kontaktów:");
		System.out.println(contacts.get(0).compareTo(contacts.get(1)));

		System.out.println();
		System.out.println("Sortowanie domyślne dla kontaktów");
		Collections.sort(contacts);
		Contact.printContacts(contacts);

		System.out.println();
		System.out.println("Sortowanie kontaktów po numerach telefonów");
		Comparator phoneNumberComparator = new ComparatorPhoneNumber();
		Collections.sort(contacts, phoneNumberComparator);
		Contact.printContacts(contacts);
		

		System.out.println();
		System.out.println("Dodanie zdarzenia");
		Event.addEvent(events, LocalDateTime.of(2027, 2, 13, 17, 21), "Zakupy");
		Event.printEvents(events);
		
		System.out.println();
		System.out.println("Usuwanie zdarzenia o id 2");
		Event.deleteEventById(events, 2);
		Event.printEvents(events);
		
		System.out.println();
		System.out.println("Zapis do pliku zmodyfikowanych danych");
		XmlManager.save(contacts, events);
	}

	public static void dataInit() throws Exception {
		File file = new File("calendar.xml");
		
		if (!file.exists()) {
			ArrayList<Contact> contacts = new ArrayList<>();
			ArrayList<Event> events = new ArrayList<>();

			contacts.add(new Contact(1, "Anna", "Nowak", "502345678"));
			contacts.add(new Contact(2, "Jan", "Kowalski", "501234567"));
			contacts.add(new Contact(3, "Piotr", "Wiśniewski", "503456789"));
			contacts.add(new Contact(4, "Maria", "Wójcik", "504567890"));
			contacts.add(new Contact(5, "Tomasz", "Kowalczyk", "505678901"));
			contacts.add(new Contact(6, "Magdalena", "Kamińska", "506789012"));
			contacts.add(new Contact(7, "Paweł", "Lewandowski", "507890123"));
			contacts.add(new Contact(8, "Agnieszka", "Zielińska", "508901234"));
			contacts.add(new Contact(9, "Michał", "Szymański", "509012345"));
			contacts.add(new Contact(10, "Ewa", "Woźniak", "500123456"));

			events.add(new Event(1, LocalDateTime.of(2026, 1, 10, 9, 0), "Spotkanie"));
			events.add(new Event(2, LocalDateTime.of(2026, 1, 11, 10, 30), "Telefon do Anny"));
			events.add(new Event(3, LocalDateTime.of(2026, 1, 12, 12, 0), "Wysyłka"));
			events.add(new Event(4, LocalDateTime.of(2026, 1, 13, 14, 15), "Kino"));
			events.add(new Event(5, LocalDateTime.of(2026, 1, 14, 16, 0), "Omówienie projektu"));
			events.add(new Event(6, LocalDateTime.of(2026, 1, 15, 8, 45), "Zapłacić"));
			events.add(new Event(7, LocalDateTime.of(2026, 1, 16, 11, 20), "Rozmowa o prace"));
			events.add(new Event(8, LocalDateTime.of(2026, 1, 17, 13, 10), "Wizyta u lekarza"));
			events.add(new Event(9, LocalDateTime.of(2026, 1, 18, 15, 40), "Terminu projektu"));
			events.add(new Event(10, LocalDateTime.of(2026, 1, 19, 17, 30), "Odbiór samochodu"));
			
			XmlManager.save(contacts, events);
		}
	}
}
