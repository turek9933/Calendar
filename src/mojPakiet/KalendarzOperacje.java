package mojPakiet;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class KalendarzOperacje {

	public static void inicjalizujDane(ArrayList<Kontakt> kontakty, ArrayList<Zdarzenie> zdarzenia) throws Exception {
		File plik = new File("kalendarz.xml");

		if (plik.exists()) {
			System.out.println("Znaleziono plik kalendarz.xml - wczytywanie danych z XML");

			kontakty.addAll(XmlMg.odczytajKontaktyXml("kalendarz.xml"));
			zdarzenia.addAll(XmlMg.odczytajZdarzeniaXml("kalendarz.xml"));

		} else {
			System.out.println("Brak pliku kalendarz.xml - tworzenie danych startowych");
			dodajDaneStartowe(kontakty, zdarzenia);
		}
	}

	public static void dodajDaneStartowe(ArrayList<Kontakt> kontakty, ArrayList<Zdarzenie> zdarzenia) {
		kontakty.add(new Kontakt(1, "Jan", "Kowalski", "501234567"));
		kontakty.add(new Kontakt(2, "Anna", "Nowak", "502345678"));
		kontakty.add(new Kontakt(3, "Piotr", "Wiśniewski", "503456789"));
		kontakty.add(new Kontakt(4, "Maria", "Wójcik", "504567890"));
		kontakty.add(new Kontakt(5, "Tomasz", "Kowalczyk", "505678901"));
		kontakty.add(new Kontakt(6, "Magdalena", "Kamińska", "506789012"));
		kontakty.add(new Kontakt(7, "Paweł", "Lewandowski", "507890123"));
		kontakty.add(new Kontakt(8, "Agnieszka", "Zielińska", "508901234"));
		kontakty.add(new Kontakt(9, "Michał", "Szymański", "509012345"));
		kontakty.add(new Kontakt(10, "Ewa", "Woźniak", "500123456"));

		zdarzenia.add(new Zdarzenie(1, LocalDateTime.of(2026, 1, 10, 9, 0), "Spotkanie", "501234567"));
		zdarzenia.add(new Zdarzenie(2, LocalDateTime.of(2026, 1, 11, 10, 30), "Telefon do Anny", "502345678"));
		zdarzenia.add(new Zdarzenie(3, LocalDateTime.of(2026, 1, 12, 12, 0), "Wysyłka", "503456789"));
		zdarzenia.add(new Zdarzenie(4, LocalDateTime.of(2026, 1, 13, 14, 15), "Kino", "504567890"));
		zdarzenia.add(new Zdarzenie(5, LocalDateTime.of(2026, 1, 14, 16, 0), "Omówienie projektu", "505678901"));
		zdarzenia.add(new Zdarzenie(6, LocalDateTime.of(2026, 1, 15, 8, 45), "Zapłacić", "506789012"));
		zdarzenia.add(new Zdarzenie(7, LocalDateTime.of(2026, 1, 16, 11, 20), "Rozmowa o prace", "507890123"));
		zdarzenia.add(new Zdarzenie(8, LocalDateTime.of(2026, 1, 17, 13, 10), "Wizyta u lekarza", "508901234"));
		zdarzenia.add(new Zdarzenie(9, LocalDateTime.of(2026, 1, 18, 15, 40), "Terminu projektu", "509012345"));
		zdarzenia.add(new Zdarzenie(10, LocalDateTime.of(2026, 1, 19, 17, 30), "Odbiór samochodu", "500123456"));
	}

	public static void edytujKontakt(ArrayList<Kontakt> kontakty, ArrayList<Zdarzenie> zdarzenia, int id, String noweImie, String noweNazwisko, String nowyTelefon) {
		for (Kontakt k : kontakty) {
			if (k.getId() == id) {

				String stareImie = k.getImie();
				String stareNazwisko = k.getNazwisko();
				String staryTelefon = k.getNumerTelefonu();

				// sprawdzenie czy nowy numer nie należy już do innego kontaktu
				for (Kontakt inny : kontakty) {
					if (inny.getNumerTelefonu().equals(nowyTelefon) && inny.getId() != id) {
						System.out.println("Kontakt o takim nr tel już istnieje.");
						System.out.println("Istniejący kontakt:");
						System.out.println("id=" + inny.getId() + ", imie=" + inny.getImie() + ", nazwisko="
								+ inny.getNazwisko() + ", numerTelefonu=" + inny.getNumerTelefonu());
						return;
					}
				}

				System.out.println("Kontakt przed edycją:");
				System.out.println("id=" + k.getId() + ", imie=" + stareImie + ", nazwisko=" + stareNazwisko
						+ ", numerTelefonu=" + staryTelefon);

				// aktualizacja kontaktu
				k.setImie(noweImie);
				k.setNazwisko(noweNazwisko);
				k.setNumerTelefonu(nowyTelefon);

				// aktualizacja numeru w zdarzeniach
				for (Zdarzenie z : zdarzenia) {
					if (z.getNumerTelefonu().equals(staryTelefon)) {
						z.setNumerTelefonu(nowyTelefon);
					}
				}

				System.out.println("Kontakt po edycji:");
				System.out.println("id=" + k.getId() + ", imie=" + k.getImie() + ", nazwisko=" + k.getNazwisko()
						+ ", numerTelefonu=" + k.getNumerTelefonu());

				return;
			}
		}

		System.out.println("Nie znaleziono kontaktu o id: " + id);
	}

	public static void edytujZdarzenie(ArrayList<Zdarzenie> zdarzenia, int id, LocalDateTime nowaData, String nowyOpis, String nowyTelefon) {

		for (Zdarzenie z : zdarzenia) {
			if (z.getId() == id) {

				// stary stan
				LocalDateTime staraData = z.getData();
				String staryOpis = z.getOpis();
				String staryTelefon = z.getNumerTelefonu();

				System.out.println("Zdarzenie przed edycją:");
				System.out.println("id=" + z.getId() + ", data=" + staraData + ", opis=" + staryOpis
						+ ", numerTelefonu=" + staryTelefon);

				// update
				z.setData(nowaData);
				z.setOpis(nowyOpis);
				z.setNumerTelefonu(nowyTelefon);

				System.out.println("Zdarzenie po edycji:");
				System.out.println("id=" + z.getId() + ", data=" + z.getData() + ", opis=" + z.getOpis()
						+ ", numerTelefonu=" + z.getNumerTelefonu());

				return;
			}
		}

		System.out.println("Nie znaleziono zdarzenia o id: " + id);
	}

	public static void dodajKontakt(ArrayList<Kontakt> kontakty, String imie, String nazwisko, String numerTelefonu) {
		for (Kontakt k : kontakty) {
			if (k.getNumerTelefonu().equals(numerTelefonu)) {
				System.out.println("Kontakt o takim nr tel istnieje.");
				System.out.println(k);
				return;
			}
		}

		int noweId = 1;
		for (Kontakt k : kontakty) {
			if (k.getId() >= noweId) {
				noweId = k.getId() + 1;
			}
		}

		Kontakt nowyKontakt = new Kontakt(noweId, imie, nazwisko, numerTelefonu);
		kontakty.add(nowyKontakt);

		System.out.println("Dodano kontakt:");
		System.out.println(nowyKontakt);
	}

	public static void dodajZdarzenie(ArrayList<Zdarzenie> zdarzenia, LocalDateTime data, String opis, String numerTelefonu) {
	    int noweId = 1;

	    for (Zdarzenie z : zdarzenia) {
	        if (z.getId() >= noweId) {
	            noweId = z.getId() + 1;
	        }
	    }

	    Zdarzenie noweZdarzenie = new Zdarzenie(noweId, data, opis, numerTelefonu);
	    zdarzenia.add(noweZdarzenie);

	    System.out.println("Dodano zdarzenie:");
	    System.out.println(noweZdarzenie);
	}

	public static void wyswietlKontakty(ArrayList<Kontakt> kontakty) {
		for (Kontakt k : kontakty) {
			System.out.println(k);
		}
	}

	public static void wyswietlZdarzenia(ArrayList<Zdarzenie> zdarzenia) {
		for (Zdarzenie z : zdarzenia) {
			System.out.println(z);
		}
	}

	public static void usunKontaktPoId(ArrayList<Kontakt> kontakty, ArrayList<Zdarzenie> zdarzenia, int id) {
		for (int i = 0; i < kontakty.size(); i++) {
			Kontakt k = kontakty.get(i);

			if (k.getId() == id) {
				String numerKontaktu = k.getNumerTelefonu();

				for (Zdarzenie z : zdarzenia) {
					if (z.getNumerTelefonu().equals(numerKontaktu)) {
						System.out.println(
								"Nie można usunąć kontaktu o id: " + id + ", bo jest powiązany ze zdarzeniem.");
						return;
					}
				}

				kontakty.remove(i);
				System.out.println("Usunięto kontakt o id: " + id);
				return;
			}
		}

		System.out.println("Nie znaleziono kontaktu o id: " + id);
	}

	public static void usunZdarzeniePoId(ArrayList<Zdarzenie> zdarzenia, int id) {
		for (int i = 0; i < zdarzenia.size(); i++) {
			if (zdarzenia.get(i).getId() == id) {
				zdarzenia.remove(i);
				System.out.println("Usunięto zdarzenie o id: " + id);
				return;
			}
		}
		System.out.println("brak zdarzenia o id: " + id);
	}
}
