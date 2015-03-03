

import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
	
	int id;
	Calendar date;
	Set<Contact> contacts;
	
	
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}

	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Calendar getDate() {
		return this.date;
	}

	@Override
	public Set<Contact> getContacts() {
		return this.contacts;
	}

}
