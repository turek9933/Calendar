package mojPakiet;

import java.util.Comparator;

public class ComparatorImie implements Comparator<Kontakt> {
	@Override
	public int compare(Kontakt o1, Kontakt o2) {
		return o1.getImie().compareTo(o2.getImie());
	}
}
