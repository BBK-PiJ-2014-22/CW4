import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerMeetingTest {

	ContactManager cm;
	
	/**Sets up a new ContactManager with 10 contacts to use
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.cm = new ContactManagerImpl();
		for (int i = 0; i < 10 ; i++){
			cm.addNewContact("Name "+i, "Notes "+i);
		}
	}

	//addFutureMeeting() Tests (AFM for short)
	/**AFMTest1 - Tests the base case - a  single FutureMeeting in the Future with a single contact
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void AFM1singleContactFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(cm.getContacts(0), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, cm.getContacts(0));
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest2 - Tests a meeting that has had multiple, existing contacts added to it. Date in the future
	 * 
	 * Should result in a meeting with several contacts allocated to it.
	 */
	@Test
	public void AFM2multiContactFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(cm.getContacts(0,1,2,3), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, cm.getContacts(0,1,2,3));
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest3- Empty set of contacts, date in the future
	 */
	@Test
	public void AFM3emptySetFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(new HashSet<Contact>(), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, new HashSet<Contact>());
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest4 - Contact set is null, date is in the future
	 * 
	 *Should work (by spec)
	 * 
	 */
	//TODO - may need changing if answer on Forum comes back that this should result in null pointer exception
	@Test
	public void AFM4NullSetFutureMeeting(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(null, meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, null);
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest5- Some of the Contacts added are not in CM, date in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM5SomeContactsNotInCM(){
		Set<Contact> contacts = cm.getContacts(0,1,2);
		//Note that the below contact is equal but not identical to contacts in CM. Need to be the same object
		contacts.add(new ContactImpl(0, "Name 0", "Notes 0"));
		cm.addFutureMeeting(contacts, TestTools.createCalendar(1));
	}

	/**AFMTest6- all contacts to add are not in CM, date in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM6NoContactsNotInCM(){
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(100, "Notin CRM1", "This Should Break"));
		contacts.add(new ContactImpl(101, "Notin CRM2", "This Should Break"));
		cm.addFutureMeeting(contacts, TestTools.createCalendar(1));
	}

	/**AFMTest7- Contact set works, but the date is in the past.
	 * 
	 *Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM7PastDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendar(-1));
	}
	
	/**AFMTest8- Contact set works, but the date is today.
	 * 
	 *Should result in IllegalArgumentException
	 */
	//TODO - double check this is the case. Might be ok.
	@Test(expected = IllegalArgumentException.class)
	public void AFM8TodaysDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendar(0));
	}
	
	/**AFMTest9 - Multiple meetings added. Tests that the meeting ID increases,
	 * and that the AFM method returns correctly.
	 * 
	 * Should result in an array of numbers 0 - 9
	 */
	@Test
	public void AFM9MultipleMeetings(){

		int[] actual = new int[10];
		
		for (int i = 0; i < 10 ; i ++)
			actual[i] = cm.addFutureMeeting(cm.getContacts(i), TestTools.createCalendar(1));

		int[] expected = {0,1,2,3,4,5,6,7,8,9};
		
		assertArrayEquals(expected, actual);
	}
	
	//***AddPastMeeting() Tests (APM for short)***
	
	/**APMTest1 - Tests the base case - a  single PastMeeting in the past with a single contact
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void APM1singleContactPastDate(){
		Calendar meetingdate = TestTools.createCalendar(-1);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getMeeting(0));
	}
	
	/**APMTest2 - a  single PastMeeting in the past with multiple contacts
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void APM2MultiContactPastDate(){
		Calendar meetingdate = TestTools.createCalendar(-1);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getMeeting(0));
	}
	
	/**APMTest3 - Empty set of contacts, date in the past, notes work
	 * 
	 * Should result in am illegal argument exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM3EmptyContactsPastDate(){
		Set<Contact> emptySet = new HashSet<Contact>();
		cm.addNewPastMeeting(emptySet,TestTools.createCalendar(-1),"Notes");
	}	
	
	/**APMTest4 -null for contact set, date in the past, notes work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM4NullContactsPastDate(){
		cm.addNewPastMeeting(null, TestTools.createCalendar(-1), "Notes");
	}
	
	/**APMTest5 -null for calendar, contacts and notes work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM5NullCalendarPastDate(){
		cm.addNewPastMeeting(cm.getContacts(0), null, "Notes");
	}	
	
	/**APMTest6 -null notes, date and contacts work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM6NullNotesPastDate(){
		new PastMeetingImpl(0, TestTools.createCalendar(-1),null);
	}	
	
	/**APMTest7- Some of the Contacts added are not in CM, date in the past
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM7SomeContactsNotInCM(){
		Set<Contact> contacts = cm.getContacts(0,1,2);
		//Note that the below contact is equal but not identical to contacts in CM.
		contacts.add(new ContactImpl(0, "Name 0", "Notes 0"));
		cm.addNewPastMeeting(contacts, TestTools.createCalendar(-1), "Notes");
	}

	/**APMTest8- all contacts to add are not in CM, date in the past
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM8NoContactsNotInCM(){
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(100, "Notin CRM1", "This Should Break"));
		contacts.add(new ContactImpl(101, "Notin CRM2", "This Should Break"));
		cm.addNewPastMeeting(contacts, TestTools.createCalendar(-1), "Notes");
	}
	
	/**APMTest9- Date is in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM9DateInFuture(){
		cm.addNewPastMeeting(cm.getContacts(0), TestTools.createCalendar(1), "Notes");
	}
	
	/**APMTest10- Date is today
	 * 
	 * Should parse as normal
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM10DateIsToday(){
		Calendar meetingdate = TestTools.createCalendar(0);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	//TODO - Add Past meetings Tests
	
	/**APMTest11 - Multiple Meetings added
	 * 
	 * Should result in the final meeting ID matching the correct meeting
	 */
	@Test
	public void APM11MultipleMeetings(){
	
		for (int i = 0; i < 10 ; i ++)
			cm.addNewPastMeeting(cm.getContacts(i), TestTools.createCalendar(-1),"Notes"+i);
		
		Meeting actual = cm.getMeeting(9);
		Meeting expected = new PastMeetingImpl(9, TestTools.createCalendar(-1), cm.getContacts(9) ,"Notes9");

		assertEquals(expected, actual);
	}
	
	
	
	
	
	
	//TODO - Add Get Meeting Tests
	//Test1 - Test works for past
	//Test2 - Test works for future
	//Test3 - Test breaks when blank
	
	
	
	//TODO - Get Future Meeting Tests
	//Test2- ID matches past meeting
	//Test3- ID Matches no meeting
	
	//TODO - Get Past meeting tests
	//Test2- ID matches future meeting
	//Test3- ID Matches no meeting
	
	//TODO - getFutureMeetingList(Contact) tests (Note: Check order)
	//Test1- no meetings with contact
	//Test2- one meeting with contact
	//Test3- multiple meetings with contact
	//Test4- convert future meeting to past, get list
	//Test5- contact does not exist
	
	
	//TODO - getFutureMeetingList(Date) tests (Note: Check order)
	//Test1- Date matches to future meeting
	//Test2- date matches to multiple future meetings
	//Test3- Date does not match to any future meetings
	//Test4- Date is in the past
	
	//TODO - getPastMeetingList(Contact) tests (Note: Check order)
	//Test1- no meetings with contact
	//Test2- one meeting with contact
	//Test3- multiple meetings with contact
	//Test4- convert future meeting to past, get list
	//Test5- contact does not exist
	
	
	//TODO - getPastMeetingList(Date) tests (Note: Check order)
	//Test1- Date matches to past meeting
	//Test2- date matches to multiple past meetings
	//Test3- Date does not match to any past meetings
	//Test4- Date is in the future
	
	//TODO - addMeetingNotes tests
	//Test1 - Add notes to past meeting, check they add
	//Test2 - Add notes to past meeting multiple times
	//Test3 - Add null notes to past meeting
	//Test4 - Add notes to future meeting with past date, check it converts (Force date change?)
	//Test5 - add notes to future meeting, check consistency of list
	//Test6 - add notes to future meeting in future, check exception
	//Test7 - add null notes to future meeting
	

}
