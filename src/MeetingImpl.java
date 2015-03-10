

import java.util.Calendar;
import java.util.Set;
import java.text.SimpleDateFormat;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm");
	
	
	
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
	
	//TODO - rewrite to make exact meeting matches.
	@Override
	public boolean equals(Object object){
		try{
			Meeting compare = (Meeting) object;
			if (this.getId() == compare.getId())
				return true;		
		}catch (ClassCastException ex){
			//return statement below - any object apart from a class implementing Contact is not equal to a contact
		}
		return false;
	}


	@Override
	public String toString() {
		return "Meeting [id=" + id + ", date=" + sdf.format(date.getTime()) + ", contacts="
				+ contacts + "]";
	}

}
