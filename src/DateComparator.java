import java.util.Comparator;

public class DateComparator implements Comparator<Event> {
	@Override
	public int compare(Event e1, Event e2) {
		return e1.getDate().compareTo(e2.getDate());
	}
}