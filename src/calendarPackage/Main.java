package calendarPackage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.net.URI;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.InternetAddress;

public class Main {

	public static void main(String[] args) throws Exception {
		dataInit();
		ArrayList<Contact> contacts = new ArrayList<>();
		ArrayList<Event> events = new ArrayList<>();
		DbManager.load(contacts, events);

		System.out.println("Dane startowe wczytane z DB:");
		Contact.printContacts(contacts);
		Event.printEvents(events);

		System.out.println();
		System.out.println("Zapis do pliku XML zmodyfikowanych danych");
		XmlManager.save(contacts, events);

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
		System.out.println("Odczyt z pliku XML zmodyfikowanych danych");
		contacts.clear();
		events.clear();
		XmlManager.load(contacts, events);
		Contact.printContacts(contacts);

		System.out.println();
		System.out.println("Dodanie zdarzenia");
		Event.addEvent(events, LocalDateTime.of(2027, 2, 13, 17, 21), "Zakupy", new URI("https://maps.app.goo.gl/2Qy5tNCCtX26HgAt9"));
		Event.printEvents(events);
		
		System.out.println();
		System.out.println("Usuwanie zdarzenia o id 2");
		Event.deleteEventById(events, 2);
		Event.printEvents(events);
		
		System.out.println();
		System.out.println("Edycja zdarzenia o id 3");
		Event.editEvent(events, 3, LocalDateTime.of(2027, 9, 12, 15, 30), "tenis", new URI("https://maps.app.goo.gl/2Qy5tNCCtX26HgAt9"));
		System.out.println();
		Event.printEvents(events);

		System.out.println();
		System.out.println("Zapis do bazy");
		DbManager.save(contacts, events);
		System.out.println("Ukończony zapis do bazy");
	}

	public static void dataInit() throws Exception {
		ArrayList<Contact> contacts = new ArrayList<>();
		ArrayList<Event> events = new ArrayList<>();
		
		contacts.add(new Contact(1, "Anna", "Nowak", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(654564567), new InternetAddress("anna@onet.pl")));
        contacts.add(new Contact(2, "Jan", "Kowalski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(501234567), new InternetAddress("jana@onet.pl")));
        contacts.add(new Contact(3, "Piotr", "Wiśniewski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(503456789), new InternetAddress("piotr@onet.pl")));
        contacts.add(new Contact(4, "Katarzyna", "Kowalczyk", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(512345678), new InternetAddress("katarzyna@onet.pl")));
        contacts.add(new Contact(5, "Michał", "Kamiński", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(523456789), new InternetAddress("michal@onet.pl")));
        contacts.add(new Contact(6, "Agnieszka", "Lewandowska", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(534567890), new InternetAddress("agnieszka@onet.pl")));
        contacts.add(new Contact(7, "Tomasz", "Zieliński", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(545678901), new InternetAddress("tomasz@onet.pl")));
        contacts.add(new Contact(8, "Paweł", "Szymański", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(556789012), new InternetAddress("pawel@onet.pl")));
        contacts.add(new Contact(9, "Magdalena", "Woźniak", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(567890123), new InternetAddress("magdalena@onet.pl")));
        contacts.add(new Contact(10, "Krzysztof", "Dąbrowski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(578901234), new InternetAddress("krzysztof@onet.pl")));

		events.add(new Event(1, LocalDateTime.of(2026, 1, 10, 9, 0), "Spotkanie", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(2, LocalDateTime.of(2026, 1, 11, 10, 30), "Telefon do Anny", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(3, LocalDateTime.of(2026, 1, 12, 12, 0), "Wysyłka", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(4, LocalDateTime.of(2026, 1, 13, 14, 15), "Kino", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(5, LocalDateTime.of(2026, 1, 14, 16, 0), "Omówienie projektu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(6, LocalDateTime.of(2026, 1, 15, 8, 45), "Zapłacić", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(7, LocalDateTime.of(2026, 1, 16, 11, 20), "Rozmowa o prace", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(8, LocalDateTime.of(2026, 1, 17, 13, 10), "Wizyta u lekarza", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(9, LocalDateTime.of(2026, 1, 18, 15, 40), "Terminu projektu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		events.add(new Event(10, LocalDateTime.of(2026, 1, 19, 17, 30), "Odbiór samochodu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA")));
		

		contacts.add(new Contact(11, "Krzysztof Super", "Dąbrowski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(578901234), new InternetAddress("krzysztof@onet.pl"), events));
		contacts.add(new Contact(12, "Andrzej", "Chrząszcz", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(858941234), new InternetAddress("andrzej@onet.pl"),
				new ArrayList<Event>(List.of(events.get(4), events.get(6)))
				));
		contacts.add(new Contact(13, "Michaś", "Lubomir", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(946741234), new InternetAddress("lubomir@wp.pl"),
				new ArrayList<Event>(List.of(events.get(6), events.get(9)))
				));
        
		DbManager.save(contacts, events);
	}
}
