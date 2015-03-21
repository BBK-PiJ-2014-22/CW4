
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PastMeetingTest {

	Calendar meetingdate;
	Set<Contact> contacts;
	int meetingid;
	PastMeeting meeting;
	String notes;
	
	/**Parameters should be the class of meeting. Parameters should be:
	 * 
	 * 1) Meeting ID
	 * 2) Time (in months) before or after current date that the meeting date should be set to
	 * 3) Number of contacts to add to the meeting
	 * 4) Notes (null for no notes passed)
	 * 
	 * @return the parameters for testing
	 */
	
	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {     
                {1,  -1,  1, null },
                {1,  -1,  2, "" },
                {1,  -1,  3, "Past Meeting Notes" },
                {1,   1,  4, "Future Meeting Notes" }

          });
		
	}
	
	public PastMeetingTest(int id, int monthChange, int numberOfContacts, String notes){
		
		this.meetingdate = new GregorianCalendar();
		this.meetingdate.add(Calendar.MONTH,monthChange);
		this.meetingid = id;
		this.contacts = new HashSet<Contact>();
		this.notes = notes;
		
		for (int i = 0 ; i < numberOfContacts ; i++)
			this.contacts.add(new ContactImpl(i, "Contact"+i, "Notes for contact "+i));
	}
	
	@Before
	public void setUp(){
		if (this.notes == null){
			this.meeting = new PastMeetingImpl(this.meetingid, this.meetingdate, this.contacts);
		}else{
			this.meeting = new PastMeetingImpl(this.meetingid, this.meetingdate, this.contacts, this.notes);
		}
	}
	
	@Test
	public void testGetNotes(){
		
		String comparator = this.notes;
		if (comparator == null)
			comparator = "";
		assertEquals(comparator, this.meeting.getNotes());
			
	}
}
