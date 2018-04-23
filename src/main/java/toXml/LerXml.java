package toXml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.UtilString;

public class LerXml {

	private Document doc;

	public LerXml(String path) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = (Document) dBuilder.parse(xmlFile);
	}

	public java.util.List<String> getListStrings(String tagName) {
		return toListNoString(doc, tagName);
	}

	public Map<String, String> getObservacoes(String tagName) {
		return toListNoObs(doc, tagName);
	}

	private java.util.List<String> toListNoString(Document doc, String tagName) {
		NodeList nodeList = doc.getElementsByTagName(tagName);
		java.util.List<String> lista = new ArrayList<String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node NoPai = nodeList.item(i);
			if (NoPai.getNodeType() == Node.ELEMENT_NODE) {
				Element elementePai = (Element) NoPai;
				NodeList listPai = elementePai.getChildNodes();

				for (int j = 0; j < listPai.getLength(); j++) {
					Node noFilho = listPai.item(j);
					if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
						Element elementeChild = (Element) noFilho;
						lista.add(elementeChild.getTextContent().toLowerCase());
					}
				}
			}
		}
		return lista;
	}

	private Map<String, String> toListNoObs(Document doc, String tagName) {
		NodeList nodeList = doc.getElementsByTagName(tagName);
		Map<String, String> mapObs = new HashMap<>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node NoPai = nodeList.item(i);
			if (NoPai.getNodeType() == Node.ELEMENT_NODE) {
				Element elementePai = (Element) NoPai;
				NodeList listPai = elementePai.getChildNodes();

				for (int j = 0; j < listPai.getLength(); j++) {
					Node noFilho = listPai.item(j);
					if (noFilho.getNodeType() == Node.ELEMENT_NODE) {

						Element elementeChild = (Element) noFilho;
						switch (elementeChild.getTagName()) {
						case "assunto":
							mapObs.put("assunto", elementeChild.getTextContent().toLowerCase());
							break;
						case "cabecalho":
							mapObs.put("cabecalho", elementeChild.getTextContent().toLowerCase());
							break;
						case "corpo":
							mapObs.put("corpo", elementeChild.getTextContent().toLowerCase());
							break;
						case "rodape":
							mapObs.put("rodape", elementeChild.getTextContent().toLowerCase());
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return mapObs;
	}
	

}
