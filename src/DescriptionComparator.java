import java.util.Comparator;

public class DescriptionComparator implements Comparator<Event> {
	@Override
	public int compare(Event e1, Event e2) {
		return e1.getDescription().compareTo(e2.getDescription());
	}
}
