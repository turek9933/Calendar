package mojPakiet;

public class Kontakt implements Comparable<Kontakt> {
	private int id;
	private String imie;
	private String nazwisko;
	private String numerTelefonu;

	public Kontakt(int id, String imie, String nazwisko, String numerTelefonu) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.numerTelefonu = numerTelefonu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNumerTelefonu() {
		return numerTelefonu;
	}

	public void setNumerTelefonu(String numerTelefonu) {
		this.numerTelefonu = numerTelefonu;
	}

	@Override
	public String toString() {
		return "Kontakt [id=" + id + ", imie=" + imie + ", nazwisko=" + nazwisko + ", numerTelefonu=" + numerTelefonu
				+ "]";
	}

	@Override
	public int compareTo(Kontakt other) {
		return this.getNazwisko().compareTo(other.getNazwisko());
	}

}
