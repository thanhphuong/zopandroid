package vn.fiosoft.setting.accountmanage;

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

	private String FILE_NAME = "acc.xml";

	private final String ACCOUNTS = "accounts";
	private final String ACCOUNT = "account";
	private final String PID = "pid";
	private final String EMAIL = "email";
	private final String SYNC = "sync";

	private List<Account> accounts;
	private Context context;

	public AccountManage(Context context) {
		this.context = context;
		parseXML();
	}

	private List<Account> parseXML() {
		accounts = new ArrayList<Account>();

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			AccountHandler accountHandler = new AccountHandler();
			xr.setContentHandler(accountHandler);

			xr.parse(new InputSource(context.openFileInput(FILE_NAME)));

			accounts = accountHandler.getListAccount();

		} catch (Exception ioe) {
			Log4J.e("parse", ioe.toString());
		}

		return accounts;
	}

	public List<Account> list() {
		return accounts;
	}

	public Account getAccountSync() {
		for (Account acc : accounts) {
			if (acc.getSync() == Account.SYNC_ON)
				return acc;

		}
		return null;
	}

	public void setAccountSync(Account account) {
		boolean isExists = false;
		String email = account.getEmail();
		for (Account acc : accounts) {
			if (acc.getEmail().equalsIgnoreCase(email)) {
				account.setSync(Account.SYNC_ON);
				isExists = true;
			} else {
				if (acc.getSync() == Account.SYNC_ON)
					acc.setSync(Account.SYNC_OFF);
			}
		}

		if (isExists == false) {
			account.setSync(Account.SYNC_ON);
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
				serializer.startTag("", SYNC);
				serializer.text(String.valueOf(acc.getSync()));
				serializer.endTag("", SYNC);
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
			} else if (localName.equalsIgnoreCase(SYNC)) {
				account.setSync(Integer.parseInt(builder.toString()));
			}

			builder.setLength(0);
		}

	}

}
