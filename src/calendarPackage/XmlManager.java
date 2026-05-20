package calendarPackage;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlManager {
	private String fileName;

	public XmlManager(String fileName) {
		this.fileName = fileName;
	}

	public XmlManager() {
		this("calendar.xml");
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void load(ContactList contactList, EventList eventList) throws Exception {
		Document doc = createDocument(fileName);

		loadContactArray(contactList, doc);
		loadEventArray(eventList, doc);

		loadLinksContactAndEvents(contactList, eventList, doc);
	}

	public void save(ContactList contactList, EventList eventList) throws Exception {
	    Document doc = createDocument();

	    // Stworzenie elementu głównego i dodanie go do dokumentu
	    Element root = doc.createElement("calendar");
	    doc.appendChild(root);

	    // Dodanie elementów kontaktów oraz zdarzeń
	    saveContacts(contactList, root, doc);
	    saveEvents(eventList, root, doc);

	    // Renderowanie i zapis drzewa DOM do pliku
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();

	    // Ustawienia do polskich znaków
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	    DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fileName));

	    transformer.transform(source, result);
	}

	private Document createDocument() throws Exception {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.newDocument();
	}

	private Document createDocument(String path) throws Exception {
		// Stworzenie pustego drzewa DOM - obiektowy model dokumentu i wczytanie danych z pliku
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    return builder.parse(new File(path));
	}

	private void loadContactArray(ContactList contactList, Document doc) throws Exception {
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

	    	contactList.addContact(id, name, surname, phoneNumber, mail);
	    }
	}

	private void loadEventArray(EventList eventList, Document doc) throws Exception {
		Element eventsElement = (Element) doc.getElementsByTagName("events").item(0);
	    NodeList eventNodes = doc.getElementsByTagName("event");
	    
	    for (int i = 0; i < eventNodes.getLength(); i++) {
	    	Element eventElement = (Element) eventNodes.item(i);
	    	
	    	int id = Integer.parseInt(eventElement.getElementsByTagName("id").item(0).getTextContent());
	    	LocalDateTime date = LocalDateTime.parse(eventElement.getElementsByTagName("date").item(0).getTextContent());
	    	String description = eventElement.getElementsByTagName("description").item(0).getTextContent();
	    	URI mapUri = new URI(eventElement.getElementsByTagName("mapUri").item(0).getTextContent());

	    	eventList.addEvent(id, date, description, mapUri);
	    }
	}
	private void loadLinksContactAndEvents(ContactList contactList, EventList eventList, Document doc) {
		Element contactsElement = (Element) doc.getElementsByTagName("contacts").item(0);
	    NodeList contactNodes = contactsElement.getElementsByTagName("contact");

	    for (int i = 0; i < contactNodes.getLength(); i++) {
	    	Element contactElement = (Element) contactNodes.item(i);

	    	int contactId = Integer.parseInt(contactElement.getElementsByTagName("id").item(0).getTextContent());
	    	NodeList contactEventsNodes = contactElement.getElementsByTagName("contactEvents");

	    	if (contactEventsNodes.getLength() == 0) {
	    		continue;
	    	}

	    	Element contactEventElement = (Element) contactEventsNodes.item(0);
	    	NodeList eventIdNodes = contactEventElement.getElementsByTagName("eventId");
	    	
	    	for (int j = 0; j < eventIdNodes.getLength(); j++) {
    			int eventId = Integer.parseInt(eventIdNodes.item(j).getTextContent());
    			contactList.addContactEvent(contactId, eventList, eventId);
	    	}
	    }
	}
	
	private void saveContacts(ContactList contactList, Element root, Document doc) throws Exception {
		Element contactRoot = doc.createElement("contacts");
		root.appendChild(contactRoot);

		// Konwersja numeru telefonu na Sting
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();

		for (Contact c : contactList.getContacts()) {
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
	    	
			ArrayList<Event> contactEventsArray = c.getContactEvents();
	    	if (contactEventsArray.size() > 0) {
				Element contactEvents = doc.createElement("contactEvents");
		    	for (Event e : contactEventsArray) {
		    		Element eventId = doc.createElement("eventId");
		    		eventId.setTextContent(Integer.toString(e.getId()));
		    		contactEvents.appendChild(eventId);
		    	}
		    	contactElement.appendChild(contactEvents);
	    	}

	    	contactRoot.appendChild(contactElement);
	    }
	}

	private void saveEvents(EventList eventList, Element root, Document doc) throws Exception {
		Element eventRoot = doc.createElement("events");
		root.appendChild(eventRoot);
		
		for (Event e : eventList.getEvents()) {
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
	    	
	    	ArrayList<Contact> eventContactsArray = e.getEventContacts();
	    	if (eventContactsArray.size() > 0) {
		    	Element eventContacts = doc.createElement("eventContacts");
		    	for (Contact c : e.getEventContacts()) {
		    		Element contactId = doc.createElement("contactId");
		    		contactId.setTextContent(Integer.toString(c.getId()));
		    		eventContacts.appendChild(contactId);
		    	}
		    	eventElement.appendChild(eventContacts);
	    	}

	    	eventRoot.appendChild(eventElement);
	    }
	}
}