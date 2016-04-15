package com.iamchiwon.translater;

import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

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

public class iOSStringTranslator {
	public static void main(String[] args) throws Exception {

		final String inputFilePath = "string.xliff";
		final String outputFilePath = "string-fr.xliff";

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		GoogleTranslater translator = new GoogleTranslater(Locale.ENGLISH, Locale.FRENCH);

		// 1. read input file
		Document xmlDoc = docBuilder.parse(inputFilePath);

		// 2. parse and translate
		Element root = xmlDoc.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("target");

		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			Element stringItem = (Element) nodeList.item(i);

			String value = stringItem.getTextContent();

			// translate and replace
			String translatedValue = translator.translate(value);
			stringItem.setTextContent(translatedValue);

			System.out.println(value + " : " + value + " => " + translatedValue);
		}

		// 3. write output file
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult output = new StreamResult(new FileWriter(new File(outputFilePath)));
		transformer.transform(source, output);

	}
}
