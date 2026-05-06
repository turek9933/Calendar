package calendarPackage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.net.URI;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.InternetAddress;

public class XmlManager {
	public static void load(ArrayList<Contact> contacts, ArrayList<Event> events, String fileName) throws Exception {
		Document doc = createDocument(fileName);
		
		loadContactArray(contacts, doc);
		loadEventArray(events, doc);
		loadLinksContactAndEvents(contacts, events, doc);	
		}
	
	public static void save(ArrayList<Contact> contacts, ArrayList<Event> events) throws Exception {
	    Document doc = createDocument();

	    // Stworzenie elementu głównego i dodanie go do dokumentu
	    Element root = doc.createElement("calendar");
	    doc.appendChild(root);

	    // Dodanie elementów kontaktów oraz zdarzeń
	    saveContacts(contacts, root, doc);
	    saveEvents(events, root, doc);

	    // Renderowanie i zapis drzewa DOM do pliku
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();

	    // Ustawienia do polskich znaków
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	    DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("calendar.xml"));

	    transformer.transform(source, result);
	}
	
	private static Document createDocument() throws Exception {
	    // Stworzenie pustego drzewa DOM - obiektowy model dokumentu
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.newDocument();
	}
	private static Document createDocument(String path) throws Exception {
	    // Stworzenie pustego drzewa DOM - obiektowy model dokumentu i wczytanie danych z pliku
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.parse(new File(path));
	}

	private static void loadContactArray(ArrayList<Contact> contacts, Document doc) throws Exception {
		Element contactsElement = (Element) doc.getElementsByTagName("contacts").item(0);
	    NodeList contactNodes = contactsElement.getElementsByTagName("contact");
	    
		// Konwersja numeru telefonu ze Sting
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

	    for (int i = 0; i < contactNodes.getLength(); i++) {
	    	Element contactElement = (Element) contactNodes.item(i);
	    	
	    	int id = Integer.parseInt(contactElement.getElementsByTagName("id").item(0).getTextContent());
	    	String name = contactElement.getElementsByTagName("name").item(0).getTextContent();
	    	String surname = contactElement.getElementsByTagName("surname").item(0).getTextContent();
	    	
	    	String phoneNumberString = contactElement.getElementsByTagName("phoneNumber").item(0).getTextContent();
	    	Phonenumber.PhoneNumber phoneNumber = util.parse(phoneNumberString, null);

			String mailString = contactElement.getElementsByTagName("mail").item(0).getTextContent();
	    	InternetAddress mail = new InternetAddress(mailString);

	    	contacts.add(new Contact(id, name, surname, phoneNumber, mail));
	    }
	}

	private static void loadEventArray(ArrayList<Event> events, Document doc) throws Exception {
		Element eventsElement = (Element) doc.getElementsByTagName("events").item(0);
	    NodeList eventNodes = doc.getElementsByTagName("event");
	    
	    for (int i = 0; i < eventNodes.getLength(); i++) {
	    	Element eventElement = (Element) eventNodes.item(i);
	    	
	    	int id = Integer.parseInt(eventElement.getElementsByTagName("id").item(0).getTextContent());
	    	LocalDateTime date = LocalDateTime.parse(eventElement.getElementsByTagName("date").item(0).getTextContent());
	    	String description = eventElement.getElementsByTagName("description").item(0).getTextContent();
	    	URI mapUri = new URI(eventElement.getElementsByTagName("mapUri").item(0).getTextContent());

	    	events.add(new Event(id, date, description, mapUri));
	    }
	}
	
	private static void saveContacts(ArrayList<Contact> contacts, Element root, Document doc) throws Exception {
		Element contactRoot = doc.createElement("contacts");
		root.appendChild(contactRoot);

		// Konwersja numeru telefonu na Sting
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		for (Contact c : contacts) {
			Element contactElement = doc.createElement("contact");

			Element id = doc.createElement("id");
			id.setTextContent(Integer.toString(c.getId()));
			contactElement.appendChild(id);

			Element name = doc.createElement("name");
			name.setTextContent(c.getName());
			contactElement.appendChild(name);

			Element surname = doc.createElement("surname");
			surname.setTextContent(c.getSurname());
			contactElement.appendChild(surname);

			Element phoneNumberElement = doc.createElement("phoneNumber");
			// Konwersja numeru telefonu na Sting
			String phoneNumberString = util.format(c.getPhoneNumber(), PhoneNumberUtil.PhoneNumberFormat.E164);
			phoneNumberElement.setTextContent(phoneNumberString);
			contactElement.appendChild(phoneNumberElement);
			Element mailElement = doc.createElement("mail");
			mailElement.setTextContent(c.getMail().toString());
			contactElement.appendChild(mailElement);

			Element contactEvents = doc.createElement("contactEvents");
			for (Event e : c.getContactEvents()) {
				Element eventId = doc.createElement("eventId");
				eventId.setTextContent(Integer.toString(e.getId()));
				contactEvents.appendChild(eventId);
			}
			contactElement.appendChild(contactEvents);

			contactRoot.appendChild(contactElement);
		}
	}

	private static void saveEvents(ArrayList<Event> events, Element root, Document doc) throws Exception {
        Element eventRoot = doc.createElement("events");
        root.appendChild(eventRoot);

        for (Event e : events) {
            Element eventElement = doc.createElement("event");

            Element id = doc.createElement("id");
            id.setTextContent(Integer.toString(e.getId()));
            eventElement.appendChild(id);

            Element date = doc.createElement("date");
            date.setTextContent(e.getDateString());
            eventElement.appendChild(date);

            Element description = doc.createElement("description");
            description.setTextContent(e.getDescription());
            eventElement.appendChild(description);

            Element mapUri = doc.createElement("mapUri");
            mapUri.setTextContent(e.getMapUri().toString());
            eventElement.appendChild(mapUri);

            Element eventContacts = doc.createElement("eventContacts");
            for (Contact c : e.getEventContacts()) {
                Element contactId = doc.createElement("contactId");
                contactId.setTextContent(Integer.toString(c.getId()));
                eventContacts.appendChild(contactId);
            }
            eventElement.appendChild(eventContacts);

            eventRoot.appendChild(eventElement);
        }
	
	}

	private static void loadLinksContactAndEvents(ArrayList<Contact> contacts, ArrayList<Event> events, Document doc) {

		Element contactsElement = (Element) doc.getElementsByTagName("contacts").item(0);
		NodeList contactNodes = contactsElement.getElementsByTagName("contact");

		for (int i = 0; i < contactNodes.getLength(); i++) {
			Element contactElement = (Element) contactNodes.item(i);

			int contactId = Integer.parseInt(contactElement.getElementsByTagName("id").item(0).getTextContent());

			NodeList contactEventsNodes = contactElement.getElementsByTagName("contactEvents");

			if (contactEventsNodes.getLength() == 0) {
				continue;
			}

			Element contactEventsElement = (Element) contactEventsNodes.item(0);
			NodeList eventIdNodes = contactEventsElement.getElementsByTagName("eventId");

			for (int j = 0; j < eventIdNodes.getLength(); j++) {
				int eventId = Integer.parseInt(eventIdNodes.item(j).getTextContent());

				Contact.addContactEvent(contacts, contactId, events, eventId);
			}
		}
	}
}