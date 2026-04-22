package calendarPackage;

import java.util.Comparator;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class ComparatorPhoneNumber implements Comparator<Contact> {

	@Override
	public int compare(Contact o1, Contact o2) {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		String n1 = util.format(o1.getPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164);
		String n2 = util.format(o2.getPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164);
		
		return n1.compareTo(n2);
	}
}