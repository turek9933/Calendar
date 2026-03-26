import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.time.LocalDate;

public class Main {
	// <T> Generetywny obiekt
	static <T> void printArrayList(ArrayList<T> arr) {
		for (int i = 0; i < arr.size(); i++)
			System.out.println(arr.get(i));
	}

	public static void main(String[] args) {
		System.out.println("--------------KONTAKTY----------------");
		ArrayList<Contact> contacts = new ArrayList<>();

		contacts.add(new Contact("Ela", 4685486));
		contacts.add(new Contact("Ala", 142422));
		contacts.add(new Contact("Ola", 24124));
		contacts.add(new Contact("Kasia", 544566));
		contacts.add(new Contact("Joanna", 4565463));
		contacts.add(new Contact("Klaudia", 0));
		contacts.add(new Contact("Monika", 420666));
		contacts.add(new Contact("Barbara", 24124));
		contacts.add(new Contact("Zuzanna", 421373));
		contacts.add(new Contact("Sasha", 748596231));
		
		printArrayList(contacts);
		
		
		System.out.println("---Porównanie 2 pierwszych elementów---");
		System.out.println(contacts.get(0).compareTo(contacts.get(1)));
		
		
		System.out.println("---Sortowanie domyślne dla klasy---");
		Collections.sort(contacts);
		printArrayList(contacts);

		
		System.out.println("---Sortowanie kontaktów po nr telefonów---");
		Comparator phoneNumberComparator = new PhoneNumberComperator();
		Collections.sort(contacts, phoneNumberComparator);
		printArrayList(contacts);

		
		
		System.out.println("-------------ZDARZENIA-----------------");
		ArrayList<Event> events = new ArrayList<>();
		
		events.add(new Event(LocalDate.parse("2026-03-14"), "Zdarzenie 1"));
		events.add(new Event(LocalDate.parse("2010-04-01"), "wydarzenie 3"));
		events.add(new Event(LocalDate.parse("2022-03-10"), "Opis 136"));
		events.add(new Event(LocalDate.parse("2010-05-04"), "wydarzenie 2"));
		events.add(new Event(LocalDate.parse("2003-03-10"), "Opis 134"));
		events.add(new Event(LocalDate.of(2012, 4, 1), "opisik 2"));
		events.add(new Event(LocalDate.of(2002, 03, 11), "testowanko 134"));
		events.add(new Event(LocalDate.of(2002, 11, 16), "Litwo ojczyzno moja"));
		events.add(new Event(LocalDate.of(2007, 3, 22), "Ile cię trzeba kochać"));
		events.add(new Event(LocalDate.of(2000, 1, 1), "Nikt się nie dowie*"));

		printArrayList(events);

		
		System.out.println("---Sortowanie zdarzeń po opisach---");		
		Collections.sort(events, new DescriptionComparator());
		
		printArrayList(events);
		

		System.out.println("---Sortowanie zdarzeń po datach---");		
		Comparator dateComparator = new DateComparator();
		Collections.sort(events, dateComparator);
		
		printArrayList(events);
		
		
		
		System.out.println("--------------OBSŁUGA PLIKU XML--------------");
		try {
			System.out.println("---Zapis...---");
			XMLManager.save(contacts, events, "test_file.xml");
			System.out.println("---Zapis się powiódł---");
		} catch (Exception e) {
			System.out.println("Something horrible has happend while WRITING into the file! D8");
			System.out.println(e);
		}
		System.out.println("---Odczyt---");
		try {
			ArrayList<Contact> testContacts = new ArrayList<>();
			ArrayList<Event> testEvents = new ArrayList<>();
			XMLManager.load(testContacts, testEvents, "test_file.xml");
			printArrayList(testContacts);
			printArrayList(testEvents);
		} catch (Exception e) {
			System.out.println("Something horrible has happend while READING from the file! D8");
			System.out.println(e);
		}
	}
}
