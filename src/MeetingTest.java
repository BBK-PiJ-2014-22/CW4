
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
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
public class MeetingTest {

	Calendar meetingdate;
	Set<Contact> contacts;
	int meetingid;
	Meeting meeting;
	
	/**Parameters should be the class of meeting. Parameters should be:
	 * 
	 * 1) Meeting ID
	 * 2) Time (in months) before or after current date that the meeting date should be set to
	 * 3) Number of contacts to add to the meeting
	 * 
	 * @return the parameters for testing
	 */
	
	@Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][] {     
                {1,  1,  0 },
                {2, -1,  1 },
                {3,  0 , 2 }
          });
		
	}
	
	public MeetingTest(int id, int monthChange, int numberOfContacts){
		
		this.meetingdate = new GregorianCalendar();
		this.meetingdate.add(Calendar.MONTH,monthChange);
		this.meetingid = id;
		this.contacts = new HashSet<Contact>();
		
		for (int i = 0 ; i < numberOfContacts ; i++)
			this.contacts.add(new ContactImpl(i, "Contact"+i, "Notes for contact "+i));
	}
	
	@Before
	public void setUp(){
		meeting = new MeetingImpl(this.meetingid, this.meetingdate, this.contacts);
	}

	@Test
	public void testGetID() {
		assertEquals(this.meetingid, meeting.getId());
	}

	@Test
	public void testGetDate() {
		assertEquals(this.meetingdate, meeting.getDate());
	}
	
	@Test
	public void testGetContacts() {
		assertEquals(this.contacts, meeting.getContacts());
	}
	
	
}
