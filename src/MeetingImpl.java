

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;

/**{@inheritDoc} 
 */
public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
		
	
	/**{@inheritDoc} 
	 * 
	 * Creates a new meeting, assigning ID, Date and Contacts to the meeting. If contacts is null, it will create
	 * with an empty contact set. 
	 * 
	 * Uniqueness of ID must be managed by the creating class and is not internally managed by the MeetingImpl class.
	 * 
	 * @throws NullPointerException if Date is null
	 */	
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
		this.id = id;
		
		if (date == null)
			throw new NullPointerException();
		else
			this.date = date;
		
		if (contacts == null)
			this.contacts = new HashSet<Contact>();
		else
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
	 * 
	 * Will return true if both objects are meeting, ID, contacts and Notes are the same and the 
	 * Calendars differ by < 1 second
	 */
	
	@Override
	public boolean equals(Object object){
		try{
			Meeting compare = (Meeting) object;
			return (this.getId() == compare.getId() && 
				    this.getContacts().equals(compare.getContacts()) &&
				    (Math.abs(this.getDate().getTimeInMillis() - 
				    		 compare.getDate().getTimeInMillis()) < 1000));
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		return result;
	}
}
