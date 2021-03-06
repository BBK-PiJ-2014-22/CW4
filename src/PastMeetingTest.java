
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

/**Test PastMeeting Impl
 * 
 * Parameterized to test against a range of note entries
 *
 *Test 1 - Tests the notes are working and will always return a string
 *Test 2 - Tests the meeting cannot be created with a future date
 * 
 * @author Jamie
 *
 */
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
	 * 4) Notes ("EMPTY" for no notes passed)
	 * 
	 * @return the parameters for testing
	 */
	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {     
                {1,  -1,  1, null },
                {1,  -1,  1, "EMPTY"},
                {1,  -1,  2, "" },
                {1,  -1,  3, "Past Meeting Notes" },
          });
	}
	
	/**Constructor creates fields for the SetUp to work with and the tests to match against.
	 * 
	 * @param id ID of meeting
	 * @param monthChange Date difference (in months) from today
	 * @param numberOfContacts Number of Contacts to add
	 * @param notes if EMPTY, will construct without passing notes. Else, will pass notes to constructor
	 */
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
		if (this.notes == "EMPTY"){
			this.meeting = new PastMeetingImpl(this.meetingid, this.meetingdate, this.contacts);
		}else{
			this.meeting = new PastMeetingImpl(this.meetingid, this.meetingdate, this.contacts, this.notes);
		}
		if (this.notes == "EMPTY" || this.notes == null)
			this.notes = "";
	}
	
	/**Tests notes return correctly
	 * 
	 */
	@Test
	public void testGetNotes(){
		assertEquals(this.notes, this.meeting.getNotes());
	}
	/**Tests that the meeting with otherwise good parameters will not create if in the future
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void test2FutureMeeting(){
		new PastMeetingImpl(this.meetingid, TestTools.createCalendarMonths(1), this.contacts, this.notes);
	}
}
