package calendarPackage;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import jakarta.mail.internet.InternetAddress;

public class DbManager {

	public static Connection connect() throws Exception {
		String url = "jdbc:sqlite:calendar.db";
		return DriverManager.getConnection(url);
	}

	public static void saveAll(ArrayList<Contact> contacts, ArrayList<Event> events) throws Exception {
		clearTables();
		saveContacts(contacts);
		saveEvents(events);
	}

	public static void loadAll(ArrayList<Contact> contacts, ArrayList<Event> events) throws Exception {
		loadContacts(contacts);
		loadEvents(events);
	}

	public static void clearTables() throws Exception {
		Connection connection = connect();
		Statement statement = connection.createStatement();

		statement.executeUpdate("DELETE FROM Contacts");
		statement.executeUpdate("DELETE FROM Events");

		statement.close();
		connection.close();
	}

	public static void saveContacts(ArrayList<Contact> contacts) throws Exception {
		Connection connection = connect();

		String sql = "INSERT INTO Contacts (id, name, surname, phone_number, email) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		for (Contact contact : contacts) {
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

	public static void saveEvents(ArrayList<Event> events) throws Exception {
		Connection connection = connect();

		String sql = "INSERT INTO Events (id, date, description, map_uri) VALUES (?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		for (Event event : events) {
			statement.setInt(1, event.getId());
			statement.setString(2, event.getDate().toString());
			statement.setString(3, event.getDescription());
			statement.setString(4, event.getMapUri().toString());

			statement.executeUpdate();
		}

		statement.close();
		connection.close();
	}

	public static void loadContacts(ArrayList<Contact> contacts) throws Exception {

		Connection connection = connect();
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM Contacts");

		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		while (rs.next()) {

			contacts.add(new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
					util.parse(rs.getString("phone_number"), null), new InternetAddress(rs.getString("email"))));
		}

		rs.close();
		statement.close();
		connection.close();
	}

	public static void loadEvents(ArrayList<Event> events) throws Exception {

		Connection connection = connect();
		Statement statement = connection.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM Events");

		while (rs.next()) {

			events.add(new Event(rs.getInt("id"), LocalDateTime.parse(rs.getString("date")),
					rs.getString("description"), new URI(rs.getString("map_uri"))));
		}

		rs.close();
		statement.close();
		connection.close();
	}
}