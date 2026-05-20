package calendarPackage;

import jakarta.mail.internet.InternetAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.InternetAddress;

public class Main {

	public static void main(String[] args) throws Exception {
		dataInit();
		ContactList contactList = new ContactList();
		EventList eventList = new EventList();
		DbManager.load(contactList, eventList);

		System.out.println("Dane startowe wczytane z DB:");
		System.out.println(contactList);
		System.out.println(eventList);

		System.out.println();
		System.out.println("Zapis do pliku XML zmodyfikowanych danych");
		XmlManager.save(contactList, eventList);

		System.out.println();
		System.out.println("Odczyt z pliku XML zmodyfikowanych danych");
		contactList.clearContacts();
		eventList.clearEvents();
		XmlManager.load(contactList, eventList);
		System.out.println(contactList);

		System.out.println();
		System.out.println("Dodanie zdarzenia");
		eventList.addEvent(LocalDateTime.of(2027, 2, 13, 17, 21), "Zakupy", new URI("https://maps.app.goo.gl/2Qy5tNCCtX26HgAt9"));
		System.out.println(eventList);
		
		System.out.println();
		System.out.println("Usuwanie zdarzenia o id 2");
		eventList.deleteEventById(2);
		System.out.println(eventList);
		
		System.out.println();
		System.out.println("Edycja zdarzenia o id 3");
		eventList.editEvent(3, LocalDateTime.of(2027, 9, 12, 15, 30), "tenis", new URI("https://maps.app.goo.gl/2Qy5tNCCtX26HgAt9"));
		System.out.println();
		System.out.println(eventList);
		
		System.out.println();
		System.out.println("Zapis do bazy");
		DbManager.save(contactList, eventList);
		System.out.println("Ukończony zapis do bazy");
	}

	public static void dataInit() throws Exception {
		ContactList contacts = new ContactList();
		EventList events = new EventList();
		
		contacts.addContact(1, "Anna", "Nowak", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(654564567), new InternetAddress("anna@onet.pl"));
        contacts.addContact(2, "Jan", "Kowalski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(501234567), new InternetAddress("jana@onet.pl"));
        contacts.addContact(3, "Piotr", "Wiśniewski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(503456789), new InternetAddress("piotr@onet.pl"));
        contacts.addContact(4, "Katarzyna", "Kowalczyk", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(512345678), new InternetAddress("katarzyna@onet.pl"));
        contacts.addContact(5, "Michał", "Kamiński", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(523456789), new InternetAddress("michal@onet.pl"));
        contacts.addContact(6, "Agnieszka", "Lewandowska", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(534567890), new InternetAddress("agnieszka@onet.pl"));
        contacts.addContact(7, "Tomasz", "Zieliński", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(545678901), new InternetAddress("tomasz@onet.pl"));
        contacts.addContact(8, "Paweł", "Szymański", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(556789012), new InternetAddress("pawel@onet.pl"));
        contacts.addContact(9, "Magdalena", "Woźniak", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(567890123), new InternetAddress("magdalena@onet.pl"));
        contacts.addContact(10, "Krzysztof", "Dąbrowski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(578901234), new InternetAddress("krzysztof@onet.pl"));

		events.addEvent(1, LocalDateTime.of(2026, 1, 10, 9, 0), "Spotkanie", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(2, LocalDateTime.of(2026, 1, 11, 10, 30), "Telefon do Anny", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(3, LocalDateTime.of(2026, 1, 12, 12, 0), "Wysyłka", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(4, LocalDateTime.of(2026, 1, 13, 14, 15), "Kino", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(5, LocalDateTime.of(2026, 1, 14, 16, 0), "Omówienie projektu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(6, LocalDateTime.of(2026, 1, 15, 8, 45), "Zapłacić", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(7, LocalDateTime.of(2026, 1, 16, 11, 20), "Rozmowa o prace", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(8, LocalDateTime.of(2026, 1, 17, 13, 10), "Wizyta u lekarza", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(9, LocalDateTime.of(2026, 1, 18, 15, 40), "Terminu projektu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));
		events.addEvent(10, LocalDateTime.of(2026, 1, 19, 17, 30), "Odbiór samochodu", new URI("https://maps.app.goo.gl/a9xJUn3aTCgQWSyFA"));

		contacts.addContact(11, "Krzysztof Super", "Dąbrowski", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(578001234), new InternetAddress("krzysztof@onet.pl"));
		contacts.addContactEvent(11, events.getEvents().getFirst());
		contacts.addContactEvent(11, events.getEvents().getLast());

		contacts.addContact(12, "Andrzej", "Chrząszcz", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(858941234), new InternetAddress("andrzej@onet.pl"));
		contacts.addContactEvent(12, events.getEvents().get(4));
		contacts.addContactEvent(12, events.getEvents().get(6));
		
		contacts.addContact(13, "Michaś", "Lubomir", new Phonenumber.PhoneNumber().setCountryCode(48).setNationalNumber(946741234), new InternetAddress("lubomir@wp.pl"));
		contacts.addContactEvent(13, events.getEvents().get(9));
		contacts.addContactEvent(13, events.getEvents().get(6));
        
		DbManager.save(contacts, events);
	}
}
