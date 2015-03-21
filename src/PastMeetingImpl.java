import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;


/**{@inheritDoc}
 * 
 * 
 * @author Jamie
 *
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	String notes; //cannot be private as ContactManager requires direct access
	
	/**Creates a PastMeeting with the passed parameters. Notes will be set to blank.
	 * 
	 * ID uniqueness must be handled by a user program and is not guaranteed by the implementation
	 * 
	 * @param id meeting ID
	 * @param date Date of Meeting
	 * @param contacts
	 * @throws IllegalArgumentException if date is in the future
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts){
		super(id, date, contacts);
		this.notes = "";
		checkDateIsPast(date);
	}
	
	/**Creates a PastMeeting with the passed parameters. If notes is null, it will be set to a blank string
	 * 
	 * ID uniqueness must be handled by a user program and is not guaranteed by the implementation
	 * 
	 * @param id meeting ID
	 * @param date Date of Meeting
	 * @param contacts
	 * @throws IllegalArgumentException if date is in the future
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes){
		super(id, date, contacts);
		if (notes == null)
			notes = "";
		this.notes = notes;
		checkDateIsPast(date);
	}
	
	@Override
	public String getNotes() {
		return this.notes;
	}

	/**Checks that the date is in the past, and throws an exception if not. Used in constructors.
	 * 
	 * @param date
	 * @throws IllegalArgumentException() if date is in the future
	 */
	private void checkDateIsPast(Calendar date){
		if (date.compareTo(new GregorianCalendar()) == 1)
			throw new IllegalArgumentException();
	}
}
