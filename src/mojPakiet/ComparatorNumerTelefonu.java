package mojPakiet;

import java.util.Comparator;

public class ComparatorNumerTelefonu implements Comparator<Zdarzenie> {
	@Override
	public int compare(Zdarzenie o1, Zdarzenie o2) {
		return o1.getNumerTelefonu().compareTo(o2.getNumerTelefonu());
	}
}
