import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;

public class XMLManager {
	private static Document createDocument() throws Exception {
	    // Stworzenie pustego drzewa DOM - obiektowy model dokumentu
	    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    return builder.newDocument();
	}
	private static Document createDocument(String path) throws Exception {
	    // Stworzenie pustego drzewa DOM - obiektowy model dokumentu i wczytanie danych z pliku
	    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    return builder.parse(new File(path));
	}
	
	public static void loadContactArray(ArrayList<Contact> contacts, Document doc) throws Exception {
	    NodeList contactNodes = doc.getElementsByTagName("contact");
	    
	    for (int i = 0; i < contactNodes.getLength(); i++) {
	    	Element el = (Element) contactNodes.item(i);
	    	
	    	Element name = (Element) el.getElementsByTagName("name").item(0);
	    	Element phoneNumber = (Element) el.getElementsByTagName("phoneNumber").item(0);

	    	contacts.add(new Contact(name.getTextContent(), Integer.parseInt(phoneNumber.getTextContent())));
	    }
	}
	
	public static void loadEventArray(ArrayList<Event> events, Document doc) throws Exception {
	    NodeList eventNodes = doc.getElementsByTagName("event");
	    
	    for (int i = 0; i < eventNodes.getLength(); i++) {
	    	Element el = (Element) eventNodes.item(i);
	    	
	    	Element date = (Element) el.getElementsByTagName("date").item(0);
	    	Element description = (Element) el.getElementsByTagName("description").item(0);

	    	events.add(new Event(LocalDate.parse(date.getTextContent()), description.getTextContent()));
	    }
	}
	
	public static void saveKontakty(ArrayList<Contact> kontakty, String path) throws Exception {
	    Document doc = createDocument();

	    // Stworzenie elementu głównego i dodanie go do dokumentu
	    Element root = doc.createElement("kontakty");
	    doc.appendChild(root);

	    for (int i = 0; i < kontakty.size(); i++) {
	    	Element el = doc.createElement("kontakt");

	    	Element name = doc.createElement("name");
	    	name.setTextContent(kontakty.get(i).getName());
	    	el.appendChild(name);
	    	
	    	Element phoneNumber = doc.createElement("phoneNumber");
	    	phoneNumber.setTextContent(Integer.toString(kontakty.get(i).getPhoneNumber()));
	    	el.appendChild(phoneNumber);

//	    	Alternatywne podejście - zapis jako aktybuty elementów
//	        el.setAttribute("name", kontakty.get(i).getName());
//	        el.setAttribute("phoneNumber", Integer.toString(kontakty.get(i).getPhoneNumber()));

	        root.appendChild(el);
	    }

	    // Renderowanie i zapis drzewa DOM do pliku
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    
//	    Testowy zapis, wydruk do konsoli
//	    Writer out = new StringWriter();
//	    transformer.transform(new DOMSource(doc), new StreamResult(out));
//	    System.out.println(out.toString());
	    
	    transformer.transform(new DOMSource(doc), new StreamResult(new File(path)));
	}
	public static void load(ArrayList<Contact> contacts, ArrayList<Event> events, String path) throws Exception {
	    Document doc = createDocument(path);
	    doc.getDocumentElement().normalize();
	    
	    loadContactArray(contacts, doc);
	    loadEventArray(events, doc);
	}
	
	public static void saveContacts(ArrayList<Contact> contacts, Element root, Document doc) throws Exception {
		Element contactRoot = doc.createElement("contacts");
		root.appendChild(contactRoot);
		
		for (int i = 0; i < contacts.size(); i++) {
	    	Element el = doc.createElement("contact");

//	    	Alternatywne podejście - zapis jako aktybuty elementów
//	        el.setAttribute("name", kontakty.get(i).getName());
//	        el.setAttribute("phoneNumber", Integer.toString(kontakty.get(i).getPhoneNumber()));

	    	Element name = doc.createElement("name");
	    	name.setTextContent(contacts.get(i).getName());
	    	el.appendChild(name);
	    	
	    	Element phoneNumber = doc.createElement("phoneNumber");
	    	phoneNumber.setTextContent(Integer.toString(contacts.get(i).getPhoneNumber()));
	    	el.appendChild(phoneNumber);

	    	contactRoot.appendChild(el);
	    }
	}

	public static void saveEvents(ArrayList<Event> events, Element root, Document doc) throws Exception {
		Element eventRoot = doc.createElement("events");
		root.appendChild(eventRoot);
		
		for (int i = 0; i < events.size(); i++) {
	    	Element el = doc.createElement("event");

	    	Element date = doc.createElement("date");
	    	date.setTextContent(events.get(i).getDateString());
	    	el.appendChild(date);
	    	
	    	Element description = doc.createElement("description");
	    	description.setTextContent(events.get(i).getDescription());
	    	el.appendChild(description);

	    	eventRoot.appendChild(el);
	    }
	}
	
	public static void save(ArrayList<Contact> contacts, ArrayList<Event> events, String path) throws Exception {
	    Document doc = createDocument();

	    // Stworzenie elementu głównego i dodanie go do dokumentu
	    Element root = doc.createElement("calendar");
	    doc.appendChild(root);

	    // Dodanie elementów kontaktów oraz zdarzeń
	    saveContacts(contacts, root, doc);
	    saveEvents(events, root, doc);
	    

	    // Renderowanie i zapis drzewa DOM do pliku
	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    
//	    Testowy zapis, wydruk do konsoli
//	    Writer out = new StringWriter();
//	    transformer.transform(new DOMSource(doc), new StreamResult(out));
//	    System.out.println(out.toString());
	    
	    transformer.transform(new DOMSource(doc), new StreamResult(new File(path)));
	}
}