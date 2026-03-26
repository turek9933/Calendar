import java.util.Comparator;

public class PhoneNumberComperator implements Comparator<Contact> {
	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getPhoneNumber() - c2.getPhoneNumber();
	}
}