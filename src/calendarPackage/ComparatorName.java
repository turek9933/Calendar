package calendarPackage;

import java.util.Comparator;

public class ComparatorName implements Comparator<Contact> {

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.getName().compareTo(o2.getName());
	}
}