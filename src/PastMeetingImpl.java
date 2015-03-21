import java.util.Calendar;
import java.util.Set;


public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	String notes;
	
	//TODO - add constructor exceptions
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts){
		super(id, date, contacts);
		this.notes = "";
	}
	
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes){
		super(id, date, contacts);
		if (notes == null)
			notes = "";	
		this.notes = notes;
	}
	
	@Override
	public String getNotes() {
		return this.notes;
	}

}
