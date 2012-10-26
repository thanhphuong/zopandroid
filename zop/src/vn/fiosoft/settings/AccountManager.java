package vn.fiosoft.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;

public class AccountManager {

	private String FILE_NAME = "acc.xml";

	private List<Account> parseXML() {
		List<Account> accounts = new ArrayList<Account>();

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			AccountHandler accountHandler = new AccountHandler();
			xr.setContentHandler(accountHandler);

			xr.parse(new InputSource(new FileInputStream(FILE_NAME)));

			accounts = accountHandler.getListAccount();

		} catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} catch (SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} catch (IOException ioe) {
			Log.e("SAX XML", "sax parse io error", ioe);
		}

		return accounts;
	}

	public List<Account> list() {
		return parseXML();
	}

}
