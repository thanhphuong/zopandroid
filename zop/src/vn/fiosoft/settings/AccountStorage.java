package vn.fiosoft.settings;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AccountStorage extends DefaultHandler {

	private final String ACCOUNTS = "accounts";
	private final String ACCOUNT = "account";
	private final String ID = "id";
	private final String PID = "pid";
	private final String EMAIL = "email";
	private final String PHONE = "phone";
	private final String PASSWORD = "password";
	private final String FIRST_NAME = "first_name";
	private final String LAST_NAME = "last_name";
	private final String GENDER = "gender";
	private final String BIRTHDAY = "birthday";

	private List<Account> accounts;
	private Account account;
	private StringBuilder builder;

	public List<Account> getListAccount() {
		return accounts;
	}

	public AccountStorage() {
		accounts = new ArrayList<Account>();
		account = new Account();
	}

	public boolean saveAccount(Account account) {
		return false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		builder.append(ch, start, length);
	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (this.account != null){
			
		}

	}

	@Override
	public void endDocument() throws SAXException {

	}
}
