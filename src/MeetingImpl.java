
import java.util.Calendar;
import java.util.Set;

/**{@inheritDoc} 
 * 
 * This implementation does NOT handle unique IDs for meetings directly. Uniqueness of IDs must be
 * handled by the client program using this implementation, by passing a unique ID to the constructor.
 * 
 */
public class MeetingImpl implements Meeting {
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
		
	/**{@inheritDoc} 
	 * 
	 * Creates a new meeting, assigning ID, Date and Contacts to the meeting. The contact set must have
	 * at least one contact or else the constructor will throw an exception.
	 * 
	 * Uniqueness of ID must be managed by the creating class and is not internally managed by the MeetingImpl class.
	 * 
	 * @throws NullPointerException if Date or contacts are null
	 * @throws IllegalArgumentException if the Contact Set is null
	 */	
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts){
		if (date == null || contacts == null)
			throw new NullPointerException();
		else if (contacts.isEmpty())
			throw new IllegalArgumentException();
		else{
			this.id = id;
			this.contacts = contacts;
			this.date = date;
		}
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
	 * Will return true if both objects extend Meeting, and ID, contacts and Notes are the same 
	 * and the Calendars differ by < 1 second
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

	/**{@inheritDoc} 
	 */
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
