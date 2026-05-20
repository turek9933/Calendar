package calendarPackage;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.mail.internet.InternetAddress;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DbManager {
	public static Connection connect() throws Exception {
		String url = "jdbc:sqlite:calendar.db";
		return DriverManager.getConnection(url);
	}

	public static void save(ContactList contactList, EventList eventList) throws Exception {
		clearTables();
		saveContacts(contactList);
		saveEvents(eventList);
		saveParticipations(contactList);
	}

	public static void load(ContactList contactList, EventList eventList) throws Exception {
		loadContacts(contactList);
		loadEvents(eventList);
		loadParticipations(contactList, eventList);
	}

	private static void clearTables() throws Exception {
		Connection connection = connect();
		Statement statement = connection.createStatement();

		statement.executeUpdate("DELETE FROM Contacts");
		statement.executeUpdate("DELETE FROM Events");
		statement.executeUpdate("DELETE FROM Participations");

		statement.close();
		connection.close();
	}

	private static void saveContacts(ContactList contactList) throws Exception {
		Connection connection = connect();

		String sql = "INSERT INTO Contacts (id, name, surname, phone_number, mail) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		for (Contact contact : contactList.getContacts()) {
			String phoneNumber = util.format(contact.getPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164);

			statement.setInt(1, contact.getId());
			statement.setString(2, contact.getName());
			statement.setString(3, contact.getSurname());
			statement.setString(4, phoneNumber);
			statement.setString(5, contact.getMail().toString());

			statement.executeUpdate();
		}

		statement.close();
		connection.close();
	}

	private static void saveEvents(EventList eventList) throws Exception {
		Connection connection = connect();

		String sql = "INSERT INTO Events (id, date, description, map_uri) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		for (Event event : eventList.getEvents()) {
			statement.setInt(1, event.getId());
			statement.setString(2, event.getDate().toString());
			statement.setString(3, event.getDescription());
			statement.setString(4, event.getMapUri().toString());

			statement.executeUpdate();
		}

		statement.close();
		connection.close();
	}

	private static void saveParticipations(ContactList contactList) throws Exception {
		Connection connection = connect();

		String sql = "INSERT INTO Participations (contact_id, event_id) VALUES (?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		for (Contact contact : contactList.getContacts()) {
			statement.setInt(1, contact.getId());
			for (Event contactEvent : contact.getContactEvents()) {
				statement.setInt(2, contactEvent.getId());
				statement.executeUpdate();
			}
		}

		statement.close();
		connection.close();
	}

	private static void loadContacts(ContactList contactList) throws Exception {
		Connection connection = connect();
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM Contacts");
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		while (rs.next()) {
			contactList.getContacts().add(new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
					util.parse(rs.getString("phone_number"), null), new InternetAddress(rs.getString("mail"))));
		}

		rs.close();
		statement.close();
		connection.close();
	}

	private static void loadEvents(EventList eventList) throws Exception {
		Connection connection = connect();
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM Events");

		while (rs.next()) {
			eventList.getEvents().add(new Event(rs.getInt("id"), LocalDateTime.parse(rs.getString("date")),
					rs.getString("description"), new URI(rs.getString("map_uri"))));
		}

		rs.close();
		statement.close();
		connection.close();
	}

	private static void loadParticipations(ContactList contactList, EventList eventList) throws Exception {
		Connection connection = connect();
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM Participations");

		while (rs.next()) {
			contactList.addContactEvent(rs.getInt("contact_id"), eventList, rs.getInt("event_id"));
		}

		rs.close();
		statement.close();
		connection.close();
	}
}