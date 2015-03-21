import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;


public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	String notes;
	
	//TODO - add constructor exceptions
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts){
		super(id, date, contacts);
		this.notes = "";
		checkDateIsPast(date);
	}
	
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

	/**Checks that the date is in the past, and throws an exception if not
	 * 
	 * @param date
	 * @throws IllegalArgumentException() if date is in the future
	 */
	private void checkDateIsPast(Calendar date){
		if (date.compareTo(new GregorianCalendar()) == 1)
			throw new IllegalArgumentException();
	}
}
