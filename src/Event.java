import java.time.LocalDate;

public class Event {
	private LocalDate date;
	private String description;
	private String[] members;
	
	public Event(LocalDate date, String description) {
		this.setDate(date);
		this.setDescription(description);
	}

	public LocalDate getDate() {
		return date;
	}
	
	public String getDateString() {
		return date.toString();
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	// Przekształca String na klasę daty - otrzymuje datę bez stref czasowych w formacie "YYYY-MM-DD"
	// The ISO date formatter that formats or parses a date without an offset, such as '2011-12-03'
	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "[Event] Date: " + this.date + " Description: " + this.description;
	}

//	@Override
	public int compareTo(Event anotherEvent) {
		return this.date.compareTo(anotherEvent.date);
	}
}