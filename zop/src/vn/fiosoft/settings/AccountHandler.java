package vn.fiosoft.settings;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AccountHandler extends DefaultHandler {

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

	public AccountHandler() {
		accounts = new ArrayList<Account>();
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
		if (localName.equalsIgnoreCase(ACCOUNT)) {
			account = new Account();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase(ACCOUNT)) {
			accounts.add(account);
		} else if (localName.equalsIgnoreCase(ID)) {
			account.setId(Long.parseLong(builder.toString()));
		} else if (localName.equalsIgnoreCase(PID)){
			account.setPid(Long.parseLong(builder.toString()));
		} else if (localName.equalsIgnoreCase(EMAIL)){
			account.setEmail(builder.toString());
		} else if (localName.equalsIgnoreCase(PHONE)){
			account.setPhone(builder.toString());
		} else if (localName.equalsIgnoreCase(PASSWORD)){
			account.setPassword(builder.toString());
		} else if (localName.equalsIgnoreCase(FIRST_NAME)){
			account.setFirstName(builder.toString());
		} else if (localName.equalsIgnoreCase(LAST_NAME)){
			account.setLastName(builder.toString());
		} else if (localName.equalsIgnoreCase(GENDER)){
			account.setGender(Integer.parseInt(builder.toString()));
		}else if (localName.equalsIgnoreCase(BIRTHDAY)){
			account.setBirthday(Long.parseLong(builder.toString()));
		}
		
		builder.setLength(0);
	}

	@Override
	public void endDocument() throws SAXException {

	}
}
