package mojPakiet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class XmlMg {

	public static void zapisDoXml(ArrayList<Kontakt> kontakty, ArrayList<Zdarzenie> zdarzenia) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element root = doc.createElement("kalendarz");
		doc.appendChild(root);

		Element kontaktyElement = doc.createElement("kontakty");
		root.appendChild(kontaktyElement);

		Element zdarzeniaElement = doc.createElement("zdarzenia");
		root.appendChild(zdarzeniaElement);

		for (Kontakt k : kontakty) {
			Element kontakt = doc.createElement("kontakt");
			kontaktyElement.appendChild(kontakt);

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(k.getId())));
			kontakt.appendChild(id);

			Element imie = doc.createElement("imie");
			imie.appendChild(doc.createTextNode(k.getImie()));
			kontakt.appendChild(imie);

			Element nazwisko = doc.createElement("nazwisko");
			nazwisko.appendChild(doc.createTextNode(k.getNazwisko()));
			kontakt.appendChild(nazwisko);

			Element numer = doc.createElement("numerTelefonu");
			numer.appendChild(doc.createTextNode(k.getNumerTelefonu()));
			kontakt.appendChild(numer);
		}

		for (Zdarzenie z : zdarzenia) {
			Element zdarzenie = doc.createElement("zdarzenie");
			zdarzeniaElement.appendChild(zdarzenie);

			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(String.valueOf(z.getId())));
			zdarzenie.appendChild(id);

			Element data = doc.createElement("data");
			data.appendChild(doc.createTextNode(z.getData().toString()));
			zdarzenie.appendChild(data);

			Element opis = doc.createElement("opis");
			opis.appendChild(doc.createTextNode(z.getOpis()));
			zdarzenie.appendChild(opis);

			Element numer = doc.createElement("numerTelefonu");
			numer.appendChild(doc.createTextNode(z.getNumerTelefonu()));
			zdarzenie.appendChild(numer);
			;
		}

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("kalendarz.xml"));

		transformer.transform(source, result);

		System.out.println("zapis");
	}

	public static ArrayList<Kontakt> odczytajKontaktyXml(String nazwaPliku) throws Exception {

		ArrayList<Kontakt> kontakty = new ArrayList<>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(new File(nazwaPliku));

		System.out.println("wczytany");

		Element kontaktyElement = (Element) doc.getElementsByTagName("kontakty").item(0);
		NodeList listaKontaktow = kontaktyElement.getElementsByTagName("kontakt");
		System.out.println("Liczba kontaktow: " + listaKontaktow.getLength());

		for (int i = 0; i < listaKontaktow.getLength(); i++) {
			Element kontakt = (Element) listaKontaktow.item(i);

			int id = Integer.parseInt(kontakt.getElementsByTagName("id").item(0).getTextContent());

			String imie = kontakt.getElementsByTagName("imie").item(0).getTextContent();
			String nazwisko = kontakt.getElementsByTagName("nazwisko").item(0).getTextContent();
			String numerTelefonu = kontakt.getElementsByTagName("numerTelefonu").item(0).getTextContent();

			Kontakt k = new Kontakt(id, imie, nazwisko, numerTelefonu);
			kontakty.add(k);
		}

		return kontakty;
	}

	public static ArrayList<Zdarzenie> odczytajZdarzeniaXml(String nazwaPliku) throws Exception {

		ArrayList<Zdarzenie> zdarzenia = new ArrayList<>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse(new File(nazwaPliku));

		System.out.println("wczytany");

		Element zdarzeniaElement = (Element) doc.getElementsByTagName("zdarzenia").item(0);
		NodeList listaZdarzen = zdarzeniaElement.getElementsByTagName("zdarzenie");
		System.out.println("Liczba zdarzen: " + listaZdarzen.getLength());

		for (int i = 0; i < listaZdarzen.getLength(); i++) {
			Element zdarzenie = (Element) listaZdarzen.item(i);

			int id = Integer.parseInt(zdarzenie.getElementsByTagName("id").item(0).getTextContent());

			LocalDateTime data = LocalDateTime.parse(zdarzenie.getElementsByTagName("data").item(0).getTextContent());

			String opis = zdarzenie.getElementsByTagName("opis").item(0).getTextContent();
			String numerTelefonu = zdarzenie.getElementsByTagName("numerTelefonu").item(0).getTextContent();

			Zdarzenie z = new Zdarzenie(id, data, opis, numerTelefonu);
			zdarzenia.add(z);
		}

		return zdarzenia;
	}
}