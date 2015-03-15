

import java.util.Calendar;
import java.util.Set;
import java.text.SimpleDateFormat;

public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
		
	
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}
	
	/**{@inheritDoc} 
	 */
	public MeetingImpl(Meeting meeting){
		
	}

	/**{@inheritDoc} 
	 */
	@Override
	public int getId() {
		return this.id;
	}
	
	/**{@inheritDoc} 
	 */
	@Override
	public Calendar getDate() {
		return this.date;
	}

	/**{@inheritDoc} 
	 */
	@Override
	public Set<Contact> getContacts() {
		return this.contacts;
	}
	
	/**{@inheritDoc} 
	 */
	@Override
	public boolean equals(Object object){
		try{
			Meeting compare = (Meeting) object;
			return (this.getId() == compare.getId() &&
					this.getContacts().equals(compare.getContacts()) &&
					this.getDate().equals(compare.getDate()));
		}catch (ClassCastException ex){
			return false;
		}
	}

	/**{@inheritDoc} 
	 */
	@Override
	public String toString() {
		return "Meeting [id=" + id + ", date=" + date.getTime() + ", contacts="
				+ contacts + "]";
	}

}
