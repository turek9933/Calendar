package mojPakiet;

import java.time.LocalDateTime;

import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		// get set test
		// Kontakt kontakt1 = new Kontakt(1,"AAA","BBB","123123123");
		// System.out.println(kontakt1.getId()+" AAA");
		// kontakt1.setId(4);
		// System.out.println(kontakt1.getId()+" BBB");
		
		// odczyt danych na poczatku/dodanie hardcode przykladowych danych
		ArrayList<Kontakt> kontakty = new ArrayList<>();
		ArrayList<Zdarzenie> zdarzenia = new ArrayList<>();
		
		KalendarzOperacje.inicjalizujDane(kontakty, zdarzenia);
		
		System.out.println();
		System.out.println("DODANIE KONTAKTU");
		System.out.println();
		KalendarzOperacje.dodajKontakt(kontakty, "Nowy test", "Test ", "501234522");
	
		System.out.println();
		System.out.println("EDYCJA KONTAKTU");
		System.out.println();
		KalendarzOperacje.edytujKontakt(kontakty, zdarzenia, 4, "asd", "ghj", "100100144");

		System.out.println();
		System.out.println("DODANIE ZDARZENIA");
		System.out.println();
		KalendarzOperacje.dodajZdarzenie(zdarzenia, LocalDateTime.of(2027, 2, 13, 17, 21), "Zakupy", "2");
		//KalendarzOperacje.wyswietlZdarzenia(zdarzenia);
		
		System.out.println();
		System.out.println("EDYCJA ZDARZENIA");
		System.out.println();
		KalendarzOperacje.edytujZdarzenie(zdarzenia, 5, LocalDateTime.of(2025, 4, 27, 13, 13), "AAAAssss", "22372");
		
		System.out.println();
		System.out.println("USUWANIE ZDARZENIA");
		System.out.println();
		
		KalendarzOperacje.usunZdarzeniePoId(zdarzenia, 4);

		System.out.println();
		System.out.println("USUWANIE KONTAKTU");
		System.out.println();
		
		KalendarzOperacje.usunKontaktPoId(kontakty, zdarzenia, 13);

		//KalendarzOperacje.wyswietlKontakty(kontakty);
		//KalendarzOperacje.wyswietlZdarzenia(zdarzenia);

		Collections.sort(kontakty, new ComparatorImie());
		System.out.println();
		System.out.println("SORTOWANIE KONTAKTY");
		System.out.println();
		kontakty.sort(null);
		KalendarzOperacje.wyswietlKontakty(kontakty);
		System.out.println();
		kontakty.sort(new ComparatorImie());
		KalendarzOperacje.wyswietlKontakty(kontakty);
		System.out.println();
		System.out.println("SORTOWANIE ZDARZENIA");
		System.out.println();
		zdarzenia.sort(null);
		KalendarzOperacje.wyswietlZdarzenia(zdarzenia);
		System.out.println();
		zdarzenia.sort(new ComparatorNumerTelefonu());
		KalendarzOperacje.wyswietlZdarzenia(zdarzenia);

		XmlMg.zapisDoXml(kontakty, zdarzenia);// zapis do pliku koncowy

			
	}

}
