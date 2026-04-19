package calendarPackage;

import java.util.Comparator;

public class ComparatorId implements Comparator<Event> {

	@Override
	public int compare(Event o1, Event o2) {
		return Integer.compare(o1.getId(), o2.getId());	
	}
	
}
