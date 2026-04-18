package mojPakiet;

import java.time.LocalDateTime;

public class Zdarzenie implements Comparable<Zdarzenie> {  
	private int id;
	private LocalDateTime data;
	private String opis;
	private String numerTelefonu;
	
	public Zdarzenie(int id, LocalDateTime data, String opis, String numerTelefonu) {
		this.id = id;
		this.data = data;
		this.opis = opis;
		this.numerTelefonu = numerTelefonu;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getNumerTelefonu() {
		return numerTelefonu;
	}

	public void setNumerTelefonu(String kontakt) {
		this.numerTelefonu = kontakt;
	}

	
	@Override
	public String toString() {
		return "Zdarzenie [id=" + id + ", data=" + data + ", opis=" + opis + ", numerTelefonu=" + numerTelefonu + "]";
	}

	@Override
	public int compareTo(Zdarzenie other) {
	    return this.getData().compareTo(other.getData());
	}
}