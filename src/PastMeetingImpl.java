import java.util.Calendar;
import java.util.Set;


public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts){
		super(id, date, contacts);
	}
	
	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
