import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;


public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	/**{@inheritDoc}
	 * 
	 * @throws IllegalArgumentException if date is in the past or present
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts){	
		super(id, date, contacts);
		if (date.compareTo(new GregorianCalendar()) <= 0 )
			throw new IllegalArgumentException();
	}
}
