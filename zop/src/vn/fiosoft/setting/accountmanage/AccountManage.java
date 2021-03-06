package vn.fiosoft.setting.accountmanage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlSerializer;

import vn.fiosoft.log.Log4J;
import android.content.Context;
import android.util.Xml;

public class AccountManage {

	private String FILE_NAME = "ACC.XML";

	private final String ACCOUNTS = "accounts";
	private final String ACCOUNT = "account";
	private final String PID = "pid";
	private final String EMAIL = "email";
	private final String STATUS = "status";

	private List<Account> accounts;
	private Context context;

	public AccountManage(Context context) {
		this.context = context;
		parseXML();
	}

	@SuppressWarnings("finally")
	private List<Account> parseXML() {

		try {
			accounts = new ArrayList<Account>();
			FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			AccountHandler accountHandler = new AccountHandler();
			xr.setContentHandler(accountHandler);

			xr.parse(new InputSource(fileInputStream));

			accounts = accountHandler.getListAccount();

		} catch (Exception ioe) {
			Log4J.e("parse", ioe.toString());
		} finally {
			return accounts;
		}

	}

	public List<Account> list() {
		return accounts;
	}

	public Account getAccountActivated() {
		for (Account acc : accounts) {
			if (acc.getStatus() == Account.ACTIVATED)
				return acc;

		}
		return null;
	}

	public void setAccountActivated(Account account) {
		boolean isExists = false;
		String email = account.getEmail();
		for (Account acc : accounts) {
			if (acc.getEmail().equalsIgnoreCase(email)) {
				account.setStatus(Account.ACTIVATED);
				isExists = true;
			} else {
				if (acc.getStatus() == Account.ACTIVATED)
					acc.setStatus(Account.INACTIVATED);
			}
		}

		if (isExists == false) {
			account.setStatus(Account.ACTIVATED);
			accounts.add(account);
		}
		save();
	}

	private boolean save() {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

			String s = writeXml();
			out.write(s);
			out.close();
			fos.close();
			return true;

		} catch (Exception e) {
			Log4J.e("save account", e.getMessage());
			return false;
		}

	}

	private String writeXml() {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", ACCOUNTS);
			for (Account acc : accounts) {
				serializer.startTag("", ACCOUNT);
				serializer.startTag("", PID);
				serializer.text(String.valueOf(acc.getPid()));
				serializer.endTag("", PID);
				serializer.startTag("", EMAIL);
				serializer.text(acc.getEmail());
				serializer.endTag("", EMAIL);
				serializer.startTag("", STATUS);
				serializer.text(String.valueOf(acc.getStatus()));
				serializer.endTag("", STATUS);
				serializer.endTag("", ACCOUNT);
			}
			serializer.endTag("", ACCOUNTS);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public class AccountHandler extends DefaultHandler {

		private List<Account> accounts;
		private Account account;
		private StringBuilder builder;

		public List<Account> getListAccount() {
			return accounts;
		}

		public AccountHandler() {
			builder = new StringBuilder();
			accounts = new ArrayList<Account>();
		}

		public boolean saveAccount(Account account) {
			return false;
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			builder.append(ch, start, length);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (localName.equalsIgnoreCase(ACCOUNT)) {
				account = new Account();
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (localName.equalsIgnoreCase(ACCOUNT)) {
				accounts.add(account);
			} else if (localName.equalsIgnoreCase(PID)) {
				account.setPid(Long.parseLong(builder.toString()));
			} else if (localName.equalsIgnoreCase(EMAIL)) {
				account.setEmail(builder.toString());
			} else if (localName.equalsIgnoreCase(STATUS)) {
				account.setStatus(Integer.parseInt(builder.toString()));
			}

			builder.setLength(0);
		}

	}

}
