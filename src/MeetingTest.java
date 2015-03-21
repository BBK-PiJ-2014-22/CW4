
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**Parameterized Tests for Meeting Implementation.
 * 
 * Tests:
 * 1 to 3  - Tests getters 
 * 4 to 8  - Tests .equals(Object)
 * 9 to 11 - Tests constructor exceptions
 * 12      - Tests toString();
 * 
 * @author Jamie
 *
 */
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
                {1,  1,  1 },
                {2, -1,  2 },
                {3,  0 , 3 }
          });
		
	}
	
	/**Constructor creates fields for the SetUp to work with and the tests to match against.
	 * 
	 * @param id ID of the meeting to be  made/results to be tested against
	 * @param monthChange The difference (in months) from current date to create/test against
	 * @param numberOfContacts The number of contacts to add to the meeting.
	 */
	public MeetingTest(int id, int monthChange, int numberOfContacts){	
		this.meetingdate = TestTools.createCalendarMonths(monthChange);
		this.meetingid = id;
		this.contacts = new HashSet<Contact>();
		for (int i = 0 ; i < numberOfContacts ; i++)
			this.contacts.add(new ContactImpl(i, "Contact"+i, "Notes for contact "+i));
	}
	
	/**Creates a meeting*/
	@Before
	public void setUp(){
		meeting = new MeetingImpl(this.meetingid, this.meetingdate, this.contacts);
	}
	
	//Test Getters.
	@Test
	public void test1GetID() {
		assertEquals(this.meetingid, meeting.getId());
	}

	@Test
	public void test2GetDate() {
		assertEquals(this.meetingdate, meeting.getDate());
	}
	
	@Test
	public void test3GetContacts() {
		assertEquals(this.contacts, meeting.getContacts());
	}
	
	//Test .equals(Object)
	@Test
	public void test4EqualToEqual(){
		Meeting compare = new MeetingImpl(this.meetingid, this.meetingdate, this.contacts);
		assertEquals(true,meeting.equals(compare));		
	}
	
	@Test
	public void test5EqualToUnequalID(){
		Meeting compare = new MeetingImpl(this.meetingid-1, this.meetingdate, this.contacts);
		assertEquals(false,meeting.equals(compare));		
	}
	
	@Test
	public void test6EqualToUnequalContacts(){
		
		Set<Contact> newContacts = new HashSet<Contact>();
		newContacts.add(new ContactImpl(4, "Non Contact", "Notes"));
			
		Meeting compare = new MeetingImpl(this.meetingid-1, this.meetingdate, newContacts);
		assertEquals(false,meeting.equals(compare));		
	}
	
	@Test
	public void test7EqualToUnequalDate(){
		Meeting compare = new MeetingImpl(this.meetingid-1, TestTools.createCalendarMonths(6), this.contacts);
		assertEquals(false,meeting.equals(compare));		
	}
	
	@Test
	public void test8EqualToRandomObject(){
		Integer compare = new Integer(1);
		assertEquals(false,meeting.equals(compare));		
	}
	
	//Test constructor exceptions
	@Test (expected = IllegalArgumentException.class)
	public void test9EmptyContactSet(){
		meeting = new MeetingImpl(this.meetingid, this.meetingdate, new HashSet<Contact>());
	}
	
	@Test (expected = NullPointerException.class)
	public void test10NullContactSet(){
		meeting = new MeetingImpl(this.meetingid, this.meetingdate, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void test11NullDate(){
		meeting = new MeetingImpl(this.meetingid, null, this.contacts);
	}

	//Tests toString()
	public void test12ToString(){
		String expected = "Meeting [id=" + this.meetingid + ", date=" + this.meetingdate.getTime() + ", contacts="+ this.contacts + "]";
		assertEquals(expected,meeting.toString());
	}
}
