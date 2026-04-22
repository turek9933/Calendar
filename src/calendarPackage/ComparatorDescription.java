package calendarPackage;

import java.util.Comparator;

public class ComparatorDescription implements Comparator<Event> {

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getDescription().compareTo(o2.getDescription());
	}
}
